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
	long id = People.regAuthor("jeff", "gao", "shung", "jeffrey.s.gao1", "13517 Moonflower Meadows Trail", "Dartmouth College");
	System.out.println(Long.toString(id));
	   
	  ArrayList<ArrayList<String>> results = Query.execute("SELECT * FROM Person;");
	  for (ArrayList<String> row : results) {
		  for (String value : row) {
			  System.out.print(value + "\t");
		  }
		  System.out.println();
	  }
	  
	  ArrayList<Integer> ricodes = new ArrayList<Integer>();
	  ricodes.add(1);
	  ricodes.add(2);
	  ricodes.add(3);
	  long revId = People.regReviewer("jeff", "gao", null, "Dartmouth College", "jeffrey.s.gao2", ricodes);
	  System.out.println(Long.toString(revId));
	  
	  Editor jameson = new Editor(50);
	  if (jameson.assign(3, (int)revId))
		  System.out.println("success");
	  else
		  System.out.println("fail");
	  
	  if (jameson.reject(1))
		  System.out.println("success");
	  else
		  System.out.println("fail");
	  
	  if (jameson.accept(3))
		  System.out.println("success");
	  else
		  System.out.println("fail");
	  
  }
}
