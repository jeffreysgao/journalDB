package journalDB;

import java.util.ArrayList;

public class Reviewer extends Person {
	public Reviewer(int id){
		super(id, "reviewer");
		status();
	}
	
	public void status() {
		System.out.println("Reviewer status");
	}
	public void resign(){
		//remove themselves
	}
	

	
	public void review(Boolean isAccepted, ArrayList<String> input){
		try {
			int appr = Integer.parseInt(input.get(0));
			int clar = Integer.parseInt(input.get(1));
			int meth = Integer.parseInt(input.get(2));
			int contr = Integer.parseInt(input.get(3));
			if (Validation.validateRatings(appr, clar, meth, contr)){
				review(isAccepted, appr, clar, meth, contr);
			} else { 
				System.out.println("Ratings can only be between 1-10");
			}
		} catch(Exception e ){
			System.out.println("Please give proper type and number of arguments");
		}
	}

	private void review(Boolean isAccepted, int appr, int clar, int meth, int contr){
	}
	
	public void reviewAccept(ArrayList<String> input){
		//accept or reject
		//set values for clar, appr, method, contr, etc
		//must check to see that that the manuscript is in the reviewing phase
	}
	
	public void revieReject(ArrayList<String> input){
		
	}
	
	
}