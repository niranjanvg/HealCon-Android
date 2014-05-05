package healconn_fragments;

import util.ImageDecoder;
import healconn_core.ParseConstants;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healconn.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MessageDetailFragment extends Fragment {
	
	private ParseObject selectedMessage;
	private String subject;
	private String date;
	private String content;
	private String senderID;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_message_detail, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// set message content and display
		selectedMessage = MessengerInboxFragment.selectedMessage;
		subject = selectedMessage.getString(ParseConstants.KEY_SUBJECT);
		content = selectedMessage.getString(ParseConstants.KEY_CONTENT);
		date = selectedMessage.getCreatedAt().toString();
		senderID = selectedMessage.getString(ParseConstants.KEY_SENDER_ID);
		EditText messageRecieved = (EditText) getActivity().findViewById(R.id.message_recieved);
		messageRecieved.setText(content);
		messageRecieved.setKeyListener(null);
		TextView subjectTV = (TextView) getActivity().findViewById(R.id.message_detail_subject);
		TextView dateTV = (TextView) getActivity().findViewById(R.id.message_detail_date);
		subjectTV.setText("Subject: " + subject);
		dateTV.setText("Date: " + date);
		
		// display sender info
		String senderName = selectedMessage.getString(ParseConstants.KEY_SENDER_NAME);
		TextView senderNameTV = (TextView) getActivity().findViewById(R.id.message_sender_name);
		senderNameTV.setText(senderName);
		// set sender photo
		byte[] imgBytes = null;
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		try {
			ParseObject user = query.get(senderID);
			ParseFile userPic = user.getParseFile("userPic");
			imgBytes = userPic.getData();
			Drawable drawable = ImageDecoder.makeDrawable(getResources(),imgBytes);
			ImageView userIV = (ImageView) getActivity().findViewById(R.id.message_sender_photo);
			userIV.setImageDrawable(drawable);
			
		} catch (ParseException e) {
			// error
			return;
		}
		
		// set up listener for the "Send Reply" button
		Button bttReply = (Button) getActivity().findViewById(R.id.button_message_reply);
		bttReply.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// creates a message and deliver response to sender
				EditText replyTextfield = (EditText) getActivity().findViewById(R.id.message_reply_text);
				String replyText = replyTextfield.getText().toString();
				ParseObject message;
				if (!senderID.equals("f1Z0BcuID7")) {
					message = createMessage("Reply from UHS", replyText);
				} else {
					message = createMessage("Reply from " + ParseUser.getCurrentUser().getString("name"), replyText);
				}
				send(message);
				
				// After sending the Message, send a push notification to UHS
				// First, create a query
				ParseQuery<ParseInstallation> pushQuery = ParseInstallation.getQuery();
				pushQuery.whereEqualTo("channels", "HealConn_Message_Channel"); // set the channel
				pushQuery.whereEqualTo("userId", MessageDetailFragment.this.senderID);
				// Second, send set query to push
				ParsePush push = new ParsePush();
				push.setQuery(pushQuery);
				push.setMessage("New message from "
				                + ParseUser.getCurrentUser().getString("name") + "......");
				push.sendInBackground();
								
				// navigate to inbox
				FragmentTransaction fragmentTransaction = getFragmentManager()
				.beginTransaction();
				fragmentTransaction.replace(R.id.message_fragment_container,
                                            new MessengerInboxFragment());
				fragmentTransaction.commit();
			}
		});
	}
	
	// create a ParseObject called Message for sending
	private ParseObject createMessage(String subject, String content) {
		ParseObject message = new ParseObject(ParseConstants.CLASS_MESSAGES);
		message.put(ParseConstants.KEY_SENDER_ID, ParseUser.getCurrentUser().getObjectId());
		message.put(ParseConstants.KEY_SENDER_NAME, ParseUser.getCurrentUser().get("name"));
		message.put(ParseConstants.KEY_RECIPIENT_ID, senderID);
		message.put(ParseConstants.KEY_SUBJECT, subject);
		message.put(ParseConstants.KEY_CONTENT, content);
		
		return message;
	}
	
	// sends a message
	private void send(ParseObject message) {
		try {
			message.save();
			Toast.makeText(getActivity(), "Your Message has been successfully delivered!",
                           Toast.LENGTH_LONG).show();
		} catch (ParseException e) {
			Toast.makeText(getActivity(), "Encountered error when sending your message...",
                           Toast.LENGTH_LONG).show();
		}
	}
}
