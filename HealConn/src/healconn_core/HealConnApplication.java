package healconn_core;

import android.app.Application;

import com.parse.Parse;
import com.parse.PushService;

public class HealConnApplication extends Application{

	@Override
	public void onCreate() {
		  super.onCreate();
		  Parse.initialize(this, "N3aY3s8I6pm7ILdYBpCkznVpA9Q4eADyI3tgCTPK", "0TN1fnjU3tz2f5JoZYAYlL2CfdBV8wkRTpO6vFNE");
	}
	
}
