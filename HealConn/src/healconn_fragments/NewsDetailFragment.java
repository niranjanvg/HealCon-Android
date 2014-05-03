package healconn_fragments;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.healconn.R;

public class NewsDetailFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		return inflater.inflate(R.layout.fragment_news_detail, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Uri newsUri = NewsHomeFragment.newsUri;
		WebView webView = (WebView) getActivity().findViewById(R.id.news_webview);
		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl(newsUri.toString());

	}
}
