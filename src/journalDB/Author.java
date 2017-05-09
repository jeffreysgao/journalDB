package journalDB;
public class Author extends Person {

	
	public Author(int id){
		super(id);
		status();
	}
	
	public void status(){
		//produces a report of all the author's 
		//manuscripts currently in the system where 
		//they are the primary author
		
	}
	
	public void submit(String title, String affil, int RICode, String secondaryAuthors, String filename){
		
	}
	
	public void retract(){
		//are you sure? interface
		
	}
	
}