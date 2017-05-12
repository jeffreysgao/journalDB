package journalDB;

import java.util.ArrayList;
import java.util.Scanner;

public class Author extends Person {
	public Author(int id){
		super(id, "author");
		status();
	}

	//# submitted, # under review, # rejected, # accepted, # in typesetting, # scheduled for publication, # published
	public void status(){
		int submitted = 0, underReview = 0, rejected = 0, accepted = 0, typesetting = 0, scheduled = 0, published = 0;
		
		String submittedQuery = String.format("SELECT COUNT(*) FROM Manuscript WHERE Author_Person_PERSON_ID = %1$s AND MAN_STATUS = 0;", this.id);
		if (getCount(submittedQuery) >= 0)
			submitted = getCount(submittedQuery);
		String revQuery = String.format("SELECT COUNT(*) FROM Manuscript WHERE Author_Person_PERSON_ID = %1$s AND MAN_STATUS = 2;", this.id);
		if (getCount(revQuery) >= 0)
			underReview = getCount(revQuery);
		String rejQuery = String.format("SELECT COUNT(*) FROM Manuscript WHERE Author_Person_PERSON_ID = %1$s AND MAN_STATUS = 1;", this.id);
		if (getCount(rejQuery) >= 0)
			rejected = getCount(rejQuery);
		String accQuery = String.format("SELECT COUNT(*) FROM Manuscript WHERE Author_Person_PERSON_ID = %1$s AND MAN_STATUS = 3 AND MAN_ID NOT IN (SELECT Manuscript_MAN_ID FROM Scheduling) AND MAN_NUMPAGES IS NULL;", this.id);
		if (getCount(accQuery) >= 0)
			accepted = getCount(accQuery);
		String typeQuery = String.format("SELECT COUNT(*) FROM Manuscript WHERE Author_Person_PERSON_ID = %1$s AND MAN_STATUS = 3 AND MAN_ID NOT IN (SELECT Manuscript_MAN_ID FROM Scheduling) AND MAN_NUMPAGES IS NOT NULL;", this.id);
		if (getCount(typeQuery) >= 0)
			typesetting = getCount(typeQuery);
		String schedQuery = String.format("SELECT COUNT(*) FROM Manuscript WHERE Author_Person_PERSON_ID = %1$s AND MAN_STATUS = 3 AND MAN_ID IN (SELECT Manuscript_MAN_ID FROM Scheduling);", this.id);
		if (getCount(schedQuery) >= 0)
			scheduled = getCount(schedQuery);
		String pubQuery = String.format("SELECT COUNT(*) FROM Manuscript WHERE Author_Person_PERSON_ID = %1$s AND MAN_STATUS = 4;", this.id);
		if (getCount(pubQuery) >= 0)
			published = getCount(pubQuery);
		
		String result = String.format("%1$s submitted, %2$s under review, %3$s rejected, %4$s accepted, %5$s typesetting, %6$s scheduled, %7$s published", 
				submitted, underReview, rejected, accepted, typesetting, scheduled, published);
		
		//produces a report of all the author's
		//manuscripts currently in the system where
		//they are the primary author
		System.out.println(result);
	}
	
	private int getCount(String query) {
		ArrayList<ArrayList<String>> results = Query.execute(query);
		if (results.size() != 2 || results.get(1).size() != 1) 
			return -1;
		
		return Integer.parseInt(results.get(1).get(0));
	}

	//TODO: Need to make sure that secondary authors are in the correct format
	public void submit(ArrayList<String> input){
		int RICode = -1;
		String secondaryAuthors = null;
		String filename = null;
		try {
			RICode = Integer.parseInt(input.get(2));
			switch(input.size()) {
			case (3) : 
				submit(input.get(0), input.get(1), RICode, secondaryAuthors, filename);
				break;
			case (4) :
				//title affil RICode author2 author3 author4 filename
				if (Validation.validateAuthorFormat(input.get(3))){
					secondaryAuthors = Util.formatSecondaryAuthors(input.get(3));
					submit(input.get(0), input.get(1), RICode, secondaryAuthors, filename);
				} else { 
					submit(input.get(0), input.get(1), RICode, secondaryAuthors, input.get(3));		
				}
				break;
			case(5) :
				if (Validation.validateAuthorFormat(input.get(3)) && Validation.validateAuthorFormat(input.get(4))){
					secondaryAuthors = Util.formatSecondaryAuthors(input.get(3), input.get(4));
					submit(input.get(0), input.get(1), RICode, secondaryAuthors, filename);
				} else if (Validation.validateAuthorFormat(input.get(3))){
					secondaryAuthors = Util.formatSecondaryAuthors(input.get(3));
					filename = input.get(4);
					submit(input.get(0), input.get(1), RICode, secondaryAuthors, filename);
				} else { 
					//error
					System.out.println("Please format your author names correctly such that \"<lastName>, <firstName>\"");
				}
				break;
			case(6) :
				if (Validation.validateAuthorFormat(input.get(3)) && Validation.validateAuthorFormat(input.get(4)) && Validation.validateAuthorFormat(input.get(5))){
					secondaryAuthors = Util.formatSecondaryAuthors(input.get(3), input.get(4), input.get(5));
					submit(input.get(0), input.get(1), RICode, secondaryAuthors, filename);
				} else if (Validation.validateAuthorFormat(input.get(3)) && Validation.validateAuthorFormat(input.get(4))){
					secondaryAuthors = Util.formatSecondaryAuthors(input.get(3), input.get(4));
					filename = input.get(5);
					submit(input.get(0), input.get(1), RICode, secondaryAuthors, filename);
				} else {
					System.out.println("Please format your author names correctly such that \"<lastName>, <firstName>\"");
				}
				break;
			case(7) :
				if (Validation.validateAuthorFormat(input.get(3)) && Validation.validateAuthorFormat(input.get(4)) && Validation.validateAuthorFormat(input.get(5))){
					secondaryAuthors = Util.formatSecondaryAuthors(input.get(3), input.get(4), input.get(5));
					filename = input.get(6);
					submit(input.get(0), input.get(1), RICode, secondaryAuthors, filename);
				} else {
					System.out.println("Please format your author names correctly such that \"<lastName>, <firstName>\"");
				}
				break;
			default :
				System.out.println("Please enter the proper number of arguments");
			}
		} catch(Exception e ){
			System.out.println("Please provide proper arguments");
		}

	}
	
