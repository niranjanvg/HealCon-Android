package healconn_fragments;

import healconn_core.ParseConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.healconn.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MessengerInboxFragment extends ListFragment {
	
	private List<ParseObject> mMessages;
	public static ParseObject selectedMessage;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState); 
	}
	
	@Override
	public void onResume() {
		super.onResume();
		// query database
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_MESSAGES);
		query.whereEqualTo(ParseConstants.KEY_RECIPIENT_ID, ParseUser.getCurrentUser().getObjectId());
		query.addDescendingOrder(ParseConstants.KEY_CREATED_AT);
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> messages, ParseException e) {
				if (e == null) {
					// found messages for the current user
					mMessages = messages;
					// adapt the messages for display
					ArrayList<HashMap<String,String>> myMessages = new ArrayList<HashMap<String,String>>();
					for (ParseObject message:mMessages) {
						HashMap<String, String> myMessage = new HashMap<String, String>();
						myMessage.put("subject", message.getString(ParseConstants.KEY_SUBJECT));
						myMessage.put("sender", message.getString(ParseConstants.KEY_SENDER_NAME));
						myMessages.add(myMessage);
					}
					String[] keys = {"subject", "sender"};
					int[] ids = {android.R.id.text1, android.R.id.text2};
					SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(),
												myMessages, android.R.layout.simple_list_item_2, keys, ids);
					setListAdapter(adapter);  
					
				} else {
					// error
				}
				
			}
		});
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// set selectedMessage
		selectedMessage = mMessages.get(position);
		// switch in the detail fragment
		FragmentTransaction fragmentTransaction = getFragmentManager()
				.beginTransaction();
		fragmentTransaction.replace(R.id.message_fragment_container, 
				      new MessageDetailFragment());
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}
}
