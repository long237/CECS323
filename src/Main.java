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
        getDBname();
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
    
    //making sure this is working
    
}
