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
  public static final String USERNAME = "gbsnlspl";
  public static final String PASSWORD = "dbTimespring2017!";
  public static final String DATABASE = "gbsnlspl_db";
  public static final String QUERY    = "SELECT * FROM manuscript;";

  public static void main(String[] args) {
	  /* 
	   * People.java tests
	   */
	  
	  // test regAuthor
	  System.out.println("TESTING REGAUTHOR:");
	  People.regAuthor("jeff", "gao", "shung", "jeffrey.s.gao1", "13517 Moonflower Meadows Trail", "Dartmouth College");
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
	   * Editor.java tests
	   */
	  Editor jameson = new Editor(50);
	  
	  // assign 
	  System.out.println("TESTING ASSIGN: ");
	  if (jameson.assign(3, (int)revId))
		  System.out.println("success\n");
	  else
		  System.out.println("fail\n");
	  
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
	  if (jameson.accept(3))
		  System.out.println("success\n");
	  else
		  System.out.println("fail\n");
	  
	  if (jameson.accept(1))
		  System.out.println("success\n");
	  else
		  System.out.println("fail\n");
	  
	  // typeset
	  System.out.println("TESTING TYPESET: ");
	  if (jameson.typeset(3, 24))
		  System.out.println("success\n");
	  else
		  System.out.println("fail\n");
	 
	  // schedule
	  System.out.println("TESTING SCHEDULE: ");
	  if (jameson.schedule(3, 20))
		  System.out.println("success\n");
	  else
		  System.out.println("fail\n");
	  
	  if (jameson.schedule(3, 20))
		  System.out.println("success\n");
	  else
		  System.out.println("fail\n");
	  
	  if (jameson.schedule(3, 21))
		  System.out.println("success\n");
	  else	
		  System.out.println("fail\n");
	  
	  if (jameson.schedule(1, 20))
		  System.out.println("success\n");
	  else
		  System.out.println("fail\n");
	  
	  // publish
	  System.out.println("TESTING PUBLISH: ");
	  if (jameson.publish(20))
		  System.out.println("success\n");
	  else 
		  System.out.println("fail\n");
	  
	  /*
	   * Author.java tests
	   */
	  Author dickens = new Author(1);

	  // submit
	  System.out.println("TESTING SUBMIT");
	  if (dickens.submit("A Tale of Two Cities", "UCL", 23, null, null) > 0)
		  System.out.println("success\n");
	  else
		  System.out.println("fail\n");
	  
	  // status
	  System.out.println("TESTING STATUS:");
	  dickens.status();
  }
}
