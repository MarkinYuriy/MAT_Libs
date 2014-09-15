package mat;

public interface IFesBes2 {
	String [] getContacts(String username, String[] socialNames);//assuming Google
	boolean shareByMail(String urlMatt, String[] contacts, String userName, String socialName);
	String [] getAuthorizedSocialNames(String username);
	boolean setToken(String username, String socialName,String current, String refresh);
}
