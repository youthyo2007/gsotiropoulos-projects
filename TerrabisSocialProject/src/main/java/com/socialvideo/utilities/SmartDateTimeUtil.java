package com.socialvideo.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Days;

@SuppressWarnings("deprecation")
public class SmartDateTimeUtil {

	
	
	
	public  static String getDateFormatString(Date date){
    //SimpleDateFormat hourMinuteFormat = new SimpleDateFormat(" h:m a");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    return dateFormat.format(date);
}



/*private static String getDateString(Date date){
    SimpleDateFormat dateStringFormat = new SimpleDateFormat("EEE',' MMM d y',' h:m a");
    return dateStringFormat.format(date);
}
*/
private static boolean isToday (DateTime dateTime) {
       DateMidnight today = new DateMidnight();
       return today.equals(dateTime.toDateMidnight());
}

private static boolean isYesterday (DateTime dateTime) {
       DateMidnight yesterday = (new DateMidnight()).minusDays(1);
       return yesterday.equals(dateTime.toDateMidnight());
}

private static boolean is2DaysAgo (DateTime dateTime) {
    DateMidnight twodaysago = (new DateMidnight()).minusDays(2);
    return twodaysago.equals(dateTime.toDateMidnight());
}




private static boolean isThisWeek(DateTime dateTime) {
    DateMidnight thisWeek = (new DateMidnight()).minusDays(7);
    return thisWeek.isBefore(dateTime.toDateMidnight());
}

private static boolean isLastWeek(DateTime dateTime) {
	DateMidnight thisWeek = (new DateMidnight()).minusDays(7);
    DateMidnight lastWeek = (new DateMidnight()).minusDays(14);
    boolean result = lastWeek.isBefore(dateTime.toDateMidnight()) && thisWeek.isAfter(dateTime.toDateMidnight());  
    return result;
}



private static boolean isTomorrow(DateTime dateTime){
    DateMidnight tomorrow = (new DateMidnight()).plusDays(1);
       return tomorrow.equals(dateTime.toDateMidnight());
}
private static String getDayString(Date date) {
        SimpleDateFormat weekdayFormat = new SimpleDateFormat("EEE',' h:m a");
        String s;
        if (isToday(new DateTime(date)))
            s = "today";
        else if (isYesterday(new DateTime(date)))
            s = "yesterday," + getDateFormatString(date);
        else if(isTomorrow(new DateTime(date)))
            s = "tomorrow," +getDateFormatString(date);
        else if(is2DaysAgo(new DateTime(date)))
        	s = "2 days ago," +getDateFormatString(date);
        else if(isThisWeek(new DateTime(date)))
        	s = "this week," +getDateFormatString(date);
        else if(isLastWeek(new DateTime(date)))
        	s = "last week," +getDateFormatString(date);
        else
            s = getDateFormatString(date);
        return s;
}

public static String getDateString_shortAndSmart(Date date) {
        String s;
        DateTime nowDT = new DateTime();
        DateTime dateDT = new DateTime(date);
        int days = Days.daysBetween(dateDT, nowDT).getDays();   
        if (isToday(new DateTime(date)))
            s = "today,"+getDateFormatString(date);
        else if (days < 14)
            s = getDayString(date);
        else
            s = getDayString(date);
        return s;
}


public static String getDifferenceBtwTime(Date dateTime) {

    long timeDifferenceMilliseconds = new Date().getTime() - dateTime.getTime();
    long diffSeconds = timeDifferenceMilliseconds / 1000;
    long diffMinutes = timeDifferenceMilliseconds / (60 * 1000);
    long diffHours = timeDifferenceMilliseconds / (60 * 60 * 1000);
    long diffDays = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24);
    long diffWeeks = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 7);
    long diffMonths = (long) (timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 30.41666666));
    long diffYears = (long)(timeDifferenceMilliseconds / (1000 * 60 * 60 * 24 * 365));

    System.out.println("diffYears:"+diffYears+" diffMonths:"+diffMonths+" diffWeeks:"+diffWeeks+" diffDays:"+diffDays+" diffHours:"+diffHours+" diffMinutes:"+diffMinutes+" diffSeconds:"+diffSeconds);
    if(diffYears>=1)
    	 return diffYears + "y";
    else if(diffMonths>=1)
    	return diffMonths + "m";
    else if(diffWeeks>=1)
    	return diffWeeks + "w";
    else if(diffDays>=1)
    	return diffDays + "d";
    else if(diffHours>=1)
    	return diffHours + "h";
    else if(diffMinutes>=1)
    	return diffMinutes + "m";
    else if(diffSeconds>=1)
    	return diffSeconds + "s";
    else
    	  return "1s";

}


}