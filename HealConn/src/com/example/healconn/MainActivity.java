package com.example.healconn;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseAnalytics;
import com.parse.ParseUser;

public class MainActivity extends Activity {

	public static final String TAG = MainActivity.class.getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
		
		// display name, department, studentID, profile picture
		displayUser();
		
		ImageButton bttAppoint = (ImageButton) findViewById(R.id.button_appointment);
		ImageButton bttNews = (ImageButton) findViewById(R.id.button_news);
		ImageButton bttMessenger = (ImageButton) findViewById(R.id.button_messenger);
		ImageButton bttForms = (ImageButton) findViewById(R.id.button_forms);
		
		// Listener for button Appointment
		bttAppoint.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Launch the appointment scheduling activity
				Intent intent = new Intent(getBaseContext(), ScheduleActivity.class);
				startActivity(intent);
			}
		});	
		
		// Listener for button News
		bttNews.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Launch the news activity
				Intent intent = new Intent(getBaseContext(), NewsActivity.class);
				startActivity(intent);				
			}
		});
		
		// Listener for button News
		bttForms.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Launch the news activity
				Intent intent = new Intent(getBaseContext(), FormsActivity.class);
				startActivity(intent);				
			}
		});
			
        bttMessenger.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Launch the appointment scheduling activity
				Intent intent = new Intent(getBaseContext(), MessengerActivity.class);
				startActivity(intent);
			}
		});
	}

	// displays the right user information
	@SuppressLint("NewApi")
	private void displayUser() {
		// display name, department, studentID
		Intent intent = getIntent();
		String name = intent.getExtras().getString("name");
		String department = intent.getExtras().getString("department");
		String studentID = intent.getExtras().getString("studentID");
		TextView nameText = (TextView) findViewById(R.id.name);
		TextView depText = (TextView) findViewById(R.id.department);
		TextView idText = (TextView) findViewById(R.id.studentid);
		nameText.setText("Name: " + name);
		depText.setText("Department: " + department);
		idText.setText("ID: " + studentID);
		
		// display user profile picture
		byte[] imgBytes = intent.getByteArrayExtra("userPic");
		Bitmap bmp = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
		Drawable drawable =new BitmapDrawable(getResources(), bmp);
		ImageView user = (ImageView) findViewById(R.id.user);
		user.setImageDrawable(drawable);
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
			ParseUser.logOut();
			navigateToLogin();
		}
		return super.onOptionsItemSelected(item);
	}


}
