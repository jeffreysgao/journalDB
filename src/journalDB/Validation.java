package journalDB;

import java.util.ArrayList;

public class Validation {
	public Validation(){}
	
	public static Boolean validateRole(){
		return true;
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
