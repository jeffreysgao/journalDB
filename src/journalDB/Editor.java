package journalDB;

import java.util.ArrayList;

public class Editor extends Person{
	public Editor(int id){
		super(id, "editor");
		status();
	}

	public void status(){
		int submitted = 0, underReview = 0, rejected = 0, accepted = 0, typesetting = 0, scheduled = 0, published = 0;
		
		String submittedQuery = "SELECT COUNT(*) FROM Manuscript WHERE MAN_STATUS = 0;";
		if (getCount(submittedQuery) >= 0)
			submitted = getCount(submittedQuery);
		String revQuery = "SELECT COUNT(*) FROM Manuscript WHERE MAN_STATUS = 2;";
		if (getCount(revQuery) >= 0)
			underReview = getCount(revQuery);
		String rejQuery = "SELECT COUNT(*) FROM Manuscript WHERE MAN_STATUS = 1;";
		if (getCount(rejQuery) >= 0)
			rejected = getCount(rejQuery);
		String accQuery = "SELECT COUNT(*) FROM Manuscript WHERE MAN_STATUS = 3 AND MAN_ID NOT IN (SELECT Manuscript_MAN_ID FROM Scheduling) AND MAN_NUMPAGES IS NULL;";
		if (getCount(accQuery) >= 0)
			accepted = getCount(accQuery);
		String typeQuery ="SELECT COUNT(*) FROM Manuscript WHERE MAN_STATUS = 3 AND MAN_ID NOT IN (SELECT Manuscript_MAN_ID FROM Scheduling) AND MAN_NUMPAGES IS NOT NULL;";
		if (getCount(typeQuery) >= 0)
			typesetting = getCount(typeQuery);
		String schedQuery = "SELECT COUNT(*) FROM Manuscript WHERE MAN_STATUS = 3 AND MAN_ID IN (SELECT Manuscript_MAN_ID FROM Scheduling);";
		if (getCount(schedQuery) >= 0)
			scheduled = getCount(schedQuery);
		String pubQuery = "SELECT COUNT(*) FROM Manuscript WHERE MAN_STATUS = 4;";
		if (getCount(pubQuery) >= 0)
			published = getCount(pubQuery);
		
		String result = String.format("%1$s submitted, %2$s under review, %3$s rejected, %4$s accepted, %5$s typesetting, %6$s scheduled, %7$s published", 
				submitted, underReview, rejected, accepted, typesetting, scheduled, published);
		
		//produces a report of all the
		//manuscripts currently in the system r
		System.out.println(result);
	}
	
	private int getCount(String query) {
		ArrayList<ArrayList<String>> results = Query.execute(query);
		if (results.size() != 2 || results.get(1).size() != 1) 
			return -1;
		
		return Integer.parseInt(results.get(1).get(0));
	}
	
	public void getStatus() {
		String manQuery = String.format("SELECT * FROM Manuscript ORDER BY MAN_STATUS, MAN_ID;" , this.id);
		ArrayList<ArrayList<String>> results = Query.execute(manQuery);
		if (results.size() < 2) {
			System.out.println("Error getting assigned manuscripts");
			return;
		} else {
			System.out.println("ALL MANUSCRIPTS: ");
		}
		
		for (int i = 1; i < results.size(); i++) {
			String row = "\t" + i + ". ";
			for (int j = 0; j < results.get(0).size(); j++) {
				row += String.format("%1$s: %2$s\t", results.get(0).get(j), results.get(i).get(j));
			}
			System.out.println(row);
		}
	}

	// Assign
	public void assign(ArrayList<String> input){
		if (Validation.validateDoubleIntegers(input)){
			assign(Integer.parseInt(input.get(0)), Integer.parseInt(input.get(1)));
		} else {
			System.out.println("Please provide only two options <manu#> and <reviewerId> such that they are both integers");
		}
	}

