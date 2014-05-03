package healconn_core;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.healconn.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends Activity {
	
	private EditText username;
	private EditText password;
	private EditText email;
	private Button btt_signUp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		
		btt_signUp = (Button) findViewById(R.id.button_sign_up);
		btt_signUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				username = (EditText) findViewById(R.id.username_field); 
				password = (EditText) findViewById(R.id.password_field);
				email = (EditText) findViewById(R.id.email_field);
				// check for format
				boolean formatOK = true;
				if (formatOK) {
					// create a new user
					ParseUser newUser = new ParseUser();
					newUser.setUsername(username.getText().toString());
					newUser.setPassword(password.getText().toString());
					newUser.setEmail(email.getText().toString());
						
					newUser.signUpInBackground(new SignUpCallback() {
						@Override
						public void done(ParseException e) {
							if (e == null) {
								// Success
								// alert user that the login is unsuccessful
								AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
								builder.setMessage("Successfully signed up one new user! ");
								builder.setTitle("Congratulations!");
								builder.setPositiveButton(android.R.string.ok, null);
								AlertDialog dialog = builder.create();
								dialog.show();
							}
							else {
								// Display Error Message via Dialog
							}
						}
					});
				}
				
			}
		});
	}

}
