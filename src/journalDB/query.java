package journalDB;

import java.sql.*;

public class query {
	
	public static final String SERVER   = "jdbc:mysql://sunapee.cs.dartmouth.edu/";
    public static final String USERNAME = "gbsnlspl";
    public static final String PASSWORD = "dbTimespring2017!";
    public static final String DATABASE = "cs61";
//    public static final String QUERY    = "SELECT * FROM manuscript;";
	
    public static boolean execute(String q) {
    	Connection con = null;
    	Statement stmt = null;
    	ResultSet res  = null;
    	int numColumns = 0;

    	// attempt to connect to db
    	try {
    	    // load mysql driver
    	    Class.forName("com.mysql.jdbc.Driver").newInstance();
    	    
    	    // initialize connection
    	    con = DriverManager.getConnection(SERVER+DATABASE, USERNAME, PASSWORD);
    	    
    	    System.out.println("Connection established.");

    	    // initialize a query statement
    	    stmt = con.createStatement();

    	    // query db and save results
    	    res = stmt.executeQuery(q);

    	    System.out.format("Query executed: '%s'\n\nResults:\n", q);
    	    
    	    // the result set contains metadata
    	    numColumns = res.getMetaData().getColumnCount();

    	    // print table header
    	    for(int i = 1; i <= numColumns; i++) {
    		System.out.format("%-12s", res.getMetaData().getColumnName(i));
    	    }
    	    System.out.println("\n--------------------------------------------");

    	    // iterate through results
    	    while(res.next()) {
    		for(int i = 1; i <= numColumns; i++) {
    		    System.out.format("%-12s", res.getObject(i));
    		}
    		System.out.println("");
    	    }
    	} catch (SQLException e ) {          // catch SQL errors
    	    System.err.format("SQL Error: %s", e.getMessage());
    	    return false;
    	} catch (Exception e) {              // anything else
    	    e.printStackTrace();
    	    return false;
    	} finally {
    	    // cleanup
    	    try {
    		con.close();
    		stmt.close();
    		res.close();
    		System.out.print("\nConnection terminated.");
    		return true;
    	    } catch (Exception e) { /* ignore cleanup errors */ }
    	}
    	return false;
    }
    
    public static int getInt(String q) {
    	Connection con = null;
    	Statement stmt = null;
    	ResultSet res  = null;
    	int numColumns = 0;
    	int count;

    	// attempt to connect to db
    	try {
    	    // load mysql driver
    	    Class.forName("com.mysql.jdbc.Driver").newInstance();
    	    
    	    // initialize connection
    	    con = DriverManager.getConnection(SERVER+DATABASE, USERNAME, PASSWORD);
    	    
    	    System.out.println("Connection established.");

    	    // initialize a query statement
    	    stmt = con.createStatement();

    	    // query db and save results
    	    res = stmt.executeQuery(q);

    	    System.out.format("Query executed: '%s'\n\nResults:\n", q);
    	    
    	    // the result set contains metadata
    	    numColumns = res.getMetaData().getColumnCount();

    	    if (numColumns != 1 || !res.next())
    	    	return -1;
    	   
    	    count = (Integer)res.getObject(1);
    	} catch (SQLException e ) {          // catch SQL errors
    	    System.err.format("SQL Error: %s", e.getMessage());
    	    return -1;
    	} catch (Exception e) {              // anything else
    	    e.printStackTrace();
    	    return -1;
    	} finally {
    	    // cleanup
    	    try {
    		con.close();
    		stmt.close();
    		res.close();
    		System.out.print("\nConnection terminated.");
    		return count;
    	    } catch (Exception e) { /* ignore cleanup errors */ }
    	}
    	return -1;
    }
}
