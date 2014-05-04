package healconn_fragments;

import healconn_core.LoginActivity;
import healconn_core.MainActivity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.healconn.R;


public class HomeScreenLogoFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_homescreenlogo, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		TextView logo = (TextView) getActivity().findViewById(R.id.logoTextView);
		logo.setTypeface(LoginActivity.robotoRegular);
		TextView subLogo = (TextView) getActivity().findViewById(R.id.subLogoTextView);
		subLogo.setTypeface(LoginActivity.robotoLightItalic);
	}
}