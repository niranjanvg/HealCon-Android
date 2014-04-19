package entities;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/*
 * Dates form in Parse should have following features:
 * 		
 * 		DateID
 * 		Dates
 * 		Availability
 * 		DoctorName
 */
@ParseClassName("Dates")
public class Dates extends ParseObject {
    
	public Dates(){
    	// a default constructor is required	
    }
    
    public void setDates(String date){
    	put("Dates", date);
    }
    
    public void setAvailability(String availability){
    	put("Availability", availability);
    }
    
    public void setDoctorName(String doctorName){
    	put("DoctorName", doctorName);
    }
}
