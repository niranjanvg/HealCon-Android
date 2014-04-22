package com.example.healconn;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.parse.ParseException;
import com.parse.ParseFile;

public class ScheduleFinalConfirm extends Fragment {

	// private instance variable
	private ParseFile doctorPic;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		return inflater.inflate(R.layout.fragment_confirm_final, container, false);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		doctorPic = ScheduleDatePicker.doctorPic;
		// convert to byte array
		byte[] imgBytes = null;
		try {
			imgBytes = doctorPic.getData();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		// create drawable objects
		Bitmap bmp = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
		Drawable drawable =new BitmapDrawable(getResources(), bmp);
		// set doctor image
		ImageView docPic = (ImageView) getActivity().findViewById(R.id.scheduled_doctor);
		docPic.setImageDrawable(drawable);
		
	}
	
}
