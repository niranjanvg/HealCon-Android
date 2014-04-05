package com.example.healconn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class FormsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forms);
		
		ImageButton bttFeedback = (ImageButton) findViewById(R.id.button_feedback);
		ImageButton bttHistory = (ImageButton) findViewById(R.id.button_history);
		ImageButton bttImmun = (ImageButton) findViewById(R.id.button_immun);
		
		// Listener for button News
		bttFeedback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Launch the news activity
				Intent intent = new Intent(getBaseContext(), FeedbackFormActivity.class);
				startActivity(intent);				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forms, menu);
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
