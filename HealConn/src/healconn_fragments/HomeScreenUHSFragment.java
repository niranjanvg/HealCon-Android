package healconn_fragments;

import healconn_core.MessengerActivity;

import com.example.healconn.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class HomeScreenUHSFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		return inflater.inflate(R.layout.fragment_homescreen_uhs, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		ImageButton bttMessenger = (ImageButton) getActivity().findViewById(R.id.button_messenger);
		// Listener for button Messenger
        bttMessenger.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Launch the appointment scheduling activity
				Intent intent = new Intent(getActivity().getBaseContext(), MessengerActivity.class);
				startActivity(intent);
			}
		});
        
	}
}
