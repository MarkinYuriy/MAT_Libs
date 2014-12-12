package mat;

import java.util.List;

public interface IBackConnector {
	List<Boolean> getSlots(String username, mat.MattData interval) ;
	void setMatCalendar(String username, List<mat.Matt> matts);
	void uploadMatt(String username, mat.Matt matt);
}
