package journalDB;
/*
dbexample.java
Ira Ray Jenkins

This simple example demonstrates connecting to a MySQL database and
executing a query.
*/
import java.sql.*;
import java.util.ArrayList;

public class dbexample {
  public static final String SERVER   = "jdbc:mysql://sunapee.cs.dartmouth.edu/";
  public static final String USERNAME = "salee";
  public static final String PASSWORD = "sleeslee";
  public static final String DATABASE = "salee_db";
  public static final String QUERY    = "SELECT * FROM manuscript;";

  public static void main(String[] args) {
	  /* 
	   * People.java tests
	   */
	  
	  // test regAuthor
	  System.out.println("TESTING REGAUTHOR:");
	  long authId = People.regAuthor("jeff", "gao", "shung", "jeffrey.s.gao1", "13517 Moonflower Meadows Trail", "Dartmouth College");
	  System.out.println();
	   
//	  // test method for parsing tables
//	  ArrayList<ArrayList<String>> results = Query.execute("SELECT * FROM Person;");
//	  for (ArrayList<String> row : results) {
//		  for (String value : row) {
//			  System.out.print(value + "\t");
//		  }
//		  System.out.println();
//	  }
	  
	  // test regReviewer
	  System.out.println("TESTING REGREVIEWER:");
	  ArrayList<Integer> ricodes = new ArrayList<Integer>();
	  ricodes.add(1);
	  ricodes.add(2);
	  ricodes.add(3);
	  long revId = People.regReviewer("jeff", "gao", null, "Dartmouth College", "jeffrey.s.gao2", ricodes);
	  System.out.println();
	  
	  /*
	   * Author.java tests
	   */
	  Author dickens = new Author((int)authId);

	  // submit
	  System.out.println("TESTING SUBMIT");
	  long manId = dickens.submit("A Tale of Two Cities", "UCL", 23, null, null);
	  if (manId > 0)
		  System.out.println("success\n");
	  else
		  System.out.println("fail\n");
	  
	  // status
	  System.out.println("TESTING STATUS:");
	  dickens.status();
	  
	  /*
	   * Editor.java tests
	   */
	  Editor jameson = new Editor(50);
	  
	  // assign 
	  System.out.println("TESTING ASSIGN: ");
	  if (jameson.assign((int)manId, (int)revId))
		  System.out.println("success\n");
	  else
		  System.out.println("fail\n");
	  
	  // status
	  System.out.println("TESTING STATUS:");
	  dickens.status();
	  
	  // reject
	  System.out.println("TESTING REJECT: ");
	  if (jameson.reject(1))
		  System.out.println("success\n");
	  else
		  System.out.println("fail\n");
	  
	  if (jameson.reject(5))
		  System.out.println("success\n");
	  else
		  System.out.println("fail\n");
	  
	  // accept
	  System.out.println("TESTING ACCEPT: ");
	  if (jameson.accept((int)manId))
		  System.out.println("success\n");
	  else
		  System.out.println("fail\n");
	  
	  if (jameson.accept(1))
		  System.out.println("success\n");
	  else
		  System.out.println("fail\n");
	  
	  // status
	  System.out.println("TESTING STATUS:");
	  dickens.status();
	  
	  // typeset
	  System.out.println("TESTING TYPESET: ");
	  if (jameson.typeset((int)manId, 24))
		  System.out.println("success\n");
	  else
		  System.out.println("fail\n");
	  
	  // status
	  System.out.println("TESTING STATUS:");
	  dickens.status();
	 
	  // schedule
	  System.out.println("TESTING SCHEDULE: ");
	  if (jameson.schedule((int)manId, 20))
		  System.out.println("success\n");
	  else
		  System.out.println("fail\n");
	  
	  if (jameson.schedule((int)manId, 20))
		  System.out.println("success\n");
	  else
		  System.out.println("fail\n");
	  
	  if (jameson.schedule((int)manId, 21))
		  System.out.println("success\n");
	  else	
		  System.out.println("fail\n");
	  
	  if (jameson.schedule(1, 20))
		  System.out.println("success\n");
	  else
		  System.out.println("fail\n");
	  
	  // status
	  System.out.println("TESTING STATUS:");
	  dickens.status();
	  
	  // retract
	  System.out.println("TESTING RETRACT: ");
	  dickens.retract((int)manId);
	  
//	  // publish
//	  System.out.println("TESTING PUBLISH: ");
//	  if (jameson.publish(20))
//		  System.out.println("success\n");
//	  else 
//		  System.out.println("fail\n");
//	  
  }
}
