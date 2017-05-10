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
		int[] statuses = {2};
		if (!checkStatus(statuses, manuNum))
			return false;

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
	public boolean reject(int manuNum){
		// Check if manuscript is rejectable
		int[] statuses = {0, 2};
		if (!checkStatus(statuses, manuNum))
			return false;

		// Reject manuscript
		String rQuery = String.format("UPDATE Manuscript SET MAN_STATUS = 1, MAN_LASTUPDATED = NOW() WHERE MAN_ID = %1$s;", manuNum);
		return Query.insert(rQuery) >= 0;
	}

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
				System.out.println(Integer.parseInt(results.get(1).get(0)));
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
				System.out.println(Integer.parseInt(results.get(1).get(0)));
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

	public void publish(ArrayList<String> input){
		if (Validation.validateDoubleIntegers(input)){
			publish(Integer.parseInt(input.get(0)));
		} else {
			System.out.println("Please provide a valid integer issue number");
		}
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
