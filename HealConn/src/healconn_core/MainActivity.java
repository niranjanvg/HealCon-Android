package healconn_core;

import healconn_fragments.HomeScreenFragment;
import healconn_fragments.HomeScreenUHSFragment;
import healconn_fragments.HomeScreenLogoFragment;
import healconn_fragments.UserInfoFragment;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.healconn.R;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

public class MainActivity extends Activity {
	
	// private instance variables
	public static Intent intent = null;
	public static boolean isAdmin = false;
	
	public static final String TAG = MainActivity.class.getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		/* subscribe HealConn Message Channel enable to
		 * send push and receive push from other devices */
		PushService.subscribe(this, "HealConn_Message_Channel", MessengerActivity.class);
		
		/* after login, save userId to Installation object */
		ParseInstallation installation = ParseInstallation.getCurrentInstallation();
		installation.put("userId", ParseUser.getCurrentUser().getObjectId());
		installation.saveInBackground();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ParseAnalytics.trackAppOpened(getIntent());
		
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser == null) {
			navigateToLogin();
		}
		else {
			Log.i(TAG, currentUser.getUsername());
		}
		
		// retrieve intent
		intent = getIntent();
		// see if user is UHS
		if (intent.getExtras() != null) {
			if (intent.getExtras().getString("name").equals("UHS")) {
				isAdmin = true;
			}
			else {
				isAdmin = false;
			}
		}

		// set up initial fragments
		FragmentTransaction fragmentTransaction = getFragmentManager()
				.beginTransaction();
		fragmentTransaction.add(R.id.homescreen_logo_fragment_container, 
				      new HomeScreenLogoFragment());
		fragmentTransaction.add(R.id.userinfo_fragment_container, new UserInfoFragment());
		if (!isAdmin) {
			fragmentTransaction.add(R.id.homescreen_fragment_container, new HomeScreenFragment());
		} else {
			fragmentTransaction.add(R.id.homescreen_fragment_container, new HomeScreenUHSFragment());
		}
		fragmentTransaction.commit();
				
	}
    

	private void navigateToLogin() {
		Intent intent = new Intent(this, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_logout) {
			PushService.unsubscribe(this, "HealConn_Message_Channel");
			ParseUser.logOut();
			navigateToLogin();
		}
		return super.onOptionsItemSelected(item);
	}
}
