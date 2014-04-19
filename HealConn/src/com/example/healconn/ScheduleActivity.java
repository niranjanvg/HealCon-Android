package com.example.healconn;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ScheduleActivity extends Activity {
	
	private GoogleMap mMap;
	private LinearLayout calendarLayout;
	private CalendarView calendarView;
	private final int black = 0xff000000;
    private final Drawable drawableBlack = new ColorDrawable(black);
    
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
        //params.topMargin = 100;
        calendarView.setLayoutParams(params);
        calendarView.setBackground(drawableBlack);
        
        calendarLayout.addView(calendarView);
        
        calendarView.setOnDateChangeListener(new OnDateChangeListener() {
			@Override
			public void onSelectedDayChange(CalendarView view, int year, int month,
					int dayOfMonth) {
				Toast.makeText(getBaseContext(),"Selected Date is\n\n"
					+ (month + 1) + " : " + dayOfMonth + ":" + year,
					Toast.LENGTH_LONG).show();
			}
        });
	
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
