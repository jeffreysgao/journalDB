package journalDB;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.mysql.fabric.xmlrpc.base.Array;

public class UI {
	private static Person currentUser;
  public UI() throws IOException {}

  private static void login(int id, String person){
	  switch(person) {
	  case "editor" : 
		  currentUser = new Editor(id);
		  break;
		  
	  case "author" : 
		  currentUser = new Author(id);
		  break;
	  case "reviewer" : 
		  currentUser = new Reviewer(id);
		  break;
	  default: 
		System.out.println("Not a legitimate login id");
		break;
	  }
  }

  private static void routeVerbs(ArrayList<String> input){
	  if (currentUser != null && Validation.validateLength(input,1)){
		  String verb = input.get(0);
		  userVerbs(verb, input);
	  } else if (Validation.validateLength(input, 1)){
		  String verb = input.get(0);
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
	        BlandVerb.verbRegister(role, input);
	        break;

	      case "login" :
	    	int id = Integer.parseInt(input.get(0));
	    	login(id, BlandVerb.verbLogin(id));
	    	break;
		  }
	  } else {System.out.println("Not enough arugments were provided");}
 }
  
  private static void userVerbs(String verb, ArrayList<String> input){
	  switch(verb) {
	  	case "status" :
	  		currentUser.status(input);
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
	  		currentUser.resign(input);
	  		break;

	  	default:
	  		System.out.println("Please choose a valid command");
	  }
  }
 

  public static void main (String[] args) throws IOException{
    Scanner scan = new Scanner(System.in);
    String singleInput = scan.nextLine();

    System.out.println(singleInput);
    int i = Integer.parseInt("1");
    System.out.println(i);
    int a =0, b =0, c= 0, d = 0;
    int sum = 0;
    try {
    	ArrayList<Integer> jon = new ArrayList<Integer>();
    	jon.add(1);
    	jon.add(2);
    	jon.add(3);
    	sum = sum + jon.get(0);
    	sum = sum + jon.get(1);
    	sum = sum + jon.get(2);
    	sum = sum +jon.get(3);

    } catch( Exception e){

    	System.out.println("haha");
    	System.out.println(sum);

    }
    ArrayList<String> input = new ArrayList<String>(Arrays.asList(singleInput.split(" ")));

    //routeVerbs(input);
    


  }
}
