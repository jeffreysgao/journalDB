package journalDB;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class UI {
	private static Person currentUser;


  private static void routeVerbs(ArrayList<String> input){
	  if (currentUser != null && Validation.validateLength(input,1)){
		  String verb = input.get(0);
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
	  if (Validation.validateLength(input, 1)){
		 switch(verb) {
	      case "register" :
	        String role = input.get(0);
	        BlandVerb.register(role, input);
	        break;

	      case "login" :
	    	int id = Integer.parseInt(input.get(0));
	    	currentUser = BlandVerb.login(input);
	    	break;
	    	default : 
	    		System.out.println("Not a valid command");
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
	  		currentUser.resign();
	  		break;
	  	default:
	  		System.out.println("Please choose a valid command");
	  }
  }
 

  public static void main (String[] args) throws IOException{
	System.out.println(args);
    //What i need to save
    /*
    String singleInput = scan.nextLine();
    ArrayList<String> input = new ArrayList<String>(Arrays.asList(singleInput.split(" ")));
    routeVerbs(input);
    */
    
    //stack overflow answer
    Scanner scanner = new Scanner(System.in);
    String readString = scanner.nextLine();
    ArrayList<String> input = new ArrayList<String>(Arrays.asList(readString.split(" ")));
    while(readString!=null) {
        System.out.println(readString);
       routeVerbs(input);

        if (readString.isEmpty()) {
            System.out.println("");
        }

        if (scanner.hasNextLine()) {
            readString = scanner.nextLine();
            input = new ArrayList<String>(Arrays.asList(readString.split(" ")));
        } else {
            readString = null;
        }
    }
  }
}
