package com.example.healconn;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class FeedbackFormActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback_form);
		setCurrentDateOnView();
		addListenerOnRatingBar();

	}
	
	// display current date
	public void setCurrentDateOnView() {	
		
		DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);
	
		final Calendar c = Calendar.getInstance();	
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);		
		int day = c.get(Calendar.DAY_OF_MONTH);
	
		// set current date into date picker		
		datePicker.init(year, month, day, null);
	 
	}
	
	public void addListenerOnRatingBar() {  		
		RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar_service);
		
		// update the rating description text according to user choice
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,
				boolean fromUser) {
				TextView descrip = (TextView) findViewById(R.id.rating_descrip);
				String[] feedbacks = {"Extremely poor", "Extremely poor", "Not quite as expected", 
						"Acceptable", "Very helpful", "Couldn't be any better!"};
				int index = (int) rating;
				descrip.setText(feedbacks[index]);		 
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feedback_form, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
