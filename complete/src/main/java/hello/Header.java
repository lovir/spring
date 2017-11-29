package hello;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Header {

	private String responseTime;
	private String successYN;

	public Header(String responseTime, String successYN) {
		// TODO Auto-generated constructor stub
		 this.responseTime = responseTime;
		 this.successYN = successYN;
	}
	
	public String getResponseTime() {
		Calendar cal = new GregorianCalendar();
		
	    return cal.getTime().toString();
	}
	 
	public String getSuccessYN() {
	    return "Y";
	} 
	
	public Header() {}
	
}
