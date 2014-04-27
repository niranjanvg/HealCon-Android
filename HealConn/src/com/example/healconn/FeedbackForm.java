package com.example.healconn;

import java.util.Calendar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;


public class FeedbackForm extends Fragment {
	
	// private instance variable
	private FragmentManager fragmentManager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		return inflater.inflate(R.layout.fragment_feedback_form, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setCurrentDateOnView();
		addListenerOnRatingBar();
		
		// Set listener for "Submit" button
		Button btt_submit = (Button) getActivity().findViewById(R.id.button_submit);
		btt_submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Replace fragment
		        fragmentManager = getFragmentManager();
		        FragmentTransaction fragmentTransaction = 
		        		            fragmentManager.beginTransaction();
		        fragmentTransaction.replace(R.id.feedback_fragment_container,
		        		            new FeedbackConfirm());
		        fragmentTransaction.addToBackStack(null);
		        fragmentTransaction.commit();
			}
		});
		
	}
	
	/* listener for rating bar */
	public void addListenerOnRatingBar() {  		
		RatingBar ratingBar = (RatingBar) getActivity().findViewById(R.id.ratingBar_service);
		
		// update the rating description text according to user choice
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,
				boolean fromUser) {
				TextView descrip = (TextView) getActivity().findViewById(R.id.rating_descrip);
				String[] feedbacks = {"Extremely poor", "Extremely poor", "Not quite as expected", 
						"Acceptable", "Very helpful", "Couldn't be any better!"};
				int index = (int) rating;
				descrip.setText(feedbacks[index]);		 
			}
		});
	}
	
	// display current date
	public void setCurrentDateOnView() {	
		
		DatePicker datePicker = (DatePicker) getActivity().findViewById(R.id.date_picker);
	
		final Calendar c = Calendar.getInstance();	
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);		
		int day = c.get(Calendar.DAY_OF_MONTH);
	
		// set current date into date picker		
		datePicker.init(year, month, day, null);
	 
	}

}
