package healconn_fragments;

import healconn_core.MainActivity;

import com.example.healconn.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class UserInfoFragment extends Fragment {
	
	private static String name;
	private static String department;
	private static String studentID;
	private static byte[] imgBytes;
	private Intent intent;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_userinfo, container, false);
	}	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		intent = MainActivity.intent;
		
		if (intent.getExtras() != null)
			displayUser();
		else {
			displayKnownUser();
		}
		
	}
	
	// displays an already known user
	private void displayKnownUser() {
		TextView nameText = (TextView) getActivity().findViewById(R.id.name);
		TextView depText = (TextView) getActivity().findViewById(R.id.department);
		TextView idText = (TextView) getActivity().findViewById(R.id.studentid);
		// check if user is admin
		if (name.equals("UHS")) {
			nameText.setText("Carnegie Mellon UHS");
			depText.setText("");
			idText.setText("");
			MainActivity.isAdmin = true;
		}
		else {
			nameText.setText("Name: " + name);
			depText.setText("Department: " + department);
			idText.setText("ID: " + studentID);
			MainActivity.isAdmin = false;
		}
		
		// display profile pic		
		Bitmap bmp = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
		Drawable drawable =new BitmapDrawable(getResources(), bmp);
		ImageView user = (ImageView) getActivity().findViewById(R.id.user);
		user.setImageDrawable(drawable);
	}

	// displays the right user information
	@SuppressLint("NewApi")
	private void displayUser() {
		// display name, department, studentID
		name = intent.getExtras().getString("name");
		department = intent.getExtras().getString("department");
		studentID = intent.getExtras().getString("studentID");
		TextView nameText = (TextView) getActivity().findViewById(R.id.name);
		TextView depText = (TextView) getActivity().findViewById(R.id.department);
		TextView idText = (TextView) getActivity().findViewById(R.id.studentid);
		// check if user is admin
		if (name.equals("UHS")) {
			nameText.setText("Carnegie Mellon UHS");
			depText.setText("");
			idText.setText("");
			MainActivity.isAdmin = true;
		}
		else {
			nameText.setText("Name: " + name);
			depText.setText("Department: " + department);
			idText.setText("ID: " + studentID);
			MainActivity.isAdmin = false;
		}
		
		// display user profile picture
		imgBytes = intent.getByteArrayExtra("userPic");
		Bitmap bmp = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
		Drawable drawable =new BitmapDrawable(getResources(), bmp);
		ImageView user = (ImageView) getActivity().findViewById(R.id.user);
		user.setImageDrawable(drawable);
	}
}
