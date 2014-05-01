package healconn_fragments;

import com.example.healconn.R;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ScheduleSurveyFormFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		return inflater.inflate(R.layout.fragment_survey_form, container, false);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// set listener for "submit" button
		Button bttSubmit = (Button) getActivity().findViewById(R.id.button_submit);
		bttSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// when user clicks on submit, switch in the confirm fragment
				FragmentTransaction fragmentTransaction = getFragmentManager()
						.beginTransaction();
				fragmentTransaction.replace(R.id.appointment_fragment_container, 
						      new ScheduleFinalConfirmFragment());
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();		
			}
		});
	}
}
