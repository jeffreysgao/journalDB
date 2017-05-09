package journalDB;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import static Verb;

public class UI {
  public UI() throws IOException {}


  private static void verbChecker(String verb, ArrayList<String> input) {
    switch(verb) {
      case "register" :
        String role = input.get(0);
        input.remove(0);
        Verb.verbRegister(role, input);
        break;

      case "login" :
    	 Verb.verbLogin(input);
        break;

      case "status" :
    	  
        break;

      case "submit" :
    	  //submit <title> <Affiliation> <RICode> <author2> <author3> <author4> <filename>
    	  Verb.verbSubmit(input);
        break;

      case "retract" :
        break;

      default:
        System.out.println("Please choose a correct command");
    }


  }

  public static void main (String[] args) throws IOException{
    Scanner scan = new Scanner(System.in);
    String item = scan.next();
    System.out.println(item);

  }
}
