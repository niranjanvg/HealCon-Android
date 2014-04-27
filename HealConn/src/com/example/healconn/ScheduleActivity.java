package com.example.healconn;

import java.util.ArrayList;

import model.NavDrawerItem;
import adapter.NavDrawerListAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ScheduleActivity extends Activity {
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;	 
	private CharSequence mDrawerTitle; 	// nav drawer title
	private CharSequence mTitle; 		// used to store app title	 
	private String[] navMenuTitles;     // slide menu items
	private TypedArray navMenuIcons; 
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	
	private GoogleMap mMap;
	private final int black = 0xff000000;
    private FragmentManager _fragmentManager;
    
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);
		
		configureNavigationDrawer();
        
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
        FragmentTransaction fragmentTransaction = 
        		            _fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.appointment_fragment_container,
        		            new ScheduleDatePicker());
        fragmentTransaction.commit();
    }

	private void configureNavigationDrawer() {
		// configure navigation drawer
		mTitle = mDrawerTitle = getTitle();	 
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); 
        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons); 
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        
        // adding nav drawer items to array
        navDrawerItems = new ArrayList<NavDrawerItem>();
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Appointment
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // News
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Messenger
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // Form
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));

        // Recycle the typed array
        navMenuIcons.recycle();
 
        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);
 
        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
 
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }
 
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
	}
	
	// Slide menu item click listener
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
        	Intent intent;
        	switch(position) {
        		// home
        		case 0:
        			intent = new Intent(getBaseContext(), MainActivity.class);
    				startActivity(intent);
    				break;
    			// appointment
        		case 1:
        			intent = new Intent(getBaseContext(), ScheduleActivity.class);
    				startActivity(intent);
    				break;
    			// news
        		case 2:
        			intent = new Intent(getBaseContext(), NewsActivity.class);
    				startActivity(intent);
    				break;
    			// messenger
        		case 3:
        			intent = new Intent(getBaseContext(), MessengerActivity.class);
    				startActivity(intent);
    				break;
    			// forms
        		case 4:
        			intent = new Intent(getBaseContext(), FormsActivity.class);
    				startActivity(intent);
    				break;
    			default:
    				break;
        	}
        }
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

    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
 
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
 
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
 
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
	
}
