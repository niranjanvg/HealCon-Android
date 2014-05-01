package entities;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/*
 * Description:
 * SurveyForm class 
 * Use as an instance variable in Appointment class
 * When users make an appointment, they can choose
 * whether or not to take an extra pre-diagnose survey
 * to help doctor better prepare for treatment
 * 
 * A SurveyForm in Parse should have following features:
 * 		
 * 		SurveyFormID
 * 		WhatIsWrong
 * 		Symptom
 * 		History
 * 		DrugUsing
 * 
 * Version: 1.0  Created: 04/18/2014
 */

@ParseClassName("SurveyForm")
public class SurveyForm extends ParseObject {
    
    public SurveyForm(){
    	// a default constructor is required
    }
    
    /**
     * Setter
     */
    public void setWhatIsWrong(String whatIsWrong){
    	put("WhatIsWrong", whatIsWrong);
    }
    public void setSymptom(String symptom){
    	put("Symptom", symptom);
    }
    public void setHistory(String history){
    	put("History", history);
    }
    public void setDrugUsing(String drugUsing){
    	put("DrugUsing", drugUsing);
    }
    
}
