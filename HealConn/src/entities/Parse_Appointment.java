package entities;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/*
 * Description:
 * Appointment class
 * Created when user click appointment button on main panel
 * an user object will be sent by intent and be used to create
 * an appointment object
 * The features in the appointment object will be sent to the 
 * backend(Parse) after they have been set and an appointment
 * has been made.
 * 
 * An appointment form in Parse should have following features:
 * 		
 * 		AppointmentID
 * 		StudentID
 *      DateID
 *      FormID
 * 
 * Version: 1.0  Created: 04/18/2014
 */

@ParseClassName("Appointment")
public class Parse_Appointment extends ParseObject {
	
	private boolean _isSurvey;
	
	public Parse_Appointment(){
		// a default constructor is required
	}
	
	/**
	 * setter
	 * @param userParseUser
	 */
	public void setUserId(ParseUser userParseUser){
		put("studentId", userParseUser.get("studentID")); 
	}
	public void setDate(Parse_Dates date){
		put("date", date.get("id"));
	}
	public void setFormId(Parse_SurveyForm surveyForm){
		put("SurveyFormID", surveyForm.get("id"));
	}
	public void setIsSurvey(boolean isSurvey){
		_isSurvey = isSurvey;
	}
	
	/**
	 * determine whether user chooses to take survey form or not
	 * @return true if user take survey, false if not
	 */
	public boolean isSurvey(){
		return _isSurvey;
	}
}
