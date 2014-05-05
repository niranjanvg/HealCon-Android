package healconn_fragments;


import java.util.ArrayList;
import java.util.HashMap;

import remote_service.NewsService;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.healconn.R;

public class NewsHomeFragment extends ListFragment {
	
	// private instance variable
	public static Uri newsUri;

	private String[] titles = NewsService.titles;

	private String[] urls = NewsService.urls;

	// Array of integers points to images stored in res/drawable folder
	private int[] thumbnails = NewsService.thumbnails;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {	
		return inflater.inflate(R.layout.fragment_news_home, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		ArrayList<HashMap<String,String>> newsTitles = new ArrayList<HashMap<String,String>>();
		for (int i = 0; i < titles.length; i++) {
			HashMap<String,String> newsTitle = new HashMap<String,String>();
			newsTitle.put("title", titles[i]);
			newsTitle.put("thumbnail", Integer.toString(thumbnails[i]));
			newsTitles.add(newsTitle);
		}
		String keys[] = {"thumbnail", "title"};
		int ids[] = {R.id.thumbnail,R.id.title};
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), newsTitles, R.layout.row_layout,
												keys, ids);		
		setListAdapter(adapter);

	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String url = urls[position];
		newsUri = Uri.parse(url);
		// replace fragment
		FragmentTransaction fragmentTransaction = getFragmentManager()
				.beginTransaction();
		fragmentTransaction.replace(R.id.news_fragment_container, 
				      new NewsDetailFragment());
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}
	
}
