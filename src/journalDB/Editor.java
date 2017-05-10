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
		if (Validation.validateDoubleIntegers(input)){
			assign(Integer.parseInt(input.get(0)), Integer.parseInt(input.get(2)));
		} else { 
			System.out.println("Please provide two options <manu#> and <reviewerId> such that they are both integers");
		}
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
	
	public void reject(ArrayList<String> input){
		if (Validation.validateSingleInteger(input)) { 
			reject(Integer.parseInt(input.get(0)));
		} else {
			System.out.println("Please provide valid integer arugment");
		}
	}
	public void reject(int manuNum){
		
	}
	
	public void accept(ArrayList<String> input){
		if (Validation.validateSingleInteger(input)){
			accept(Integer.parseInt(input.get(0)));
		} else {
			System.out.println("Please provide valid integer arugment");
		}
	}
	public void accept(int manuNum){
		
	}
	
	public void typeset(ArrayList<String> input){
		if (Validation.validateDoubleIntegers(input)){
			typeset(Integer.parseInt(input.get(0)), Integer.parseInt(input.get(1)));
		} else {
			System.out.println("Please provide a valid integer <manu#> and number of page");
		}
	}
	public void typeset(int manuNum, int numPages){
		
	}
	
	public void schedule(ArrayList<String> input){
		if (Validation.validateDoubleIntegers(input)){
			typeset(Integer.parseInt(input.get(0)), Integer.parseInt(input.get(1)));
		} else {
			System.out.println("Please provide a valid integer <manu#> and issue number");
		}
	}
	public void schedule(int manuNum, int issue){
		
	}
	
	public void publish(ArrayList<String> input){
		if (Validation.validateDoubleIntegers(input)){
			publish(Integer.parseInt(input.get(0)));
		} else { 
			System.out.println("Please provide a valid integer issue number");
		}
	}
	public void publish(int issue){
		
	}
	
	
}