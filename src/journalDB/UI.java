package journalDB;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UI {
	private static Person currentUser;


  private static void routeVerbs(ArrayList<String> input){
	  if (currentUser != null && Validation.lengthAtLeast(input,1)){
		  String verb = input.get(0);
		  input.remove(0);
		  userVerbs(verb, input);
	  } else if (Validation.lengthAtLeast(input, 1)){
		  String verb = input.get(0);
		  input.remove(0);
		  blandVerbs(verb, input);
	  } else {
		  System.out.println("Please enter a command");
	  }
  }
  private static void blandVerbs(String verb, ArrayList<String> input) {
	  if (Validation.lengthAtLeast(input, 1)){
		 switch(verb) {
	      case "register" :
	        String role = input.get(0);
	        input.remove(0);
	        BlandVerb.register(role, input);
	        break;
	      case "login" :
	    	currentUser = BlandVerb.login(input);
	    	break;
	    	default : 
	    		System.out.println("Please login or register yourself");
	    		break;
		  }
	  } else {System.out.println("Not enough arugments were provided");}
 }
  
  private static void userVerbs(String verb, ArrayList<String> input){
	  switch(verb) {
	  	case "status" :
	  		currentUser.status();
	  		break;
	  	case "submit" :
	  		currentUser.submit(input);
	  		break;
	  	case "retract" :
	  		currentUser.retract(input);
	  		break;
	  	case "assign" : 
	  		currentUser.assign(input);
	  		break;
	  	case "reject" : 
	  		currentUser.reject(input);
	  		break;
	  	case "accept" : 
	  		currentUser.accept(input);
	  		break;
	  	case "typeset" : 
	  		currentUser.typeset(input);
	  		break;
	  	case "schedule" :
	  		currentUser.schedule(input);
	  		break;
	  	case "publish" : 
	  		currentUser.publish(input);
	  		break;
	  	case "resign" : 
	  		currentUser.resignation();
	  		break;
	  	case "review" : 
	  		currentUser.review(input);
	  		break;
	  	case "create" : 
	  		currentUser.create(input);
	  		break;

	  	default:
	  		System.out.println("Please choose a valid command");
	  }
  }
 

  private static ArrayList<String> deliminate(CharSequence readString){
	    ArrayList<String> matchList = new ArrayList<String>();
	    Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
	    Matcher regexMatcher = regex.matcher(readString);
	    while (regexMatcher.find()) {
	        if (regexMatcher.group(1) != null) {
	            // Add double-quoted string without the quotes
	            matchList.add(regexMatcher.group(1));
	        } else {
	            // Add unquoted word
	            matchList.add(regexMatcher.group());
	        }
	    } 
	    return matchList;
  }
  public static void main (String[] args) throws IOException{
    
    //stack overflow answer
	System.out.println("Enter a command");
    Scanner scanner = new Scanner(System.in);
    String readString = scanner.nextLine();
    ArrayList<String> input = deliminate(readString);

    while(readString!=null) {
       routeVerbs(input);

        if (readString.isEmpty()) {
            System.out.println("");
        }

        if (scanner.hasNextLine()) {
            readString = scanner.nextLine();
            input = deliminate(readString);
        } else {
            readString = null;
        }
    }
    scanner.close();
  }
}
