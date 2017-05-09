package journalDB;

import java.util.ArrayList;

public class people {
	public static final int AUTHOR = 0;
	public static final int EDITOR = 1; 
	public static final int REVIEWER = 2;
	
	public static int regReviewer(String fname, String lname, String mname, String affil, String email, ArrayList<Integer> ricodes) {
		int id = regPerson(fname, lname, mname, REVIEWER);
		if (id > 0) {
			String rQuery = "INSERT INTO Reviewer (Person_PERSON_ID, REV_EMAIL, REV_ISACTIVE";
			if (affil != null && !affil.isEmpty()) 
				rQuery += ", REV_AFFIL";
			rQuery += String.format(") VALUES (%1, %2, true", id, email);
			if (affil != null && !affil.isEmpty()) 
				rQuery += String.format(", %1", affil);
			rQuery += ");";
			if (query.execute(rQuery)) {
				for (int ricode : ricodes) {
					// query to insert ricodes
				}
				
				return id;
			}
		}
		
		return -1;
	}
	
	public static int regAuthor(String fname, String lname, String mname, String email, String address, String affil) {
		int id = regPerson(fname, lname, mname, AUTHOR);
		if (id > 0) {
			String aQuery = "INSERT INTO Author (Person_PERSON_ID, AUTH_ADDR, AUTH_EMAIL";
			if (affil != null && !affil.isEmpty())
				aQuery += ", AUTH_AFFIL";
			aQuery += String.format(") VALUES (%1, %2, %3", id, address, email);
			if (affil != null && !affil.isEmpty()) 
				aQuery += String.format(", %1", affil);
			aQuery += ");";
			if (query.execute(aQuery))
				return id;
		} 
		return -1;
	}
	
	public static int regEditor(String fname, String lname, String mname) {
		return regPerson(fname, lname, mname, EDITOR);
	}
	
	private static int regPerson(String fname, String lname, String mname, int role) {
		String cQuery = "SELECT COUNT(*) FROM Person;";
		int count = query.getInt(cQuery);
		
		if (count == -1)
			return -1;
		
		String pQuery = "INSERT INTO Person (PERSON_LNAME, PERSON_FNAME";
		if (mname != null && !mname.isEmpty())
			pQuery += ", PERSON_MNAME";
		pQuery += String.format(", PERSON_ROLE) VALUES (%1, %2", lname, fname);
		if (mname != null && !mname.isEmpty())
			pQuery += String.format(", %1", mname);
		switch(role) {
			case AUTHOR: 
				pQuery += ", author);";
				break;
			case EDITOR: 
				pQuery += ", editor";
				break;
			case REVIEWER: 
				pQuery += ", reviewer";
				break;
			default: 
				return -1;
		} 
		
		query.execute(pQuery);
		return count++;
	}
}
