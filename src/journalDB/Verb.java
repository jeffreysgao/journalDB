package journalDB;

import java.util.ArrayList;


public class Verb {
	public Verb() {}
	
	public static String verbRegister (String role, ArrayList<String> input){
	    switch(role) {
	      case "reviwer" :
	        //fname,. lname, mname, affil, email, ricodes as ArrayListInteger
	        ArrayList<Integer> RICodes = new ArrayList<Integer>();
	        switch(input.size()) {
	          case (5) :
	            RICodes.add(Integer.parseInt(input.get(4)));
	            People.regReviewer(input.get(0), input.get(1), null, input.get(2), input.get(3), RICodes);
	          case (6) :
	            RICodes.add(Integer.parseInt(input.get(4)));
	            RICodes.add(Integer.parseInt(input.get(5)));
	            People.regReviewer(input.get(0), input.get(1), null, input.get(2), input.get(3), RICodes);
	          case (7) :
	            RICodes.add(Integer.parseInt(input.get(4)));
	            RICodes.add(Integer.parseInt(input.get(5)));
	            RICodes.add(Integer.parseInt(input.get(6)));
	            People.regReviewer(input.get(0), input.get(1), null, input.get(2), input.get(3), RICodes);

	          default :
	            System.out.println("Please enter the proper number of arugments");
	        }
	        break;

	      case "author" :
	    	 //regAuthor(String fname, String lname, String mname, String email, String address, String affil) {
	        if(Validation.validateLength(input, 4)){
	          People.regAuthor(input.get(0), input.get(1), null, input.get(2), input.get(3), input.get(4));
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

	    return "Hello";
	  }

	public static String verbLogin(ArrayList<String> input){
		
		return "Hello";
	}
	
	public static String verbSubmit (ArrayList<String> input) {
		//submit(String title, string affiliation, int RICODE, string secondaryAuthors, string filename)
		int RICode;
		String secondaryAuthors = null;
		switch(input.size()){
			case (4) : 
				RICode = Integer.parseInt(input.get(2));
				submit(input.get(0), input.get(1), RICode, secondaryAuthors, input.get(3));
				break;
			case(5) : 
				RICode = Integer.parseInt(input.get(2));
				secondaryAuthors = input.get(3);
				submit(input.get(0), input.get(1), RICode, secondaryAuthors, input.get(4));
				break;
			case(6) : 
				RICode = Integer.parseInt(input.get(2));
				secondaryAuthors = input.get(3) + input.get(4);
				submit(input.get(0), input.get(1), RICode, secondaryAuthors, input.get(5));
				break;
			case(7) : 
				RICode = Integer.parseInt(input.get(2));
				secondaryAuthors = input.get(3) + input.get(4) + input.get(5)
				submit(input.get(0), input.get(1), RICode, secondaryAuthors, input.get(6));
				break;
		}

		return "Hello";
	}
}

