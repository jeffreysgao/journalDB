package journalDB;

import java.util.ArrayList;

public class Author extends Person {

	
	public Author(int id){
		super(id, "reviewer");
		status();
	}
	
	public void status(){
		//produces a report of all the author's 
		//manuscripts currently in the system where 
		//they are the primary author
		System.out.println("Author status");
		
	}
	
	public void submit(ArrayList<String> input){
		int RICode;
		String secondaryAuthors = null;
		switch(input.size()) {
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
				secondaryAuthors = input.get(3) + input.get(4) + input.get(5);
				submit(input.get(0), input.get(1), RICode, secondaryAuthors, input.get(6));
				break;
			default : 
				System.out.println("Please enter the proper number of arguments");
			}
	}
	
	public void submit(String title, String affil, int RICode, String secondaryAuthors, String filename){
		
	}
	
	
	public void retract(input){
		
		//are you sure? interface
		
	}
	
}