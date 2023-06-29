package com.socialvideo.utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtility {
	
	
	
	
	public static int convertDurationStringToSeconds(String timestampStr) {
		int finalDuration = 0;
		String[] tokens = timestampStr.split(":");
		int hours = 0;
		int minutes = 0;
		int seconds =0;
		
		if(tokens.length==2) {
			 minutes = Integer.parseInt(tokens[0]);
			 seconds = Integer.parseInt(tokens[1]);
		} else if(tokens.length==3) {
			hours = Integer.parseInt(tokens[0]);
			minutes = Integer.parseInt(tokens[1]);
			seconds = Integer.parseInt(tokens[2]);
		}
		
			
		finalDuration = 3600 * hours + 60 * minutes + seconds;
		return finalDuration;
		
		
		
	}
	
    public Date tommorow() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +1);
        return cal.getTime();
}

    public static Date incrementMinutes(int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, +minutes);
        return cal.getTime();
    }
    
    
	
	
	
	public static String ConvertSecondToHHMMString(int secondtTime)
	{
	  TimeZone tz = TimeZone.getTimeZone("UTC");
	  SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
	  df.setTimeZone(tz);
	  String time = df.format(new Date(secondtTime*1000L));

	  return time;

	}

}
