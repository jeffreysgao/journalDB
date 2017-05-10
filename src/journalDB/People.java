package journalDB;

import java.util.ArrayList;

public class People {
	public static final int AUTHOR = 0;
	public static final int EDITOR = 1;
	public static final int REVIEWER = 2;

	public static long regReviewer(String fname, String lname, String mname, String affil, String email, ArrayList<Integer> ricodes) {
		long id = regPerson(fname, lname, mname, REVIEWER);
		if (id > 0) {
			String rQuery = "INSERT INTO Reviewer (Person_PERSON_ID, REV_EMAIL, REV_ISACTIVE";
			if (affil != null && !affil.isEmpty())
				rQuery += ", REV_AFFIL";
			rQuery += String.format(") VALUES (%1, %2, true", id, email);
			if (affil != null && !affil.isEmpty())
				rQuery += String.format(", %1", affil);
			rQuery += ");";
			if (Query.insert(rQuery) > 0) {
				for (int ricode : ricodes) {
					// query to insert ricodes
				}

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
			if (Query.insert(aQuery) > 0)
				return id;
		}
		return -1;
	}

	public static long regEditor(String fname, String lname, String mname) {
		return regPerson(fname, lname, mname, EDITOR);
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
				pQuery += ", \"editor\"";
				break;
			case REVIEWER:
				pQuery += ", \"reviewer\"";
				break;
			default:
				return -1;
		}

		return Query.insert(pQuery);
	}
}
