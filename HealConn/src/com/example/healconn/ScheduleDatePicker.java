package com.example.healconn;

import java.util.List;

import android.R.drawable;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ScheduleDatePicker extends Fragment {
	// public variable, will be used by ScheduleDateConfirm Class
	public static String selectedDatePublic; 
	
	private TextView datePickerText;
	private LinearLayout calendarLayout;
	private CalendarView calendarView;
	private RadioGroup radioGroupAlert;
	private String selectedDate;
	// used to prevent toast when scroll the calendar from happening
	private String prevSelectedDate; 
	private final int black = 0xff000000;
	private final int blue = 0xff0000ff;
	private static final String TAG = "ScheduleActivity";
	private List<ParseObject> datesObjects;
    
	/* manually set radio button id */
	private static final int RB_9_11  = 911;
	private static final int RB_11_13 = 1113;
	private static final int RB_13_15 = 1315;
	private static final int RB_15_17 = 1517;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		return inflater.inflate(R.layout.fragment_datepicker, container, false);
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		datePickerText = (TextView) getActivity().findViewById(R.id.pickUpDateText);
		
		/* Dynamically generate calendar view */  
	    calendarLayout = (LinearLayout) getActivity().findViewById(R.id.calenderLayout);       
	    calendarView = new CalendarView(getActivity());
	    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
	    		   (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	    calendarView.setLayoutParams(params);
	    calendarView.setWeekNumberColor(blue);
	    calendarView.setFocusedMonthDateColor(black);
	    calendarView.setShowWeekNumber(false);
	    long current = System.currentTimeMillis();      // get system current time
	    calendarView.setMaxDate(current + 2629740000l); // set max date to 2 month after current date 
	    calendarView.setMinDate(current - 2629740000l);  // set min date to w days before current date
	    calendarView.setDate(current - 86400000l); // set focused day to the day before current day
	    calendarLayout.addView(calendarView);
	    
	    /* calendar view select date change listener */
	    calendarView.setOnDateChangeListener(new OnDateChangeListener() {
			@Override
			public void onSelectedDayChange(CalendarView view, int year, int month,
					int dayOfMonth) {
				selectedDate = Integer.toString(year) + ":" + 
					           Integer.toString(month + 1) + ":" + 
						       Integer.toString(dayOfMonth);
				
				if(prevSelectedDate == null){
					// first time to load selectedDate
					prevSelectedDate = selectedDate;
					return;
				}else if(prevSelectedDate.equals(selectedDate)){
					// if prev selected date == current selected date
					// return from listener
					return;
				}else{
					prevSelectedDate = selectedDate;
				}
				
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Dates");
				if(datesObjects == null){
					// first time to query
					query.findInBackground(new FindCallback<ParseObject>() {
					     public void done(List<ParseObject> objects, ParseException e) {
					         if (e == null) {
					        	// query success, try to match selectedDate with Date in Dates form
					        	 Log.i(TAG, selectedDate);
					        	 datesObjects = objects;
					        	 boolean notFound = true;
					        	 for(int i = 0; i < datesObjects.size(); i++){
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
				}else{
					// already queried, so no need to query from backend
					// just to judge whether selected date match or not
				    boolean notFound = true;
		        	for(int i = 0; i < datesObjects.size(); i++){
		            	 Log.i(TAG, datesObjects.get(i).getString("Date"));
		        		 if(datesObjects.get(i).getString("Date").equals(selectedDate)){
		        			 // match success
		        			 notFound = false;
		        			 alertMatchSuccess(datesObjects.get(i));
		        		 }
		        	 }
		        	 if(notFound){
		        		 // match unsuccess
		        		 alertMatchUnsuccess();
		        	 }
				}
			}
	    });
	    
	    /* next button click listener */
	    Button nextButton = (Button) getActivity().findViewById(R.id.button_next);
        nextButton.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				FragmentTransaction fragmentTransaction = ScheduleActivity._fragmentManager
						.beginTransaction();
				fragmentTransaction.replace(R.id.appointment_fragment_container, 
						      new ScheduleDateConfirm());
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
				ScheduleActivity._fragmentManager.executePendingTransactions();
			}
		});
	}
	
	// alert user that the query is unsuccessful
	public void alertQueryUnsuccess(){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("Sorry, query for date availability is failed, "
				+ "please contact HealConn team for this problem.");
		builder.setTitle("Oops!");
		builder.setPositiveButton(android.R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	// alert user that the match between selected date and date available is unsuccessful
	public void alertMatchUnsuccess(){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("Sorry, the date you choose is unavailable, try another one!");
		builder.setTitle("Unavailable!");
		builder.setPositiveButton(android.R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	// alert user that the match is successful
	@SuppressLint("NewApi")
	public void alertMatchSuccess(ParseObject matchObject){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		boolean availability_9_11 = Boolean.parseBoolean(matchObject.get("Availability_9_11").toString());
		boolean availability_11_13 = Boolean.parseBoolean(matchObject.get("Availability_11_13").toString());
		boolean availability_13_15 = Boolean.parseBoolean(matchObject.get("Availability_13_15").toString());
		boolean availability_15_17 = Boolean.parseBoolean(matchObject.get("Availability_15_17").toString());
		
		radioGroupAlert = new RadioGroup(getActivity());
		RadioGroup.LayoutParams params = 
				new RadioGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		radioGroupAlert.setLayoutParams(params);
		
	    if(availability_9_11){
	    	RadioButton radioButton_9_11 = new RadioButton(getActivity());
	    	radioButton_9_11.setLayoutParams(params);
	    	radioButton_9_11.setText("9am ---- 11am");
	    	radioButton_9_11.setTextColor(blue);
	    	radioButton_9_11.setId(RB_9_11);
	    	radioGroupAlert.addView(radioButton_9_11);
	    }
	    if(availability_11_13){
	    	RadioButton radioButton_11_13 = new RadioButton(getActivity());
	    	radioButton_11_13.setLayoutParams(params);
	    	radioButton_11_13.setText("11am ---- 1pm");
	    	radioButton_11_13.setTextColor(blue);
	    	radioButton_11_13.setId(RB_11_13);
	    	radioGroupAlert.addView(radioButton_11_13);
	    }
	    if(availability_13_15){
	    	RadioButton radioButton_13_15 = new RadioButton(getActivity());
	    	radioButton_13_15.setLayoutParams(params);
	    	radioButton_13_15.setText("1pm ---- 3pm");
	    	radioButton_13_15.setTextColor(blue);
	    	radioButton_13_15.setId(RB_13_15);
	    	radioGroupAlert.addView(radioButton_13_15);
	    }
	    if(availability_15_17){
	    	RadioButton radioButton_15_17 = new RadioButton(getActivity());
	    	radioButton_15_17.setLayoutParams(params);
	    	radioButton_15_17.setText("3pm ---- 5pm");
	    	radioButton_15_17.setTextColor(blue);
	    	radioButton_15_17.setId(RB_15_17);
	    	radioGroupAlert.addView(radioButton_15_17);
	    }
		
		builder.setView(radioGroupAlert);
		builder.setTitle("Available hours:");
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			    // if alert dialog button is clicked, 
				// check whether radio button is selected or not
				switch (radioGroupAlert.getCheckedRadioButtonId()) {
					case -1: // no button is selected
						// do nothing
						break;
					case 911:
						// 9 to 11 is selected
						selectedDatePublic = "Your chosen time is " + selectedDate +
											 ": 9am --- 11am.    Press NEXT to confirm!";
						datePickerText.setText(selectedDatePublic);
						break;
					case 1113:
						// 11 to 13 is selected
						selectedDatePublic = "Your chosen time is " + selectedDate +
						 					 ": 11am --- 1pm.    Press NEXT to confirm!";
						datePickerText.setText(selectedDatePublic);
						break;
					case 1315:
						// 13 to 15 is selected
						selectedDatePublic = "Your chosen time is " + selectedDate +
						 					 ": 1pm --- 3pm.    Press NEXT to confirm!";
						datePickerText.setText(selectedDatePublic);
						break;
					case 1517:
						// 15 to 17 is selected
						selectedDatePublic = "Your chosen time is " + selectedDate +
						 					 ": 3pm --- 5pm.    Press NEXT to confirm!";
						datePickerText.setText(selectedDatePublic);
						break;
					default:
						break;
				}
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
