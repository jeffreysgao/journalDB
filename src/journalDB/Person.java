package journalDB;

import java.util.ArrayList;

public class Person { 
	protected int id;
	protected String className;
	public Person(int id, String className){
		this.id = id;
		this.className = className;
	}
	
	private void warning(String verb){
		System.out.println("You do not have the access rights to " + verb);
	}
	
	//all roles
	public void status(){ warning("status"); }
	
	//author verbs
	public void submit(ArrayList<String> input) { warning("submit");}
	public void retract(ArrayList<String> input){ warning("retract"); }

	//editor verbs
	public void assign(ArrayList<String> input){ warning("assign"); }
	public void reject(ArrayList<String> input){ warning("reject"); }
	public void accept(ArrayList<String> input){ warning("accept"); }
	public void typeset(ArrayList<String> input){ warning("typeset"); }
	public void schedule(ArrayList<String> input){ warning("schedule"); }
	public void publish(ArrayList<String> input){ warning("publish"); }
	
	//reviewer verbs
	public void resign(){ warning("resign");}
	public void review(Boolean isAccepted, ArrayList<String> input){ warning("review"); }

	
	public String getClassName(){
		return this.className;
	}
}