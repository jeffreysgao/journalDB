package journalDB;

public class Reviewer extends Person {

	
	public Reviewer(int id){
		super(id, "reviewer");
		status();
	}
	
	public void status() {
		System.out.println("Reviewer status");
	}
	public void resign(){
		
	}
	

	public void review(){
		//accept or reject
		//set values for clar, appr, method, contr, etc
		//must check to see that that the manuscript is in the reviewing phase
	}
}