	public boolean assign(int manuNum, int revId){
		// Check if the manuscript is assignable
		int[] statuses = {0, 2};
		if (!checkStatus(statuses, manuNum))
			return false;
		
		// Make sure that the manuscript is under review
		String sQuery = String.format("UPDATE Manuscript SET MAN_STATUS = 2 WHERE MAN_ID = %1$s;", manuNum);
		if (Query.insert(sQuery) < 0) {
			System.out.println("Error updating manuscript status");
			return false;
		}

		// Insert assignment
		String assnQuery = String.format("INSERT INTO Assignment (Manuscript_MAN_ID, Reviewer_Person_PERSON_ID, ASSIGN_DATE) "
				+ "VALUES (%1$s, %2$s, NOW());", manuNum, revId);
		return Query.insert(assnQuery) >= 0;
	}

	// Reject
	public void reject(ArrayList<String> input){
		if (Validation.validateSingleInteger(input)) {
			reject(Integer.parseInt(input.get(0)));
		} else {
			System.out.println("Please provide one valid integer arugment");
		}
	}

	public boolean reject(int manuNum){
		// Check if manuscript is rejectable
		int[] statuses = {0, 2};
		if (!checkStatus(statuses, manuNum))
			return false;

		// Reject manuscript
		String rQuery = String.format("UPDATE Manuscript SET MAN_STATUS = 1, MAN_LASTUPDATED = NOW() WHERE MAN_ID = %1$s;", manuNum);
		return Query.insert(rQuery) >= 0;
	}

	// Accept
	public void accept(ArrayList<String> input){
		if (Validation.validateSingleInteger(input)){
			accept(Integer.parseInt(input.get(0)));
		} else {
			System.out.println("Please provide valid integer arugment");
		}
	}

	public boolean accept(int manuNum){
		// Check if manuscript is acceptable
		int[] statuses = {2};
		if (!checkStatus(statuses, manuNum))
			return false;

		// Accept manuscript
		String accQuery = String.format("UPDATE Manuscript SET MAN_STATUS = 3, MAN_LASTUPDATED = NOW() WHERE MAN_ID = %1$s;", manuNum);
		return Query.insert(accQuery) >= 0;
	}

	// Typeset
	public void typeset(ArrayList<String> input){
		if (Validation.validateDoubleIntegers(input)){
			typeset(Integer.parseInt(input.get(0)), Integer.parseInt(input.get(1)));
		} else {
			System.out.println("Please provide a valid integer <manu#> and number of page");
		}
	}

	public boolean typeset(int manuNum, int numPages){
		// Check if manuscript is typesettable
		int[] statuses = {3};
		if (!checkStatus(statuses, manuNum))
			return false;

		// Typeset manuscript
		String tQuery = String.format("UPDATE Manuscript SET MAN_NUMPAGES = %1$s WHERE MAN_ID = %2$s;", numPages, manuNum);
		return Query.insert(tQuery) >= 0;
	}

	// Schedule
	public void schedule(ArrayList<String> input){
		if (Validation.validateDoubleIntegers(input)){
			typeset(Integer.parseInt(input.get(0)), Integer.parseInt(input.get(1)));
		} else {
			System.out.println("Please provide a valid integer <manu#> and issue number");
		}
	}

