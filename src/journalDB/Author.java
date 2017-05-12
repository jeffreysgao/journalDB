package journalDB;

import java.util.ArrayList;

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
		try {
			RICode = Integer.parseInt(input.get(2));
			switch(input.size()) {
			case (4) :
				submit(input.get(0), input.get(1), RICode, secondaryAuthors, input.get(3));
				System.out.println("RICode is not an integer");
				break;
			case(5) :
				secondaryAuthors = input.get(3);
				submit(input.get(0), input.get(1), RICode, secondaryAuthors, input.get(4));
				break;
			case(6) :
				secondaryAuthors = input.get(3) + input.get(4);
				submit(input.get(0), input.get(1), RICode, secondaryAuthors, input.get(5));
				break;
			case(7) :
				secondaryAuthors = input.get(3) + input.get(4) + input.get(5);
				submit(input.get(0), input.get(1), RICode, secondaryAuthors, input.get(6));
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
		return Query.insert(sQuery);
	}

	public void retract(ArrayList<String> input){
		try {
			retract(Integer.parseInt(input.get(1)));
		} catch (Exception e ){
			System.out.println("Please provide the proper number of arguments to retract manuscript");
		}
	}
	
	public void retract(int manID){
		
	}
}
