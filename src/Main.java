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

//            // Testing getting publisher function
            System.out.println("");
            System.out.println("Test getting a specific publisher");
            System.out.println("Enter pub name: ");
            String userPub = getUserInput();
            ResultSet rs2 = getPublisher(conn, userPub);
            UIobj.printResultSet(rs2);
//            
//            //Testing get all book titles functions:
//            System.out.println("");
//            System.out.println("Testing print all book titles");
//            ResultSet rs3 = getAllBooks(conn);
//            UIobj.printResultSet(rs3);
//            
//            //Testing getting a single book: 
//            System.out.println("");
//            System.out.println("Enter a book title: ");
//            String uTitle = getUserInput();
//            System.out.println("Enter a Group Name: ");
//            String uGroup = getUserInput();
//            ResultSet rs4 = getBook(conn, uTitle, uGroup);
//            UIobj.printResultSet(rs4);
            
            //Testing inserting a Publisher to the table. 
//            System.out.println("");
//            System.out.println("Enter a publisher name: ");
//            String uPubName = getUserInput();
//            System.out.println("Enter the publisher address: ");
//            String uAddr = getUserInput();
//            System.out.println("Enter publisher phone: ");
//            String uPhone = getUserInput();
//            System.out.println("Enter publisher email: ");
//            String uEmail = getUserInput();
//            Boolean result = insertPub(conn, uPubName, uAddr, uPhone, uEmail);
//            if (result){
//                System.out.println("Insert new publisher succesful");
//            }
//            System.out.println("Enter the name of the publisher being replaced: ");
//            System.out.println("");
//            String oldName = getUserInput();
//            Boolean buyRes = buyOutPub(conn, uPubName, oldName);
//            if (buyRes){
//                System.out.println("Update books publishername successfully");
//            }
//            System.out.println("");
//            System.out.println("Rifat");
//            ResultSet rs3 = UIobj.listWritingGroup(conn);
//            UIobj.printResultSet(rs3);

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
            System.out.println("Error when connecting to database !!!");
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

    //This function will also display any Publisher that have yet to publish a book. 
    public static ResultSet getPublisher(Connection conn, String pubName) {
        try {
            PreparedStatement pStmt = conn.prepareStatement(
                    "select * from publishers left outer join books using (PublisherName) "
                            + "left outer join WritingGroups using (groupname)"
                            + "where PublisherName = ?");
            pStmt.clearParameters();
            pStmt.setString(1, pubName);
            ResultSet rs = pStmt.executeQuery();
            System.out.println("Execute successfully");
            return rs;
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
            return null;
        }
    }
    
    //Return a result set of all of the title of the books
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
    
    public static ResultSet getBook(Connection conn, String uTitle, String uGroupName){
        try{
            PreparedStatement pStmt = conn.prepareStatement(
                    "Select * from Books where BookTitle = ? or GroupName = ?");
            pStmt.setString(1, uTitle);
            pStmt.setString(2, uGroupName);
            ResultSet rs = pStmt.executeQuery();
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
    
    public static boolean buyOutPub(Connection conn, String newName, String oldName){
        try{
            PreparedStatement pStmt = conn.prepareStatement("UPDATE Books SET publisherName = ? WHERE publisherName = ?");
            pStmt.clearParameters();
            //the new name of the publisher 
            pStmt.setString(1, newName);
            //Specifies the publisher name to be change
            pStmt.setString(2, oldName);
            int res = pStmt.executeUpdate();
            return true;
        }
         catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
            System.out.println("Buying out a publisher operation failed !!!");
            return false;
        }
    }
    
    public static boolean insertPub(Connection conn, String uPubName, String uPubAddr, String uPhone, String uEmail){
        try{
            PreparedStatement pStmt = conn.prepareStatement("Insert into Publishers(PublisherName, PublisherAddress, PublisherPhone, PublisherEmail) values"
                    + "(?, ?, ?, ?) ");
            pStmt.clearParameters();
            pStmt.setString(1, uPubName);
            pStmt.setString(2, uPubAddr);
            pStmt.setString(3, uPhone);
            pStmt.setString(4, uEmail);
            int numRes = pStmt.executeUpdate();
            return true;
            
        }
        catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
            System.out.println("Inserting publisher name failed. ");
            return false;
        }
    }

    // making sure this is working

}
