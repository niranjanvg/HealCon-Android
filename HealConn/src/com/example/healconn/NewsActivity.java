package com.example.healconn;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class NewsActivity extends ListActivity {
	
	private String[] titles = {"Fake Meats, Finally, Tastes Like Chicken",
									 "Low Vitamin D Levels Linked to Disease in Two Big Studies",
									 "Exercising for Healthier Eyes",
									 "Exercises to Strengthen Bones",
									 "How a Warm-Up Routine Can Save Your Knees",
									 "Study Questions Fat and Heart Disease Link",
									 "Quick Gains After a Smoking Ban",
									 "From Dogs, Answers About Breast Cancer"};
	
	private String[] authors = {"Stephanie Storm","Anahad O'Connor","Gretchen Reynolds",
								  "Gretchen Reynolds", "Gretchen Reynolds", "Anahad O'Connor",
								  "Catherine Saint Louis", "Roni Caryn Rabin"};
	
	private String[] urls = {"http://www.nytimes.com/2014/04/03/business/meat-alternatives-on-the-plate-and-in-the-portfolio.html?_r=0",
							 "http://well.blogs.nytimes.com/2014/04/01/low-vitamin-d-levels-linked-to-disease-in-two-big-studies/",
							 "http://well.blogs.nytimes.com/2014/03/26/exercising-for-eye-health/",
							 "http://well.blogs.nytimes.com/2014/03/21/ask-well-exercises-to-strengthen-bones/",
							 "http://well.blogs.nytimes.com/2014/03/19/how-a-warm-up-routine-can-save-your-knees/",
							 "http://well.blogs.nytimes.com/2014/03/17/study-questions-fat-and-heart-disease-link/",
							 "http://well.blogs.nytimes.com/2014/03/31/quick-gains-after-a-ban/?_php=true&_type=blogs&_r=0",
							 "http://well.blogs.nytimes.com/2014/03/31/by-treating-dogs-helping-humans/"};
	
    // Array of integers points to images stored in res/drawable folder
    private int[] thumbnails = new int[]{
        R.drawable.news1,
        R.drawable.news2,
        R.drawable.news3,
        R.drawable.news4,
        R.drawable.news5,
        R.drawable.news6,
        R.drawable.news7,
        R.drawable.news8,
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		ArrayList<HashMap<String,String>> newsTitles = new ArrayList<HashMap<String,String>>();
		for (int i = 0; i < titles.length; i++) {
			HashMap<String,String> newsTitle = new HashMap<String,String>();
			newsTitle.put("title", titles[i]);
			newsTitle.put("author", authors[i]);
			newsTitle.put("thumbnail", Integer.toString(thumbnails[i]));
			newsTitles.add(newsTitle);
		}
		String keys[] = {"thumbnail", "title","author"};
		int ids[] = {R.id.thumbnail,R.id.title, R.id.author};
		SimpleAdapter adapter = new SimpleAdapter(this, newsTitles, R.layout.row_layout,
												keys, ids);		
		setListAdapter(adapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String url = urls[position];
		Intent intent = new Intent(this, NewsWebViewActivity.class);
		intent.setData(Uri.parse(url));
		startActivity(intent);
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
