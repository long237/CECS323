
/** 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chunchunmaru
 */
import java.util.Scanner;
import java.sql.*;

public class UserInterface {

    public void printNameprompt() {
        System.out.println("Enter a username: ");
    }

    public void printDBprompt() {
        System.out.println("Enter a database name: ");
    }

    public void printPassprompt() {
        System.out.println("Enter a password: ");
    }

    public void printMenu() {
        System.out.println("Main Menu: \n"
                + "1.List all groups \n"
                + "2.List specific group\n"
                + "3.List all publishers\n"
                + "4.List specific publisher\n"
                + "5.List all book titles\n"
                + "6.List a specifc book\n"
                + "7.Insert a new book\n"
                + "8.Insert a publisher\n"
                + "9.Remove a specific book\n"
                + "10.Exit\n");
    }

    public void printAttri() {
        System.out.println("Enter an attribute to display (N to stop): ");
    }

    public String getUserInput() {
        Scanner in = new Scanner(System.in);
        String user_input = "";
        try {
            user_input = in.nextLine();
            return user_input;
        } catch (Exception e) {
            System.out.println("Invalid input, please try again.");
            return "-1";
        }
    }

    public void printResultSet(ResultSet rs) {
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCol = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= numCol; i++) {
                    String colValue = rs.getString(i);
                    System.out.println(colValue + " " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        }

    }

    // List all writing groups
    public ResultSet listWritingGroup(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            String WritingGroupscol = "Select * from WritingGroups";
            ResultSet rs = stmt.executeQuery(WritingGroupscol);
            return rs;
//            while (rs.next()) {
//                String wgName = rs.getString("GroupName");
//                String wgWriter = rs.getString("HeadWriter");
//                String wgYear = rs.getString("YearFormed");
//                String wgSubject = rs.getString("Subject");
//                
//                System.out.println(wgName);
//            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    // List all the data for a group specified by the user
    // ! This includes all the data for the associated books and publishers
    public static void specifiedData(Connection conn, Statement stmt) {
        try {
            // Books natural join writingGroups
            ResultSet rs = stmt.executeQuery("select * from Books " + "natural join " + "WritingGroups");
            System.out.println("GroupName " + "HeadWriter " + " YearFormed" + " Subject" + " BookTitle"
                    + " YearPublished" + " NumberPages");
            while (rs.next()) {
                String wgName = rs.getString("GroupName");
                String wgWriter = rs.getString("HeadWriter");
                String wgYear = rs.getString("YearFormed");
                String wgSubject = rs.getString("Subject");

                String bBookTitle = rs.getString("BookTitle");
                String pYearPublished = rs.getString("YearPublished");
                String bNumberPages = rs.getString("NumberPages");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // where clause = ?
    public static void whereClause(Connection conn, Statement stmt, ResultSet rs) {
        Scanner in = new Scanner(System.in);
        try {
            String whereC = "select * from WritingGroups" + " where YearFormed >= ?";
            PreparedStatement pstmt = conn.prepareStatement(whereC);
            pstmt.setInt(1, 2000);
            rs = pstmt.executeQuery();
            System.out.println("Writing groups that formed after the year 2000: ");
            while (rs.next()) {
                System.out.print("Group name: " + rs.getString("wgName"));
                System.out.print("Year Formed: " + rs.getString("wgYear") + "\n");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // extra method to list a data group specified by user
    public static void listDataGroup(Connection conn, Statement stmt) {
        Scanner in = new Scanner(System.in);
        int loop = 1;
        while (loop == 1) {
            try {
                System.out.println("Choose a data group to be listed: ");
                String DataGroupcol = in.nextLine();
                if (DataGroupcol == "WritingGroups") {
                    loop = 0;
                    listDataGroup(conn, stmt);
                } else if (DataGroupcol == "Books") {
                    loop = 0;
                    listBook(conn, stmt);
                } else if (DataGroupcol == "Publisher") {
                    loop = 0;
                    listPublishers(conn, stmt);
                } else {
                    System.out.println("Invalid input, please try again!");
                }

                ResultSet rs = stmt.executeQuery(DataGroupcol);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    // List all publishers
    public static void listPublishers(Connection conn, Statement stmt) {
        try {
            String ListPublisherscol = "String * from ListPublishers";
            ResultSet rs = stmt.executeQuery(ListPublisherscol);

            while (rs.next()) {
                String pName = rs.getString("PublisherName");
                String pAddress = rs.getString("PublisherAddress");
                String pPhone = rs.getString("PublisherPhone");
                String pEmail = rs.getString("PublisherEmail");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // listing books
    public static void listBook(Connection conn, Statement stmt) {
        try {
            String ListPublisherscol = "String * from Books";
            ResultSet rs = stmt.executeQuery(ListPublisherscol);

            while (rs.next()) {
                String bBookTitle = rs.getString("BookTitle");
                String pYearPublished = rs.getString("YearPublished");
                String bNumberPages = rs.getString("NumberPages");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
