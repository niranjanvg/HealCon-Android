package healconn_fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.healconn.R;
import com.parse.ParseUser;

public class ScheduleDateConfirmFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		return inflater.inflate(R.layout.fragment_dateconfirm, container, false);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		TextView dateConfirmText = (TextView) getActivity().findViewById(R.id.dateConfirmText);
		dateConfirmText.setText(ScheduleDatePickerFragment.selectedDatePublic);
	    
		/* back button click listener */
	    Button backButton = (Button) getActivity().findViewById(R.id.dataConfirmBack);
        backButton.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				FragmentTransaction fragmentTransaction = getFragmentManager()
						.beginTransaction();
				fragmentTransaction.replace(R.id.appointment_fragment_container, 
						      new ScheduleDatePickerFragment());
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}
		});
        
        // listener for button "No thanks"
        Button surveyNegative = (Button) getActivity().findViewById(R.id.button_survey_no);
        surveyNegative.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// replace with confirmation fragment
				FragmentTransaction fragmentTransaction = getFragmentManager()
						.beginTransaction();
				fragmentTransaction.replace(R.id.appointment_fragment_container, 
						      new ScheduleFinalConfirmFragment());
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();		
			}
		});
        
        // listener for button "Yes"
        Button surveyPositive = (Button) getActivity().findViewById(R.id.button_survey_yes);
        surveyPositive.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// when user clicks on the "yes" button, switch in the survey fragment
				FragmentTransaction fragmentTransaction = getFragmentManager()
						.beginTransaction();
				fragmentTransaction.replace(R.id.appointment_fragment_container, 
						      new ScheduleSurveyFormFragment());
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
				
			}
		});
	}
}
