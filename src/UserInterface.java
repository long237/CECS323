
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
        System.out.println("Main Menu: \n" + "1.List all groups \n" + "2.List specific group\n"
                + "3.List all publishers\n" + "4.List specific publisher\n" + "5.List all book titles\n"
                + "6.List a specifc book\n" + "7.Insert a new book\n" + "8.Insert a publisher\n"
                + "9.Remove a specific book\n" + "10.Exit\n");
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

    public int getUserInt() {
        Scanner in = new Scanner(System.in);
        int user_input = -1;
        try {
            user_input = in.nextInt();
            return user_input;
        } catch (Exception e) {
            System.out.println("Invalid menu option, please try again.");
            return -1;
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
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Failed to list writing books");
            return null;
        }
    }

    // List all the data for a group specified by the user
    // ! This includes all the data for the associated books and publishers
    public ResultSet specifiedData(Connection conn, String uGroup) {
        try {
            // Books left join writingGroups
            PreparedStatement pStmt = conn.prepareStatement(
                    "select * from WritingGroups left outer join Books using (GroupName) left outer join Publishers using (PublisherName) where GroupName = ?");
            pStmt.clearParameters();
            pStmt.setString(1, uGroup);
            ResultSet rs = pStmt.executeQuery();
            return rs;
        } catch (SQLException se) {
            se.printStackTrace();
            System.out.println("Sorry, Invalid input");
            return null;
        }
    }

    /*
     * // where clause = ? |dont need| public void whereClause(Connection conn,
     * Statement stmt, ResultSet rs) { Scanner in = new Scanner(System.in); try {
     * String whereC = "select * from WritingGroups" + " where YearFormed >= ?";
     * PreparedStatement pstmt = conn.prepareStatement(whereC); pstmt.setInt(1,
     * 2000); rs = pstmt.executeQuery();
     * System.out.println("Writing groups that formed after the year 2000: "); while
     * (rs.next()) { System.out.print("Group name: " + rs.getString("wgName"));
     * System.out.print("Year Formed: " + rs.getString("wgYear") + "\n"); } } catch
     * (Exception e) { System.out.println(e); } }
     * 
     * 
     * // extra method to list a data group specified by user public void
     * listDataGroup(Connection conn) { Scanner in = new Scanner(System.in); int
     * loop = 1; while (loop == 1) { try { Statement stmt = conn.createStatement();
     * System.out.println("Choose a data group to be listed: "); String DataGroupcol
     * = in.nextLine(); if (DataGroupcol == "WritingGroups") { loop = 0;
     * listDataGroup(conn); } else if (DataGroupcol == "Books") { loop = 0;
     * listBook(conn); } else if (DataGroupcol == "Publisher") { loop = 0;
     * listPublishers(conn); } else {
     * System.out.println("Invalid input, please try again!"); } ResultSet rs =
     * stmt.executeQuery(DataGroupcol); // return rs; } catch (Exception e) {
     * System.out.println(e); // return null; } } }
     */
    // List all publishers
    public ResultSet listPublishers(Connection conn) {
        try {
            String sql = "Select * from Publishers";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Failed to list publisher");
            return null;
        }
    }

    // listing books
    public ResultSet listBook(Connection conn) {
        try {
            String sql = "Select * from Books";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Failed to list book");
            return null;
        }
    }

    // removes book
    public void removeBook(Connection conn, String uBook, String uBook2) {
        try {
            PreparedStatement pStmt = conn.prepareStatement("delete from books where BookTitle = ? and GroupName = ?");
            pStmt.clearParameters();
            pStmt.setString(1, uBook);
            pStmt.setString(2, uBook2);
            pStmt.executeUpdate();
            System.out.println(uBook + " was sucessfully deleted");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Sorry, invalid book inputted");
        }
    }
}
