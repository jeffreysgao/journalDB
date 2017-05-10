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

	//TODO: Need to make sure that secondary authors are in the correct format
	public void submit(ArrayList<String> input){
		int RICode = -1;
		String secondaryAuthors = null;
		try {
			RICode = Integer.parseInt(input.get(2));
			switch(input.size()) {
			case (4) :
				submit(input.get(0), input.get(1), RICode, secondaryAuthors, input.get(3));
				System.out.println("RICode is not an integer");
				break;
			case(5) :
				secondaryAuthors = input.get(3);
				submit(input.get(0), input.get(1), RICode, secondaryAuthors, input.get(4));
				break;
			case(6) :
				secondaryAuthors = input.get(3) + input.get(4);
				submit(input.get(0), input.get(1), RICode, secondaryAuthors, input.get(5));
				break;
			case(7) :
				secondaryAuthors = input.get(3) + input.get(4) + input.get(5);
				submit(input.get(0), input.get(1), RICode, secondaryAuthors, input.get(6));
				break;
			default :
				System.out.println("Please enter the proper number of arguments");
			}
		} catch(Exception e ){
			System.out.println("Please provide proper arguments");
		}

	}
	
	public void submit(String title, String affil, int RICode, String secondaryAuthors, String filename){
	}


	public void retract(ArrayList<String> input){
		try {
			retract(Integer.parseInt(input.get(1)));
		} catch (Exception e ){
			System.out.println("Please provide the proper number of arguments to retract manuscript");
		}
	}
	
	public void retract(int manID){
	}
}
