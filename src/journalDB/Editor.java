package journalDB;

import java.util.ArrayList;

public class Editor extends Person{
	public Editor(int id){
		super(id, "editor");
	}
	
	public void status(){
		System.out.println("Editor status");
	}
	
	public void assign(ArrayList<String> input){
		
	}
	
	public boolean assign(int manuNum, int revId){
		// Check if the manuscript is assignable
		String statusQuery = String.format("SELECT MAN_STATUS FROM Manuscript WHERE MAN_ID = %1$s;", manuNum);
		ArrayList<ArrayList<String>> results = Query.execute(statusQuery);
		if (results != null && results.size() == 2 && results.get(0).size() == 1) {
			if (results.get(0).get(0).equals("MAN_STATUS")) {
				System.out.println(Integer.parseInt(results.get(1).get(0)));
				
				if (Integer.parseInt(results.get(1).get(0)) != 2) {
					System.out.println("Manuscript is not in the under review status");
					return false;
				}
			} else {
				System.out.println("Error in getting the status of manuscript " + manuNum);
				return false;
			}
		} else {
			System.out.println("Error in getting the status of manuscript " + manuNum);
			return false;
		}
		
		// Insert assignment
		String assnQuery = String.format("INSERT INTO Assignment (Manuscript_MAN_ID, Reviewer_Person_PERSON_ID, ASSIGN_DATE) " 
				+ "VALUES (%1$s, %2$s, NOW());", manuNum, revId);
		return Query.insert(assnQuery) >= 0;
	}
	
	public void reject(int manuNum){
		
	}
	
	public void accept(int manuNum){
		
	}
	
	public void typeset(int manuNum, int numPages){
		
	}
	
	public void schedule(int manuNum, int issue){
		
	}
	
	public void publish(int issue){
		
	}
	
	
}