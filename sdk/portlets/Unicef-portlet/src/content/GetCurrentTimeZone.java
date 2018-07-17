package content;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
 
public class GetCurrentTimeZone {
 
  public static void main(String[] args) throws ParseException {
    
    //get Calendar instance
    Calendar now = Calendar.getInstance();
    
    //get current TimeZone using getTimeZone method of Calendar class
//    TimeZone timeZone = now.getTimeZone();
//    Date date = new Date();
//    
//    System.out.println(date);
//    System.out.println(now.getTime());
    
    String date= "03/05/2018 10:28:32";
    Date _localdate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(date);  
	
	Date serverDate = new Date();
	
	System.out.println(_localdate);
	System.out.println(serverDate);
	
	float diff= Float.parseFloat(String.valueOf((_localdate.getTime()-serverDate.getTime())))/(60*60 * 1000);
	String _diff = new DecimalFormat("##.##").format(diff);
	System.out.println(_diff);
    
    //display current TimeZone using getDisplayName() method of TimeZone class
  
    
  }
}