	public boolean schedule(int manuNum, int issue){
		// Check if manuscript is schedulable
		int[] statuses = {3};
		if (!checkStatus(statuses, manuNum))
			return false;


		// Check if issue exists
		String iQuery = String.format("SELECT COUNT(*) FROM Issue WHERE ISS_ID = %1$s;", issue);
		ArrayList<ArrayList<String>> results = Query.execute(iQuery);
		if (results != null && results.size() == 2 && results.get(0).size() == 1) {
			if (results.get(0).get(0).equals("COUNT(*)")) {
				if (Integer.parseInt(results.get(1).get(0)) != 1) {
					System.out.println("Can't find issue " + issue);
					return false;
				}
			}
		} else {
			System.out.println("Error checking if issue exists");
			return false;
		}
		
		// Check if manuscript has been scheduled already 
		String existsQuery = String.format("SELECT COUNT(*) FROM Scheduling WHERE Manuscript_MAN_ID = %1$s;", manuNum);

		results = Query.execute(existsQuery);
		if (results != null && results.size() == 2 && results.get(0).size() == 1) {
			if (results.get(0).get(0).equals("COUNT(*)")) {
				if (Integer.parseInt(results.get(1).get(0)) > 0) {
					System.out.println("Manuscript has already been scheduled");
					return false;
				}
			}
		} else {
			System.out.println("Error checking if manuscript has been scheduled");
			return false;
		}

		// Get the manuscript num pages
		int numPages = 0;
		String mQuery = String.format("SELECT MAN_NUMPAGES FROM Manuscript WHERE MAN_ID = %1$s;", manuNum);
		results = Query.execute(mQuery);
		if (results != null && results.size() == 2 && results.get(1).size() == 1)
			numPages = Integer.parseInt(results.get(1).get(0));

		// Get the current page number
		int pageNo = 0;
		int order = 0;
		String pQuery = String.format("SELECT PAGENO, ORDERING FROM Scheduling WHERE Issue_ISS_ID = %1$s;", issue);
		results = Query.execute(pQuery);
		if (results != null && results.size() >= 1 && results.get(0).size() == 2 && results.get(0).get(0).equals("PAGENO")
				&& results.get(0).get(1).equals("ORDERING")) {
			int maxOrder = 0;
			int maxPageNo = 0;
			 if (results.size() > 1) {
				 for (int i = 1; i < results.size(); i++) {
						ArrayList<String> row = results.get(i);
						maxPageNo = Math.max(maxPageNo, Integer.parseInt(row.get(0)));
						maxOrder = Math.max(maxOrder, Integer.parseInt(row.get(1)));
					}
			 }

			pageNo = maxPageNo + 1;
			order = maxOrder + 1;
			if (pageNo + numPages > 100) {
				System.out.println("Manuscript would put issue page count over 100 pages");
				return false;
			}
		} else {
			System.out.println("Error getting other manuscripts scheduled to issue");
			return false;
		}

		// Schedule manuscript for issue
		String sQuery = String.format("INSERT INTO Scheduling (Issue_ISS_ID, Manuscript_MAN_ID, PAGENO, ORDERING)"
				+ " VALUES (%1$s, %2$s, %3$s, %4$s);", issue, manuNum, pageNo, order);

		return Query.insert(sQuery) >= 0;
	}
	
	// Publish
	public void publish(ArrayList<String> input){
		if (Validation.validateSingleInteger(input)){
			publish(Integer.parseInt(input.get(0)));
		} else {
			System.out.println("Please provide a valid integer issue number");
		}
	}
	
	public boolean publish(int issue){
		// Get all manuscripts for the issue
		ArrayList<Integer> manIds = new ArrayList<Integer>();
		String schedQuery = String.format("SELECT Manuscript_MAN_ID FROM Scheduling WHERE Issue_ISS_ID = %1$s;", issue);
		ArrayList<ArrayList<String>> results = Query.execute(schedQuery);
		if (results != null && results.size() > 1 && results.get(0).size() == 1) {
			if (results.get(0).get(0).equals("Manuscript_MAN_ID")) {
				for (int i = 1; i < results.size(); i++) 
					manIds.add(Integer.parseInt(results.get(i).get(0)));
			} else {
				System.out.println("Error in retrieving manuscripts scheduled to issue");
				return false;
			}
		} else {
			System.out.println("Error in retrieving manuscripts scheduled to issue");
			return false;
		}
		
		for (int manuNum : manIds) {
			String statusQuery = String.format("UPDATE Manuscript SET MAN_STATUS = 4, MAN_LASTUPDATED = NOW() WHERE MAN_ID = %1$s;", manuNum);
			if (Query.insert(statusQuery) < 0)
				System.out.println("Error in updating status of manuscript " + manuNum);
		}
		
		return true;
	}

	// Create (issue)
	public void create(ArrayList<String> input){
		if (Validation.validateDoubleIntegers(input)){
			int period = Integer.parseInt(input.get(0));
			int year = Integer.parseInt(input.get(1));
			if (Validation.validateYear(year) && Validation.validatePeriod(period)){
				//create(period, year);
			} else {
				System.out.println("Please center a valid period 1-4 and year 1970-2020");
			}
		}
	}
	public boolean create(int period, int year) {
		// TODO: Implement this
		return false;
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

		System.out.println("Manuscript is not in the under review status");
		return false;
	}
}
