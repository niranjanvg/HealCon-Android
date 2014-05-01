package healconn_fragments;

import healconn_core.FormsActivity;
import healconn_core.MessengerActivity;
import healconn_core.NewsActivity;
import healconn_core.ScheduleActivity;

import com.example.healconn.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class HomeScreenFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_homescreen, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// set listener for the image buttons

		ImageButton bttAppoint = (ImageButton) getActivity().findViewById(R.id.button_appointment);
		ImageButton bttNews = (ImageButton) getActivity().findViewById(R.id.button_news);
		ImageButton bttMessenger = (ImageButton) getActivity().findViewById(R.id.button_messenger);
		ImageButton bttForms = (ImageButton) getActivity().findViewById(R.id.button_forms);
		
		// Listener for button Appointment
		bttAppoint.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Launch the appointment scheduling activity
				Intent intent = new Intent(getActivity().getBaseContext(), ScheduleActivity.class);
				startActivity(intent);
			}
		});	
		
		// Listener for button News
		bttNews.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Launch the news activity
				Intent intent = new Intent(getActivity().getBaseContext(), NewsActivity.class);
				startActivity(intent);				
			}
		});
		
		// Listener for button News
		bttForms.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Launch the news activity
				Intent intent = new Intent(getActivity().getBaseContext(), FormsActivity.class);
				startActivity(intent);				
			}
		});
		
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
