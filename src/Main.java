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

public class Main {
    static String USER;
    static String PASS;
    static String DBNAME;
    
    static final String displayFormat="%-5s%-15s%-15s%-15s\n";
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";
    
    
    public static void main(String[] args){
        UserInterface UIobj = new UserInterface();

        //Asking for DB name
        DBNAME = getDBname();
        //Asking for username
        UIobj.printNameprompt();
        USER = getUserName();
        //Asking for password:
        UIobj.printPassprompt();
        PASS = getUserName();
        
        //Building String for connection
        DB_URL = DB_URL + DBNAME + ";user="+ USER + ";password=" + PASS;
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        
        try{
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            
            //Established connection to the database
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);
            
            System.out.println("Connect to " + DBNAME + " user: " + USER + " password: " + PASS);
            
            stmt = conn.createStatement();
            String sql = "Select booktitle from books";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                String n = rs.getString("booktitle");
                System.out.println(n);
                
            }
            
            conn.close();
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } 
        catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }
    
    //fixme this works even for no database name;
    public static String getDBname(){
        UserInterface UIobj = new UserInterface();
        String user_input = "-1";
        while (user_input == "-1" || user_input.length() < 1){
            UIobj.printDBprompt();
            user_input = UIobj.getUserInput();
        }
        return user_input;
    }
    
    public static String getUserName(){
        UserInterface UIobj = new UserInterface();
        String user_input = "-1";
        //username can be empty
        while (user_input == "-1"){
            //UIobj.printDBprompt();
            user_input = UIobj.getUserInput();
        }
        return user_input;
    }
    
    //making sure this is working
    
}
