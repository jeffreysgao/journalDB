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
		String query = "UPDATE `Reviewer` SET REV_ISACTIVE = false WHERE Person_PERSON_ID = " + this.id;
		Query.execute(query);
	}
	
	public void review(Boolean isAccepted, ArrayList<String> input){
		try {
			int manId = Integer.parseInt(input.get(0));
			int appr = Integer.parseInt(input.get(1));
			int clar = Integer.parseInt(input.get(2));
			int meth = Integer.parseInt(input.get(3));
			int contr = Integer.parseInt(input.get(4));
			if ( Validation.validateRatings(appr, clar, meth, contr)){
				review(isAccepted, manId, appr, clar, meth, contr);
			} else { 
				System.out.println("Ratings can only be between 1-10");
			}
		} catch(Exception e ){
			System.out.println(input);
			System.out.println("Please give proper type and number of arguments");
		}
	}

	private void review(Boolean isAccepted, int manId, int appr, int clar, int meth, int contr){	
		int intAccepted = isAccepted ? 1 : 0;
		String query = "INSERT INTO Rating (Assignment_Reviewer_Person_PERSON_ID, Assignment_Manuscript_MAN_ID, RATING_APPR, RATING_CLAR, RATING_METH, RATING_CONTR, RATING_REC) " + 
				"VALUES (" + this.id + ", " + manId + ", " + appr + ", " + clar + ", " + meth + ", " + contr + ", " + intAccepted +");";
		System.out.println(query);
		Query.insert(query);
	}	
}