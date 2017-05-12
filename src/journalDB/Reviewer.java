package journalDB;

import java.util.ArrayList;

public class Reviewer extends Person {
	public Reviewer(int id){
		super(id, "reviewer");
		status();
	}
	
	public void status() {
		int submitted = 0, underReview = 0, rejected = 0, accepted = 0, typesetting = 0, scheduled = 0, published = 0;
		
		String revQuery = String.format("SELECT COUNT(*) FROM Assignment JOIN Manuscript ON Manuscript_MAN_ID = MAN_ID"
				+ " WHERE Reviewer_Person_PERSON_ID = %1$s AND MAN_STATUS = 2;", this.id);
		if (getCount(revQuery) >= 0)
			underReview = getCount(revQuery);
		String rejQuery = String.format("SELECT COUNT(*) FROM Assignment JOIN Manuscript ON Manuscript_MAN_ID = MAN_ID"
				+ " WHERE Reviewer_Person_PERSON_ID = %1$s AND MAN_STATUS = 1;", this.id);
		if (getCount(rejQuery) >= 0)
			rejected = getCount(rejQuery);
		String accQuery = String.format("SELECT COUNT(*) FROM Assignment JOIN Manuscript ON Manuscript_MAN_ID = MAN_ID"
				+ " WHERE Reviewer_Person_PERSON_ID = %1$s AND MAN_STATUS = 3 AND MAN_ID NOT IN (SELECT Manuscript_MAN_ID FROM Scheduling)"
				+ " AND MAN_NUMPAGES IS NULL;", this.id);
		if (getCount(accQuery) >= 0)
			accepted = getCount(accQuery);
		String typeQuery = String.format("SELECT COUNT(*) FROM Assignment JOIN Manuscript ON Manuscript_MAN_ID = MAN_ID"
				+ " WHERE Reviewer_Person_PERSON_ID = %1$s AND MAN_STATUS = 3 AND MAN_ID NOT IN (SELECT Manuscript_MAN_ID FROM Scheduling)"
				+ " AND MAN_NUMPAGES IS NOT NULL;", this.id);
		if (getCount(typeQuery) >= 0)
			typesetting = getCount(typeQuery);
		String schedQuery = String.format("SELECT COUNT(*) FROM Assignment JOIN Manuscript ON Manuscript_MAN_ID = MAN_ID"
				+ " WHERE Reviewer_Person_PERSON_ID = %1$s AND MAN_STATUS = 3 AND MAN_ID IN (SELECT Manuscript_MAN_ID FROM Scheduling);", this.id);
		if (getCount(schedQuery) >= 0)
			scheduled = getCount(schedQuery);
		String pubQuery = String.format("SELECT COUNT(*) FROM Assignment JOIN Manuscript ON Manuscript_MAN_ID = MAN_ID"
				+ " WHERE Reviewer_Person_PERSON_ID = %1$s AND MAN_STATUS = 4;", this.id);
		if (getCount(pubQuery) >= 0)
			published = getCount(pubQuery);
		
		String result = String.format("%1$s submitted, %2$s under review, %3$s rejected, %4$s accepted, %5$s typesetting, %6$s scheduled, %7$s published", 
				submitted, underReview, rejected, accepted, typesetting, scheduled, published);
		
		//produces a report of all the reviewer's
		//manuscripts currently in the system where
		//they are the primary reviewer
		System.out.println(result);
	}
	
	public void getStatus() {
		String manQuery = String.format("SELECT * FROM Manuscript WHERE MAN_ID IN"
				+ " (SELECT Manuscript_MAN_ID FROM Assignment WHERE Reviewer_Person_PERSON_ID = %1$s) ORDER BY MAN_STATUS, MAN_ID;" , this.id);
		ArrayList<ArrayList<String>> results = Query.execute(manQuery);
		if (results.size() < 2) {
			System.out.println("Error getting assigned manuscripts");
			return;
		} else {
			System.out.println("ASSIGNED MANUSCRIPTS: ");
		}
		
		for (int i = 1; i < results.size(); i++) {
			String row = "\t" + i + ". ";
			for (int j = 0; j < results.get(0).size(); j++) {
				row += String.format("%1$s: %2$s\t", results.get(0).get(j), results.get(i).get(j));
			}
			System.out.println(row);
		}
	}
	
	public boolean resignation(){
		String query = "UPDATE `Reviewer` SET REV_ISACTIVE = false WHERE Person_PERSON_ID = " + this.id;
		// value returned should be > 0 if at least one row is updated
		return Query.insert(query) >= 0;
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

	public boolean review(Boolean isAccepted, int manId, int appr, int clar, int meth, int contr){	
		// check if manuscript is assigned and unreviewed by you
		String manQuery = String.format("SELECT COUNT(*) FROM Assignment WHERE Manuscript_MAN_ID = %1$s && Reviewer_Person_PERSON_ID = %2$s;"
				, manId, this.id);
		if (getCount(manQuery) <= 0) {
			System.out.println("You are not assigned to this manuscript");
			return false;
		}
		
		String ratQuery = String.format("SELECT COUNT(*) FROM Rating WHERE Assignment_Reviewer_Person_PERSON_ID = %1$s AND "
				+ "Assignment_Manuscript_MAN_ID = %2$s;", this.id, manId);
		if (getCount(ratQuery) > 0) {
			System.out.println("You have already reviewed this manuscript");
			return false;
		}
			
		int intAccepted = isAccepted ? 1 : 0; //1 is accepted, 0 not accepted
		String query = "INSERT INTO Rating (Assignment_Reviewer_Person_PERSON_ID, Assignment_Manuscript_MAN_ID, RATING_APPR, RATING_CLAR, RATING_METH, RATING_CONTR, RATING_REC) " + 
				"VALUES (" + this.id + ", " + manId + ", " + appr + ", " + clar + ", " + meth + ", " + contr + ", " + intAccepted +");";
		return Query.insert(query) >= 0;
	}	
	
	private int getCount(String query) {
		ArrayList<ArrayList<String>> results = Query.execute(query);
		if (results.size() != 2 || results.get(1).size() != 1) 
			return -1;
		
		return Integer.parseInt(results.get(1).get(0));
	}
}