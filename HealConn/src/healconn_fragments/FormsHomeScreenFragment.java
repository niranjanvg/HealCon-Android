package healconn_fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.healconn.R;

public class FormsHomeScreenFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		return inflater.inflate(R.layout.fragment_forms, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		ImageButton bttFeedback = (ImageButton) getActivity().findViewById(R.id.button_feedback);
		ImageButton bttHistory = (ImageButton) getActivity().findViewById(R.id.button_history);
		ImageButton bttImmun = (ImageButton) getActivity().findViewById(R.id.button_immun);
		
		// Listener for button News
		bttFeedback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Switch in feedback form fragment
				FragmentTransaction fragmentTransaction = getFragmentManager()
						.beginTransaction();
				fragmentTransaction.replace(R.id.forms_fragment_container, 
						      new FeedbackFormFragment());
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}
		});

	}
}
