package journalDB;

import java.math.BigInteger;
import java.sql.*;

public class Query {
	
	public static final String SERVER   = "jdbc:mysql://sunapee.cs.dartmouth.edu/";
    public static final String USERNAME = "gbsnlspl";
    public static final String PASSWORD = "dbTimespring2017!";
    public static final String DATABASE = "gbsnlspl_db";
	
    /*
     * Used for queries that get tables, true indicates success
     */
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
    		System.out.println("\nConnection terminated.");
    		return true;
    	    } catch (Exception e) { /* ignore cleanup errors */ }
    	}
    	return false;
    }
    
    /* 
     * Used for queries that update tables, true indicates successfully changing 1+ rows
     */
    public static int insert(String q) {
    	Connection con = null;
    	Statement stmt = null;
    	int res = 0;
    	int numColumns = 0;
    	int id = -1;

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
    	    res = stmt.executeUpdate(q, Statement.RETURN_GENERATED_KEYS);
    	    
    	    ResultSet rs = stmt.getGeneratedKeys();
    	    if (rs.next())
    	    	id = rs.getInt(1);
    	    rs.close();

    	    System.out.format("Query executed: '%s'\n\nResults:\n", q);
    	    
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
    		System.out.println("\nConnection terminated.");
    		
    		if (res > 0)
    			return id;
    	    } catch (Exception e) { /* ignore cleanup errors */ }
    	}
    	return -1;
    }
    
    
    /*
     * Used for queries that return a single column, single row table with a long value (count)
     */
    public static long getLong(String q) {
    	Connection con = null;
    	Statement stmt = null;
    	ResultSet res  = null;
    	int numColumns = 0;
    	long count = -1;

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
    	   
    	    count = ((BigInteger)res.getObject(1)).longValue();
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
    		System.out.println("\nConnection terminated.");
    		return count;
    	    } catch (Exception e) { /* ignore cleanup errors */ }
    	}
    	return -1;
    }
}
