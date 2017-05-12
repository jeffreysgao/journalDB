package journalDB;

import java.util.ArrayList;


public class BlandVerb {
	
	public static void register (String role, ArrayList<String> input){
	    switch(role) {
	      case "reviewer" :
	        //fname,. lname, mname, affil, email, ricodes as ArrayListInteger
	        ArrayList<Integer> RICodes = new ArrayList<Integer>();
	        switch(input.size()) {
	          case (5) :
	            RICodes.add(Integer.parseInt(input.get(4)));
	            People.regReviewer(input.get(0), input.get(1), null, input.get(2), input.get(3), RICodes);
	            break;
	          case (6) :
	            RICodes.add(Integer.parseInt(input.get(4)));
	            RICodes.add(Integer.parseInt(input.get(5)));
	            People.regReviewer(input.get(0), input.get(1), null, input.get(2), input.get(3), RICodes);
	            break;
	          case (7) :
	            RICodes.add(Integer.parseInt(input.get(4)));
	            RICodes.add(Integer.parseInt(input.get(5)));
	            RICodes.add(Integer.parseInt(input.get(6)));
	            People.regReviewer(input.get(0), input.get(1), null, input.get(2), input.get(3), RICodes);
	            break;

	          default :
	            System.out.println("Please enter the proper number of arugments");
	        }
	        break;

	      case "author" :
	        if(Validation.validateLength(input, 4)){
	          People.regAuthor(input.get(0), input.get(1), null, input.get(2), input.get(3), null);
	        } else {
	          System.out.println("Please enter the proper number of arguments");
	        }
	        break;

	      case "editor" :
	        //fname, lname, mname
	        if(Validation.validateLength(input, 2)){
	          People.regEditor(input.get(0), input.get(1), null);
	        } else {
	          System.out.println("Please enter the proper number of arguments");
	        }
	        break;

	      default :
	        System.out.println("Please choose a correct register type");

	    }
	  }

	public static Person login(ArrayList<String> input){
		if (Validation.validateSingleInteger(input)){
			int id = Integer.parseInt(input.get(0));
			return login(id);
		} else { 
			System.out.println("Login Id must be an integer");
			return null;
		}
	}
	
	public static Person login(int id){
		String query = "SELECT PERSON_ID, PERSON_LNAME, PERSON_FNAME, PERSON_MNAME, PERSON_ROLE FROM Person WHERE PERSON_ID = " + id + ";";
		ArrayList<ArrayList<String>> results = Query.execute(query);

		if (results.size() > 1){
			Person currentUser;
			String personType = results.get(1).get(4);
			switch(personType){
			  case "editor" : 
				  currentUser = new Editor(id);
				  System.out.println("Login successful. Status: Editor. Name: " + results.get(1).get(2) + " " + results.get(1).get(1));
				  break;
			  case "author" : 
				  currentUser = new Author(id);
				  String output = "Login successful. Status: Author. Name: " + results.get(1).get(2) + " " + results.get(1).get(1);
				  String aQuery = "SELECT AUTH_ADDR FROM Author WHERE Person_PERSON_ID = " + id + ";";
				  ArrayList<ArrayList<String>> addr = Query.execute(aQuery);
				  if (addr.size() != 2)
					  System.out.println("Error getting author address");
				  else 
					  output += ". Address: " + addr.get(1).get(0);
				  System.out.println(output);
				  break;
			  case "reviewer" : 
				  currentUser = null;
 				  String isActiveQuery = "SELECT REV_ISACTIVE FROM Reviewer WHERE Person_PERSON_ID = " + id + ";";
				  ArrayList<ArrayList<String>> isActiveResults = Query.execute(isActiveQuery);
				  if (isActiveResults.size() > 1 ){
					  Boolean isActive = isActiveResults.get(1).get(0).compareTo("1") == 0;
					  if (isActive){
						  currentUser = new Reviewer(id);
						  System.out.println("Login successful. Status: Reviewer. Name: " + results.get(1).get(2) + " " + results.get(1).get(1));
					  } else {
						  System.out.println("Reviewer is no longer active");
					  }
				  } else {
					  System.out.println("SQL Error, cannot find user in reviewer table. Foreign key error");				   
				  }
				  break;
			  default: 
				System.out.println("Not a legitimate user type");
				currentUser = null;
				break;
			 }
			return currentUser;
		} else { 
			System.out.println("ID does not exist in system");
			return null;
		}
	}
}

