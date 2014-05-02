package healconn_core;

import healconn_fragments.MessengerInboxFragment;
import healconn_fragments.NewMessageFragment;

import java.util.ArrayList;

import model.NavDrawerItem;
import adapter.NavDrawerListAdapter;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.healconn.R;
import com.parse.ParseUser;
import com.parse.PushService;


public class MessengerActivity extends Activity {
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;	 
	private CharSequence mDrawerTitle; 	// nav drawer title
	private CharSequence mTitle; 		// used to store app title	 
	private String[] navMenuTitles;     // slide menu items
	private TypedArray navMenuIcons; 
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messenger);
		// set up navigation drawer
		configureNavigationDrawer();	
		// set initial fragment view
		FragmentTransaction fragmentTransaction = getFragmentManager()
		.beginTransaction();
		fragmentTransaction.add(R.id.message_fragment_container, 
		      new MessengerInboxFragment());
		fragmentTransaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.messenger, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	    	case R.id.action_logout:
	    		PushService.unsubscribe(this, "HealConn_Message_Channel");
				ParseUser.logOut();
				Intent intent = new Intent(this, LoginActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(intent);
				return true;
	        case R.id.action_new_message:
	            // Replace fragment if not admin
	        	if (!MainActivity.isAdmin) {
					FragmentTransaction fragmentTransaction = getFragmentManager()
							.beginTransaction();
					fragmentTransaction.replace(R.id.message_fragment_container, 
					      new NewMessageFragment());
					fragmentTransaction.commit();
	        	} 
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
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
}
