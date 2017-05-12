package journalDB;

import java.util.ArrayList;

public class Validation {
	public Validation(){}
	
	public static Boolean validateAuthorFormat(String author){
		String[] splitName = author.split(",");
		if (splitName.length == 2){
			return true;
		} else {
			return false;
		}
	}

	public static Boolean validatePeriod(int period){
		return (period <= 4 && period >= 1);
		
	}
	public static Boolean validateYear(int year){
		return (year >= 1970 && year <= 2020);
	}
	
	public static Boolean validateLength(ArrayList<String> arrayList, int size){
		return  (arrayList.size() == size);
	}
	
	public static Boolean lengthAtLeast(ArrayList<String> arrayList, int size){
		return (arrayList.size() >= size);
	}
	
	public static Boolean validateRatings(int appr, int clar, int meth, int contr){
		return (isRating(appr) && isRating(clar) && isRating(meth) && isRating(contr));
	}
	
	private static Boolean isRating(int i) { 
		 return (i <= 10 && i >= 1);
	}
	
	public static Boolean validateSingleInteger(ArrayList<String> item){
		if (validateLength(item, 1)){
			try {
				Integer.parseInt(item.get(0));
				return true;
			} catch(Exception e ) {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public static Boolean validateDoubleIntegers(ArrayList<String> item){
		if (validateLength(item, 2)){
			try {
				Integer.parseInt(item.get(0));
				Integer.parseInt(item.get(1));
				return true;
			} catch(Exception e) { 
				return false;
			}
		} else {
			return false;
		}
	}

}
