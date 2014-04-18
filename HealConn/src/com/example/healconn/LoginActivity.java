package com.example.healconn;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class LoginActivity extends Activity {
	
	private EditText username_field;
	private EditText password_field;
	private String username;
	private String password;
	private Button btt_signUp;
	private Button btt_login;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_login);
		
		btt_login = (Button) findViewById(R.id.button_login);
		btt_signUp = (Button) findViewById(R.id.button_sign_up);
		
		btt_signUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Launches the sign up activity
				Intent intent = new Intent(getBaseContext(), SignUpActivity.class);
				startActivity(intent);
			}
		});
		
		btt_login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				username_field = (EditText) findViewById(R.id.username_field); 
				password_field = (EditText) findViewById(R.id.password_field);
				username = username_field.getText().toString();
				password = password_field.getText().toString();
				
				// check for format
				boolean formatOK = true;
				if (formatOK) {
					// Login a new user
					setProgressBarIndeterminateVisibility(true);
					ParseUser.logInInBackground(username, password, new LogInCallback() {
						
						@Override
						public void done(ParseUser user, ParseException e) {
							setProgressBarIndeterminateVisibility(false);
							if (e == null) {
								// login success
								Intent intent = new Intent(LoginActivity.this, MainActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
								
								String name = (String) user.get("name");
								String department = (String) user.get("department");
								String studentID = (String) user.get("studentID");
								ParseFile profilePic = user.getParseFile("userPic");
								byte[] imgBytes = null;
								try {
									imgBytes = profilePic.getData();
								} catch (ParseException e1) {
									e1.printStackTrace();
								}
								if (imgBytes != null) {
									intent.putExtra("userPic", imgBytes);
								}
								intent.putExtra("name", name);
								intent.putExtra("department", department);
								intent.putExtra("studentID", studentID);
								
								startActivity(intent);
							}
							else {
								// alert user that the login is unsuccessful
								AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
								builder.setMessage("Sorry, the information you entered is incorrect, please try again...");
								builder.setTitle("Oops!");
								builder.setPositiveButton(android.R.string.ok, null);
								AlertDialog dialog = builder.create();
								dialog.show();
							}
						}
					});
					
				}
				
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
