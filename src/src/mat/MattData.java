package mat;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("serial")
public class MattData implements Serializable {

	String name;//name of MATT
	int mattId;//from DB (BES1)
	int nDays;//number of days
	Date startDate;
	int startHour;//0
	int endHour;//24-time slot
	int timeSlot; //in minutes
	String password; //if null the MATT is public
	HashMap<String, List<String>[]> sncalendars;//key - Social Network,
	//      value-two dimensional strings array, first dimension: 0-Upload, 1-Download
	//calendars null - no synch with calendars
	//first Example: Google for upload 2 calendars, for download 1 calendar
	// calendars=sncalendars.get("Google");
	//calendars[0] - contains two calendar names for upload
	//calendars[1] - contains one calendar for download
	//second Example - no download:
	//           calendars[1] is null
	public MattData(String name, int nDays, Date startDate, int startHour, int endHour, int timeSlot, String password) {
		this.mattId = 0;
		this.name = name;
		this.nDays = nDays;
		this.startDate = startDate;
		this.startHour = startHour;
		this.endHour = endHour;
		this.timeSlot = timeSlot;
		this.password = password;
		this.sncalendars = new HashMap<String, List<String>[]>();
	}
	public List<String> getUploadCalendars(String SN) {
		if(sncalendars!=null && sncalendars.get(SN)!=null)
			return sncalendars.get(SN)[0];
		return null;
	}
	public List<String> getDownloadCalendars(String SN) {
		if(sncalendars!=null && sncalendars.get(SN)!=null)
			return sncalendars.get(SN)[1];
		return null;
	}
	public String[] getUploadSN(){
		ArrayList<String> arrSN = new ArrayList<String>();
		Set<String> sn = null;
		if(sncalendars!=null){
			sn = sncalendars.keySet();
		}
		else return null;
		for(String s: sn){
			if(sncalendars.get(s)!=null && sncalendars.get(s)[0]!=null)
				arrSN.add(s);
		}
		String[] result = new String[arrSN.size()];
		result = arrSN.toArray(result);
		return result;
	}

	public String[] getDownloadSN(){
		ArrayList<String> arrSN = new ArrayList<String>();
		Set<String> sn = null;
		if(sncalendars!=null){
			sn = sncalendars.keySet();
		}
		else return null;
		for(String s: sn){
			if(sncalendars.get(s)!=null && sncalendars.get(s)[1]!=null)
				arrSN.add(s);
		}
		String[] result = new String[arrSN.size()];
		result = arrSN.toArray(result);
		return result;
	}
	
	public void setUploadCalendars(String SN, List<String> calendars) {
		if(sncalendars.containsKey(SN))
			sncalendars.get(SN)[0]=calendars;
		else{
			@SuppressWarnings("unchecked")
			List<String>[] arrlist = new List[2];
			arrlist[0] = calendars;
			arrlist[1] = null;
			sncalendars.put(SN, arrlist);
		}
	}
	public void setDownloadCalendars(String SN, List<String> calendars) {
		if(sncalendars.containsKey(SN))
			sncalendars.get(SN)[1]=calendars;
		else{
			@SuppressWarnings("unchecked")
			List<String>[] arrlist = new List[2];
			arrlist[1] = calendars;
			arrlist[0] = null;
			sncalendars.put(SN, arrlist);
		}
	}

	public int getMattId() {
		return mattId;
	}
	public void setMattId(int mattId) {
		this.mattId=mattId;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setnDays(int nDays) {
		this.nDays = nDays;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}
	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}
	public void setTimeSlot(int timeSlot) {
		this.timeSlot = timeSlot;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	public int getnDays() {
		return nDays;
	}
	public Date getStartDate() {
		return startDate;
	}
	public int getStartHour() {
		return startHour;
	}
	public int getEndHour() {
		return endHour;
	}
	public int getTimeSlot() {
		return timeSlot;
	}
	public String getPassword() {
		return password;
	}

	public Date dateFormat(){
		Date ddate = null;
		if(startDate != null){
			try {
				ddate=new SimpleDateFormat("dd/MM/yyyy").parse(startDate.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return ddate;
	}

	public String mattDataToJSON() {
		return "{"+
				'"'+"mattId"+'"'+":"+'"'+mattId+'"'+','+
				'"'+"name"+'"'+":"+'"'+name+'"'+','+
				'"'+"nDays"+'"'+':'+nDays+','+
				'"'+"startDate"+'"'+':'+'"'+dateFormat()+'"'+','+
				'"'+"startHour"+'"'+':'+'"'+startHour+'"'+','+
				'"'+"endHour"+'"'+':'+'"'+endHour+'"'+','+
				'"'+"timeSlot"+'"'+':'+timeSlot+','+
				'"'+"password"+'"'+':'+'"'+password+'"'+
				"}"
				;
	}
}
