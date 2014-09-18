package mat;

import java.io.IOException;
import java.util.List;

public interface IBes1Bes2 {
	List<Boolean> getSlots(String username, String [] snNames,
			mat.MattData interval) throws IOException;
	void setMatCalendar(String username,String [] snNames,List<mat.Matt> matts );
	
}
