package com.example.healconn;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ScheduleActivity extends Activity {
	
	private GoogleMap mMap;
	private LinearLayout calendarLayout;
	private CalendarView calendarView;
	private final int black = 0xff000000;
    private final Drawable drawableBlack = new ColorDrawable(black);
    private static final String TAG = "ScheduleActivity";
    
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);

		/* Set up google map data */
		mMap = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        LatLng UHS = new LatLng(40.444987, -79.943503);
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UHS, 17));
        mMap.addMarker(new MarkerOptions()
                .title("University Health Service Center")
                .snippet("Carnegie Mellon University UHS")
                .position(UHS));
        
        /* Dynamically generate calendar view */
        Fragment f = (Fragment) getFragmentManager().findFragmentById(R.id.datepicker);
        calendarLayout = (LinearLayout) f.getView().findViewById(R.id.calenderLayout);       
        calendarView = new CalendarView(getApplicationContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
        		   (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        calendarView.setLayoutParams(params);
        calendarView.setBackground(drawableBlack);
        long current = System.currentTimeMillis();      // get system current time
        calendarView.setMaxDate(current + 5259000000l); // set max date to 2 month after current date 
        calendarView.setMinDate(current - 2630000000l); // set min date to 1 month before current date
        calendarLayout.addView(calendarView);
        
        /* calendar view select date change listener */
        calendarView.setOnDateChangeListener(new OnDateChangeListener() {
			@Override
			public void onSelectedDayChange(CalendarView view, int year, int month,
					int dayOfMonth) {
				final String selectedDate = Integer.toString(year) + ":" + 
					                        Integer.toString(month + 1) + ":" + 
						                    Integer.toString(dayOfMonth);			
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Dates");
				query.findInBackground(new FindCallback<ParseObject>() {
				     public void done(List<ParseObject> objects, ParseException e) {
				         if (e == null) {
				        	// query success, try to match selectedDate with Date in Dates form
				        	 Log.i(TAG, selectedDate);
				        	 boolean notFound = true;
				        	 for(int i = 0; i < objects.size(); i++){
				        		 Log.i(TAG, objects.get(i).getString("Date"));
				        		 if(objects.get(i).getString("Date").equals(selectedDate)){
				        			 // match success
				        			 notFound = false;
				        			 alertMatchSuccess(objects.get(i));
				        		 }
				        	 }
				        	 if(notFound){
				        		 // match unsuccess
				        		 alertMatchUnsuccess();
				        	 }
				         }else{
				             // query fail
				        	 alertQueryUnsuccess();
				         }
				     }
				 });
			}
        });
	
	}
    
	// alert user that the query is unsuccessful
	public void alertQueryUnsuccess(){
		AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleActivity.this);
		builder.setMessage("Sorry, query for date availability is failed, "
				+ "please contact HealConn team for this problem.");
		builder.setTitle("Oops!");
		builder.setPositiveButton(android.R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	// alert user that the match between selected date and date available is unsuccessful
	public void alertMatchUnsuccess(){
		AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleActivity.this);
		builder.setMessage("Sorry, the date you choose is unavailable, try another one!");
		builder.setTitle("Unavailable!");
		builder.setPositiveButton(android.R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	// alert user that the match is successful
	@SuppressLint("NewApi")
	public void alertMatchSuccess(ParseObject matchObject){
		AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleActivity.this);
		boolean availability_9_11 = Boolean.parseBoolean(matchObject.get("Availability_9_11").toString());
		boolean availability_11_13 = Boolean.parseBoolean(matchObject.get("Availability_11_13").toString());
		boolean availability_13_15 = Boolean.parseBoolean(matchObject.get("Availability_13_15").toString());
		boolean availability_15_17 = Boolean.parseBoolean(matchObject.get("Availability_15_17").toString());
		
		RadioGroup radioGroupAlert = new RadioGroup(getApplicationContext());
		RadioGroup.LayoutParams params = 
				new RadioGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		radioGroupAlert.setLayoutParams(params);
		
	    if(availability_9_11){
	    	RadioButton radioButton_9_11 = new RadioButton(getApplicationContext());
	    	radioButton_9_11.setLayoutParams(params);
	    	radioButton_9_11.setText("9am ---- 11am");
	    	radioButton_9_11.setBackgroundColor(black);
	    	radioGroupAlert.addView(radioButton_9_11);
	    }
	    if(availability_11_13){
	    	RadioButton radioButton_11_13 = new RadioButton(getApplicationContext());
	    	radioButton_11_13.setLayoutParams(params);
	    	radioButton_11_13.setText("11am ---- 1pm");
	    	radioButton_11_13.setBackgroundColor(black);
	    	radioGroupAlert.addView(radioButton_11_13);
	    }
	    if(availability_13_15){
	    	RadioButton radioButton_13_15 = new RadioButton(getApplicationContext());
	    	radioButton_13_15.setLayoutParams(params);
	    	radioButton_13_15.setText("1pm ---- 3pm");
	    	radioButton_13_15.setBackgroundColor(black);
	    	radioGroupAlert.addView(radioButton_13_15);
	    }
	    if(availability_15_17){
	    	RadioButton radioButton_15_17 = new RadioButton(getApplicationContext());
	    	radioButton_15_17.setLayoutParams(params);
	    	radioButton_15_17.setText("3pm ---- 5pm");
	    	radioButton_15_17.setBackgroundColor(black);
	    	radioGroupAlert.addView(radioButton_15_17);
	    }
	    
		//FrameLayout fl = (FrameLayout) findViewById(android.R.id.custom);
		//fl.addView(radioGroupAlert, new LayoutParams
		//		 (LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		builder.setView(radioGroupAlert);
		builder.setTitle("Available!");
		
		builder.setPositiveButton(android.R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.schedule, menu);
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
