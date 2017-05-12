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
	  long authId = People.regAuthor("jeff", "gao", "shung", "jeffrey.s.gao1", "13517 Moonflower Meadows Trail", "Dartmouth College");
	  if (authId > 0)
		  System.out.println("success\n");
	  else 
		  System.out.println("fail\n");
	  
	  
	  // test regReviewer
	  System.out.println("TESTING REGREVIEWER:");
	  ArrayList<Integer> ricodes = new ArrayList<Integer>();
	  ricodes.add(1);
	  ricodes.add(2);
	  ricodes.add(3);
	  long revId = People.regReviewer("jeff", "gao", null, "Dartmouth College", "jeffrey.s.gao2", ricodes);
	  if (revId > 0)
		  System.out.println("success\n");
	  else 
		  System.out.println("fail\n");
	  
	  /*
	   * Author.java tests
	   */
	  System.out.println("INITIALIZING AUTHOR: ");
	  Author dickens = new Author((int)authId);
	  System.out.println();

	  // submit
	  System.out.println("TESTING SUBMIT: ");
	  long manId = dickens.submit("A Tale of Two Cities", "UCL", 23, null, null);
	  if (manId > 0)
		  System.out.println("success\n");
	  else
		  System.out.println("fail\n");
	  
	  // status
	  System.out.println("TESTING STATUS:");
	  dickens.status();
	  System.out.println();
	  
	  /*
	   * Editor.java and Reviewer.java tests
	   */
	  System.out.println("INTIALIZING EDITOR: ");
	  Editor jameson = new Editor(50);
	  System.out.println();
	  
	  // get status
	  System.out.println("TESTING EDITOR GETSTATUS: ");
	  jameson.getStatus();
	  System.out.println();
	  
	  System.out.println("INITIALIZING REVIEWER: ");
	  Reviewer jgao = new Reviewer((int)revId);
	  System.out.println();
	  
	  // assign 
	  System.out.println("TESTING ASSIGN: ");
	  if (jameson.assign((int)manId, (int)revId))
		  System.out.println("success\n");
	  else
		  System.out.println("fail\n");
	  
	  // review 
	  System.out.println("TESTING REVIEW: ");
	  if (jgao.review(true, (int)manId, 9, 8, 7, 9))
		  System.out.println("success\n");
	  else
		  System.out.println("fail\n");
	  
	  jgao.review(true, (int)manId, 9, 8, 7, 9);
	  System.out.println();
	  jgao.review(true, 1, 9, 8, 7, 9);
	  System.out.println();
	  
	  // status
	  System.out.println("TESTING STATUS:");
	  dickens.status();
	  jgao.status();
	  System.out.println();
	  
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
	  jgao.status();
	  System.out.println();
	  
	  // typeset
	  System.out.println("TESTING TYPESET: ");
	  if (jameson.typeset((int)manId, 24))
		  System.out.println("success\n");
	  else
		  System.out.println("fail\n");
	  
	  // status
	  System.out.println("TESTING STATUS:");
	  dickens.status();
	  jgao.status();
	  System.out.println();

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
	  jgao.status();
	  System.out.println();

	  // publish
	  System.out.println("TESTING PUBLISH: ");
	  if (jameson.publish(20))
		  System.out.println("success\n");
	  else 
		  System.out.println("fail\n");
	  
	  // retract
	  System.out.println("TESTING RETRACT: ");
	  dickens.retract((int)manId);
	  System.out.println();
	  
	  // status
	  System.out.println("TESTING STATUS:");
	  dickens.status();
	  jgao.status();
	  System.out.println();

	  // get status
	  System.out.println("TESTING GET STATUS: ");
	  Reviewer rev = new Reviewer(15);
	  rev.getStatus();
	  System.out.println();
	  
	  // resignation
	  System.out.println("TESTING RESIGNATION: ");
	  if (jgao.resignation())
		  System.out.println("success");
	  else 
		  System.out.println("failure");
  }
}
