package journalDB;


public class Util {
	
	public static Boolean getInt(){
		return true;
	}
	
	public static String formatSecondaryAuthors(String author1){
		return "\"" + author1 + ";" + "\"";
	}
	
	public static String formatSecondaryAuthors(String author1, String author2){
		return "\"" + author1 + "; " + author2 + ";" + "\""; 
	}
	
	public static String formatSecondaryAuthors(String author1, String author2, String author3){
		return "\"" + author1 + "; " + author2 + "; " + author3 + ";" + "\"";
	}
}