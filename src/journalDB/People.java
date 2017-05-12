package journalDB;

import java.util.ArrayList;

public class People {
	public static final int AUTHOR = 0;
	public static final int EDITOR = 1;
	public static final int REVIEWER = 2;

	public static long regReviewer(String fname, String lname, String mname, String affil, String email, ArrayList<Integer> ricodes) {
		if (fname == null || fname.isEmpty() || lname == null || lname.isEmpty() || email == null || email.isEmpty())
			return -1;
		
		if (ricodes.size() == 0 || ricodes.size() > 3)
			return -1;
		
		long id = regPerson(fname, lname, mname, REVIEWER);
		if (id > 0) {
			String rQuery = "INSERT INTO Reviewer (Person_PERSON_ID, REV_EMAIL, REV_ISACTIVE";
			if (affil != null && !affil.isEmpty())
				rQuery += ", REV_AFFIL";
			rQuery += String.format(") VALUES (\"%1$s\", \"%2$s\", true", id, email);
			if (affil != null && !affil.isEmpty())
				rQuery += String.format(", \"%1$s\"", affil);
			rQuery += ");";
			if (Query.insert(rQuery) >= 0) {
				for (int ricode : ricodes) {
					String rcQuery = String.format("INSERT INTO Interests (Reviewer_Person_PERSON_ID, Area_AREA_ID) VALUES (%1$s, %2$s);", id, ricode);
					if (Query.insert(rcQuery) < 0)
						System.out.println("Inserting " + ricode + " for " + id + " into ricodes failed");
				}
				
				System.out.println("Reviewer id #" + id + " registered");
				return id;
			}
		}
		return -1;
	}

	public static long regAuthor(String fname, String lname, String mname, String email, String address, String affil) {
		long id = regPerson(fname, lname, mname, AUTHOR);
		if (id > 0) {
			String aQuery = "INSERT INTO Author (Person_PERSON_ID, AUTH_ADDR, AUTH_EMAIL";
			if (affil != null && !affil.isEmpty())
				aQuery += ", AUTH_AFFIL";
			aQuery += String.format(") VALUES (\"%1$s\", \"%2$s\", \"%3$s\"", id, address, email);
			if (affil != null && !affil.isEmpty())
				aQuery += String.format(", \"%1$s\"", affil);
			aQuery += ");";
			if (Query.insert(aQuery) >= 0) {
				System.out.println("Author id #" + id + " registered");
				return id;
			}
		}
		return -1;
	}

	public static long regEditor(String fname, String lname, String mname) {
		int id = (int)regPerson(fname, lname, mname, EDITOR);
		if (id > 0) 
			System.out.println("Editor id #" + id + " registered");
		return id;
	}

	private static long regPerson(String fname, String lname, String mname, int role) {
		String pQuery = "INSERT INTO Person (PERSON_LNAME, PERSON_FNAME";
		if (mname != null && !mname.isEmpty())
			pQuery += ", PERSON_MNAME";
		pQuery += String.format(", PERSON_ROLE) VALUES (\"%1$s\", \"%2$s\"", lname, fname);
		if (mname != null && !mname.isEmpty())
			pQuery += String.format(", \"%1$s\"", mname);
		switch(role) {
			case AUTHOR:
				pQuery += ", \"author\");";
				break;
			case EDITOR:
				pQuery += ", \"editor\");";
				break;
			case REVIEWER:
				pQuery += ", \"reviewer\");";
				break;
			default:
				return -1;
		}

		return Query.insert(pQuery);
	}
}
