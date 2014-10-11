package mat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

@SuppressWarnings("serial")
public class Matt implements Serializable {
	mat.MattData data;
	ArrayList<Boolean> slots;

	public mat.MattData getData() {return data;}
	public void setData(mat.MattData data) {this.data = data;}
	public ArrayList<Boolean> getSlots() {return slots;}
	public void setSlots(ArrayList<Boolean> slots) {this.slots = slots;}

	static public String delimiter=",";

	public String mattToJSON() {
		String res=data.mattDataToJSON();
		if(slots!=null){
			res=res+','+"[";
			for(boolean it:slots){
				res+=(it?1:0)+delimiter;
			}
			res=res.substring(0,res.length()-1);
			res="{"+res+"]"+"}";
		}
		return res;
	}

	public String arrayList2JSON(){
		String res=null;
		if(slots!=null){
			res="[";
			for(boolean it:slots){
				res+=(it?1:0)+delimiter;
			}
			res=res.substring(0,res.length()-1);
			res=res+"]";
		}
		return res;
	}

	public String matt2browser(){
	 /*["Vasya",[Mon,Tue,Wen,Thu],[20.09,21.09,22.09,23.09]
	 ,[10:00,10:30,11:00,11:30,12:00]
	 ,[[0,1,0,0],[1,0,0,1],[0,0,1,1],[0,0,0,1]
	 ,[1,1,0,1]]]*/

		StringBuffer daysStr=null;
		String username=data.getName();
		Date startDate=data.getStartDate();
		int nDays=data.getnDays();
		int startHour=data.getStartHour();
		int endHour=data.getEndHour();
		int timeSlot=data.getTimeSlot();
		int timeSlots;
		int hours=0;
 
		if (!(startHour==endHour)) {
			if (startHour < endHour) {
				hours = (endHour - startHour) * 60;
			} else {
				hours = (24 - startHour + endHour) * 60;
			}
			timeSlots=hours/timeSlot;
		} else{
			hours = (24 - startHour + endHour) * 60;
			timeSlots=(hours/timeSlot)-1;
		}
 
		Calendar cal = new GregorianCalendar(Locale.US);
		cal.setTime(startDate);
   
		SimpleDateFormat dfDays=new SimpleDateFormat("EEE",Locale.US); 
		
		daysStr = new StringBuffer();
		daysStr.append('[');
		daysStr.append('"');
		daysStr.append(username);
		daysStr.append('"');
		daysStr.append(",[");
		daysStr.append(dfDays.format(cal.getTime())); 
		
		for(int i=1;i<nDays;i++){
			daysStr.append(delimiter);
			cal.add(Calendar.DAY_OF_WEEK, 1);
			daysStr.append(dfDays.format(cal.getTime()));
		}
		daysStr.append("],");
 
		cal.setTime(startDate);
		dfDays.applyPattern("dd.MM");
		daysStr.append("["+dfDays.format(cal.getTime()));
 
		for(int i=1;i<nDays;i++){
			daysStr.append(delimiter);
			cal.add(Calendar.DAY_OF_WEEK, 1);
			daysStr.append(dfDays.format(cal.getTime()));
		}
		daysStr.append("],");
 
		cal.setTime(startDate);
		cal.set(Calendar.HOUR_OF_DAY,startHour);
		cal.set(Calendar.MINUTE,0);
		dfDays.applyPattern("HH:mm");
		daysStr.append("["+dfDays.format(cal.getTime()));
 
		for(int i=0;i<timeSlots;i++){
			daysStr.append(delimiter);
			cal.add(Calendar.MINUTE, timeSlot);
			daysStr.append(dfDays.format(cal.getTime()));
		}
		daysStr.append("],");
 
		if(slots!=null){
			Object[] tmpArr=slots.toArray();
			daysStr.append("[");
			int count=0;
			for(int i=0;i<timeSlots;i++){
				daysStr.append("[");
				for(int j=0;j<nDays;j++){
					if((boolean) tmpArr[count].equals(true)){
						daysStr.append(1);}
					else{
						daysStr.append(0);
					}
					daysStr.append(',');
					++count;
				}
				daysStr.insert(daysStr.length()-1, "]");
			}
			int t=daysStr.length();
			daysStr.setLength(t-1);
			daysStr.append("]]");
		}
		return daysStr.toString();
	}
	
	public static ArrayList<Boolean> fromBrowser2ArrayList(String newTablJSON){
	  /*["Vasya",[Mon,Tue,Wen,Thu],[20.09,21.09,22.09,23.09]
	    ,[10:00,10:30,11:00,11:30,12:00]
	    ,[[0,1,0,0],[1,0,0,1],[0,0,1,1],[0,0,0,1]
	    ,[1,1,0,1]]]*/ //----parse it to ArrayList----
		String delimTmp="[[";
		ArrayList<Boolean> newTabList=null;
		if(!(newTablJSON==null)){
			newTabList=new ArrayList<Boolean>();
			int index1=newTablJSON.lastIndexOf(delimTmp);
			String strTmp=newTablJSON.substring(index1+1, newTablJSON.length()-2);
			String str4nDays=(String) strTmp.substring(strTmp.lastIndexOf("[")+1,strTmp.lastIndexOf("]"));
			str4nDays=str4nDays.replaceAll(",", "");
			int numDays=str4nDays.length();
			strTmp=strTmp.replace('[', ' ');
			strTmp=strTmp.replaceAll(",","").replaceAll("]", "").replaceAll(" ","");
			char[]buf=strTmp.toCharArray();
			Boolean e;
			int n=0;
			for(int t=0;t<numDays;++t){
				n=t;
				for(int m=0;m<(buf.length/numDays);m++){
					e=(buf[n]=='1'?true:false);
					newTabList.add(e);
					n=n+numDays;
				}
			}
		}
		return newTabList;}
	}