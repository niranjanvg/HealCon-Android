package remote_service;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationService {
	
	public static void setupGoogleMapService(GoogleMap mMap) {
	    LatLng UHS = new LatLng(40.444987, -79.943503);
	    mMap.setMyLocationEnabled(true);
	    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UHS, 17));
	    mMap.addMarker(new MarkerOptions()
	            .title("University Health Service Center")
	            .snippet("Carnegie Mellon University UHS")
	            .position(UHS));
	}

}
