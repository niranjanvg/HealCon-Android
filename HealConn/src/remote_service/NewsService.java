package remote_service;

import com.example.healconn.R;

public class NewsService {
	// news website titles
	public static final String[] titles = {"Daily Health News from CNN",
										   "New York Times Well Blog",
										   "CMU Health Service",
										   "Health.com",
										   "BBC Health News",
										   "ABC.GO Health News",
										   "CBS Health News"};
	// news website urls
	public static final String[] urls = {"http://www.cnn.com/HEALTH/",
										 "http://well.blogs.nytimes.com",
										 "http://www.cmu.edu/health-services/",
										 "http://www.health.com/health/",
										 "http://www.bbc.com/news/health/",
										 "http://abcnews.go.com/Health/",
										 "http://www.cbsnews.com/health/"};
	
	// Array of integers points to images stored in res/drawable folder
	public static final int[] thumbnails = new int[]{
			R.drawable.news1,
			R.drawable.news2,
			R.drawable.news3,
			R.drawable.news4,
			R.drawable.news5,
			R.drawable.news6,
			R.drawable.news7,
	};
}
