package com.example.healconn;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ScheduleActivity extends Activity {
	
	private GoogleMap mMap;
	private final int black = 0xff000000;
    public static FragmentManager _fragmentManager;
    private FrameLayout _appointmentFrameLayout;
    
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
        
        /* initialize fragment manager and frame layout */
        _fragmentManager = getFragmentManager();
        _appointmentFrameLayout = (FrameLayout) 
        		            findViewById(R.id.appointment_fragment_container);
        FragmentTransaction fragmentTransaction = 
        		            _fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.appointment_fragment_container,
        		            new ScheduleDatePicker());
        fragmentTransaction.commit();
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
