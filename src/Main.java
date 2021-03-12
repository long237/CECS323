/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chunchunmaru
 */
import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    static String USER;
    static String PASS;
    static String DBNAME;

    static final String displayFormat = "%-5s%-15s%-15s%-15s\n";
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";

    public static void main(String[] args) {
        UserInterface UIobj = new UserInterface();

        // Asking for DB name
        UIobj.printDBprompt();
        DBNAME = getUserInput();
        // Asking for username
        UIobj.printNameprompt();
        USER = getUserName();
        // Asking for password:
        UIobj.printPassprompt();
        PASS = getUserName();

        // Building String for connection
        DB_URL = DB_URL + DBNAME + ";user=" + USER + ";password=" + PASS;
        Connection conn = null; // initialize the connection
        Statement stmt = null; // initialize the statement that we're using

        try {
            // STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // Established connection to the database
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);

            System.out.println("Connect to " + DBNAME + " user: " + USER + " password: " + PASS);

            stmt = conn.createStatement();
            // String sql = "Select booktitle from books";
            String sql = "Select * from publishers natural join books natural join WritingGroups";
            ResultSet rs = stmt.executeQuery(sql);
            // while (rs.next()){
            // String n = rs.getString("booktitle");
            // System.out.println(n);
            //
            // }
            UIobj.printResultSet(rs);

            // Testing getting publisher function
            System.out.println("");
            System.out.println("Enter pub name: ");
            String userPub = getUserInput();
            ResultSet rs2 = getPublisher(conn, userPub);
            UIobj.printResultSet(rs2);
            
            //Testing get all book titles functions:
            System.out.println("");
            ResultSet rs3 = getAllBooks(conn);
            UIobj.printResultSet(rs3);

            // System.out.println("Printing col ....");
            // ArrayList colList = getColName(conn);
            // System.out.println(colList);
            //
            // //Testing searching for specific publisher
            // System.out.println("Enter a publisher name to look up: ");

            // ArrayList uattList = getPubAtt();
            // System.out.println("");
            // System.out.println(uattList);
            //
            // System.out.println("");
            // System.out.println("Check for validity: ");
            // System.out.println(checkAttri(uattList, colList));

            conn.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    // fixme this works even for no database name;
    public static String getUserInput() {
        UserInterface UIobj = new UserInterface();
        String user_input = "-1";
        while (user_input == "-1" || user_input.length() < 1) {
            user_input = UIobj.getUserInput();
        }
        return user_input;
    }

    public static String getUserName() {
        UserInterface UIobj = new UserInterface();
        String user_input = "-1";
        // username can be empty
        while (user_input == "-1") {
            // UIobj.printDBprompt();
            user_input = UIobj.getUserInput();
        }
        return user_input;
    }

    public static ResultSet getPublisher(Connection conn, String pubName) {
        try {
            PreparedStatement pStmt = conn.prepareStatement(
                    "select * from publishers natural join books natural join WritingGroups where PublisherName = ? ");
            pStmt.clearParameters();
            pStmt.setString(1, pubName);
            ResultSet rs = pStmt.executeQuery();
            return rs;
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
            return null;
        }
    }
    
    public static ResultSet getAllBooks(Connection conn){
        try{
            String sql = "SELECT booktitle FROM Books ";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        }
        catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
            return null;
        }
    }

    // Obtain all of the attributes name of the three sets.
    public static ArrayList getColName(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from writingGroups natural join Books natural join Publishers");
            ResultSetMetaData rsmd = rs.getMetaData();

            ArrayList colList = new ArrayList<String>();
            int numCol = rsmd.getColumnCount();
            for (int i = 1; i <= numCol; i++) {
                colList.add(rsmd.getColumnName(i));
            }
            return colList;
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
            return null;
        }
    }

    public static ArrayList getPubAtt() {
        ArrayList userList = new ArrayList<String>();
        UserInterface UIobj = new UserInterface();
        String user_input = "-1";
        // username can be empty
        while (user_input.equals("-1") || !(user_input.equals("N"))) {
            UIobj.printAttri();
            user_input = UIobj.getUserInput();
            if (!(user_input.equals("N"))) {
                userList.add(user_input);
            }
        }
        return userList;
    }

    public static boolean checkAttri(ArrayList<String> user_input, ArrayList<String> attriList) {
        for (int i = 0; i < user_input.size(); i++) {
            String userUpper = user_input.get(i).toUpperCase();
            if (!(attriList.contains(userUpper))) {
                return false;
            }
        }
        return true;

    }

    // making sure this is working

}