	// returns the id of the manuscript that was submitted
	public int submit(String title, String affil, int RICode, String secondaryAuthors, String filename){
		if (title == null || affil == null)
			return -1;
		
		String sQuery = String.format("INSERT INTO Manuscript (MAN_STATUS, MAN_DATE, "
				+ " MAN_TITLE, Area_AREA_ID, Author_Person_PERSON_ID, MAN_SECONDARY_AUTHORS)"
				+ " VALUES (0, NOW(), \"%1$s\", %2$s, %3$s", title, RICode, this.id);
		if (secondaryAuthors == null)
			sQuery += ", null);";
		else
			sQuery += ", " + secondaryAuthors + ");";
		int id = Query.insert(sQuery);
		if (id > 0) {
			System.out.println("Successfully submitted manuscript id #" + id);
		} else {
			System.out.println("Could not submit");
		}
		
		return id;
	}

	public void retract(ArrayList<String> input){
		try { 
			int id = Integer.parseInt(input.get(0));
			System.out.println("Are you sure? y/n");
			Scanner scanner = new Scanner(System.in);
			String readString = scanner.nextLine();

			Boolean flag = true;
			while (flag){
				if (readString.compareTo("y") == 0 || readString.compareTo("yes") == 0 || readString.compareTo("Y") == 0){
					flag = false;
					System.out.println("Retracting manuscript");
					retract(id);
					break;
				} else if (readString.compareTo("n") == 0 || readString.compareTo("no") == 0 || readString.compareTo("N") == 0){
					flag = false;
					System.out.println("Cancelling retraction");
					break;
				} else {
					flag = true;
				}
				
		        if (readString.isEmpty()) {
		            System.out.println("");
		        }
		        if (scanner.hasNextLine()) {
		            readString = scanner.nextLine();
		        } else {
		            readString = null;
		        }
			}
			scanner.close();
		} catch (Exception e ){
			System.out.println("Please provide the proper number of arguments to retract manuscript");
		}
	}
	
	public boolean retract(int manId){
		// check if a manuscript is assigned to this author
		String cQuery = String.format("SELECT COUNT(*) FROM Manuscript WHERE MAN_ID = %1$s AND Author_Person_PERSON_ID = %2$s;", manId, this.id);
		if (getCount(cQuery) != 1) {
			System.out.println("You are not the author of this manuscript");
			return false;
		} 
		
		// check if a manuscript is published yet
		int[] statuses = {4};
		if (checkStatus(statuses, manId)) {
			System.out.println("Manuscript has already been published");
			return false;
		}
		
		// if it hasn't been published, remove it from the database
		String dQuery = String.format("DELETE FROM Scheduling WHERE Manuscript_MAN_ID = %1$s;", manId);
		if (Query.insert(dQuery) < 0) {
			System.out.println("Error unscheduling manuscript");
			return false;
		}
		String rQuery = String.format("DELETE FROM Rating WHERE Assignment_Manuscript_MAN_ID = %1$s;", manId);
		int result = Query.insert(rQuery);
		if (result < 0) {
			System.out.println(result + "");
			System.out.println("Error deleting ratings for manuscript");
			return false;
		}
		String aQuery = String.format("DELETE FROM Assignment WHERE Manuscript_MAN_ID = %1$s;", manId);
		if (Query.insert(aQuery) < 0) {
			System.out.println("Error deleting assignments for manuscript");
			return false;
		}
		String mQuery = String.format("DELETE FROM Manuscript WHERE MAN_ID = %1$s;", manId);
		if (Query.insert(mQuery) < 0) {
			System.out.println("Error deleting manuscript");
			return false;
		}
		
		System.out.println("Deleted manuscript id #" + manId);
		return true;
	}
	
	// Helper method to check if statuses are in a given set
	private boolean checkStatus(int[] statuses, int manuNum) {
		String statusQuery = String.format("SELECT MAN_STATUS FROM Manuscript WHERE MAN_ID = %1$s;", manuNum);
		ArrayList<ArrayList<String>> results = Query.execute(statusQuery);
		if (results != null && results.size() == 2 && results.get(0).size() == 1) {
			if (results.get(0).get(0).equals("MAN_STATUS")) {
				for (int i = 0; i < statuses.length; i++) {
					if (Integer.parseInt(results.get(1).get(0)) == statuses[i])
						return true;
				}
			} else {
				System.out.println("Error in getting the status of manuscript " + manuNum);
				return false;
			}
		} else {
			System.out.println("Error in getting the status of manuscript " + manuNum);
			return false;
		}
		return false;
	}
}
