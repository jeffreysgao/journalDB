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
	

}
