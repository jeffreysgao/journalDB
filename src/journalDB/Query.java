package journalDB;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;

public class Query {

	public static final String SERVER   = "jdbc:mysql://sunapee.cs.dartmouth.edu/";
    public static final String USERNAME = "salee";
    public static final String PASSWORD = "sleeslee";
    public static final String DATABASE = "salee_db";

    /*
     * Used for queries that get tables, true indicates success
     */
    public static ArrayList<ArrayList<String>> execute(String q) {
    	Connection con = null;
    	Statement stmt = null;
    	ResultSet res  = null;
	    ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
    	int numColumns = 0;

    	// attempt to connect to db
    	try {
    	    // load mysql driver
    	    Class.forName("com.mysql.jdbc.Driver").newInstance();

    	    // initialize connection
    	    con = DriverManager.getConnection(SERVER+DATABASE, USERNAME, PASSWORD);

//    	    System.out.println("Connection established.");

    	    // initialize a query statement
    	    stmt = con.createStatement();

    	    // query db and save results
    	    res = stmt.executeQuery(q);

    	    System.out.format("Query executed: '%s'\n", q);
    	    
    	    numColumns = res.getMetaData().getColumnCount();
    	    
    	    ArrayList<String> header = new ArrayList<String>();
    	    for(int i = 1; i <= numColumns; i++) 
    	    	header.add(res.getMetaData().getColumnName(i));
    	    results.add(header);
    	    
    	    while(res.next()) {
    	    	ArrayList<String> row = new ArrayList<String>();
    	    	for (int i = 1; i <= numColumns; i++) 
    	    		row.add(res.getString(i));
    	    	results.add(row);
    	    }
    	} catch (SQLException e ) {          // catch SQL errors
    	    System.err.format("SQL Error: %s", e.getMessage());
    	    return null;
    	} catch (Exception e) {              // anything else
    	    e.printStackTrace();
    	    return null;
    	} finally {
    	    // cleanup
    	    try {
    		con.close();
    		stmt.close();
    		res.close();
//    		System.out.println("Connection terminated.\n");
    		return results;
    	    } catch (Exception e) { /* ignore cleanup errors */ }
    	}
    	return null;
    }

    /*
     * Used for queries that update tables, true indicates successfully changing 1+ rows
     * Returns 1+ if new key is created 
     * Returns 0 if successful but no new key created
     * Returns -1 if unsuccessful
     */
    public static int insert(String q) {
    	Connection con = null;
    	Statement stmt = null;
    	int res = 0;
    	int numColumns = 0;
    	int id = 0;

    	// attempt to connect to db
    	try {
    	    // load mysql driver
    	    Class.forName("com.mysql.jdbc.Driver").newInstance();

    	    // initialize connection
    	    con = DriverManager.getConnection(SERVER+DATABASE, USERNAME, PASSWORD);

//    	    System.out.println("Connection established.");

    	    // initialize a query statement
    	    stmt = con.createStatement();

    	    // query db and save results
    	    res = stmt.executeUpdate(q, Statement.RETURN_GENERATED_KEYS);

    	    ResultSet rs = stmt.getGeneratedKeys();
    	    if (rs.next())
    	    	id = rs.getInt(1);
    	    rs.close();
    	    
    	    System.out.format("Query executed: '%s'\n", q);

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
//    		System.out.println("Connection terminated.\n");

    		if (res > 0)
    			return id;
    		else 
    			return res;
    	    } catch (Exception e) { /* ignore cleanup errors */ }
    	}
    	return -1;
    }
}
