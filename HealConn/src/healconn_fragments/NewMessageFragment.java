package healconn_fragments;

import healconn_core.ParseConstants;

import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healconn.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class NewMessageFragment extends Fragment {
	
	// private instance variables
	private String recipientID;
	private String subject;
	private String content;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		return inflater.inflate(R.layout.fragment_new_message, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// attach listener for the send message button
		Button buttonSendMsg = (Button) getActivity().findViewById(R.id.button_new_message);
		buttonSendMsg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// retrieve message subject and content
				EditText subjectField = (EditText) getActivity().findViewById(R.id.message_subject);
				EditText contentField = (EditText) getActivity().findViewById(R.id.message_reply_text);
				subject = subjectField.getText().toString();
				content = contentField.getText().toString();				

				// sends the Message to UHS
				recipientID = "f1Z0BcuID7";
				ParseObject message = createMessage(subject, content);
				send(message);
				
				// After sending the Message, send a push notification to UHS
				// First, create a query
				ParseQuery<ParseInstallation> pushQuery = ParseInstallation.getQuery();
				pushQuery.whereEqualTo("userId", recipientID);
				// Second, send set query to push
				ParsePush push = new ParsePush();
				push.setQuery(pushQuery);
				push.setMessage("New message from " 
				                + ParseUser.getCurrentUser().getString("name") + "......");
				push.sendInBackground();
				
				
				// navigate to Inbox
				FragmentTransaction fragmentTransaction = getFragmentManager()
				.beginTransaction();
				fragmentTransaction.replace(R.id.message_fragment_container, 
				      new MessengerInboxFragment());
				fragmentTransaction.commit();
				
			}
		});
	}
	
	// create a ParseObject called Message for sending
	protected ParseObject createMessage(String subject, String content) {
		ParseObject message = new ParseObject(ParseConstants.CLASS_MESSAGES);
		message.put(ParseConstants.KEY_SENDER_ID, ParseUser.getCurrentUser().getObjectId());
		message.put(ParseConstants.KEY_SENDER_NAME, ParseUser.getCurrentUser().get("name"));
		message.put(ParseConstants.KEY_RECIPIENT_ID, recipientID);
		message.put(ParseConstants.KEY_SUBJECT, subject);
		message.put(ParseConstants.KEY_CONTENT, content);
		
		return message;
	}
	
	// sends a message 
	protected void send(ParseObject message) {
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
