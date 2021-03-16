
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
        System.out.println("Print the menu");
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
            // while (rs.next()) {
            // String wgName = rs.getString("GroupName");
            // String wgWriter = rs.getString("HeadWriter");
            // String wgYear = rs.getString("YearFormed");
            // String wgSubject = rs.getString("Subject");
            // }
        } catch (Exception e) {
            System.out.println(e);
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
            System.out.println("DONE!");
            return rs;
            // System.out.println("GroupName " + "HeadWriter " + " YearFormed" + " Subject"
            // + " BookTitle"
            // + " YearPublished" + " NumberPages");
            // while (rs.next()) {
            // String wgName = rs.getString("GroupName");
            // String wgWriter = rs.getString("HeadWriter");
            // String wgYear = rs.getString("YearFormed");
            // String wgSubject = rs.getString("Subject");

            // String bBookTitle = rs.getString("BookTitle");
            // String pYearPublished = rs.getString("YearPublished");
            // String bNumberPages = rs.getString("NumberPages");
            // }
        } catch (SQLException se) {
            se.printStackTrace();
            return null;
        }
    }

    // where clause = ?
    public void whereClause(Connection conn, Statement stmt, ResultSet rs) {
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
    public void listDataGroup(Connection conn) {
        Scanner in = new Scanner(System.in);
        int loop = 1;
        while (loop == 1) {
            try {
                Statement stmt = conn.createStatement();
                System.out.println("Choose a data group to be listed: ");
                String DataGroupcol = in.nextLine();
                if (DataGroupcol == "WritingGroups") {
                    loop = 0;
                    listDataGroup(conn);
                } else if (DataGroupcol == "Books") {
                    loop = 0;
                    listBook(conn);
                } else if (DataGroupcol == "Publisher") {
                    loop = 0;
                    listPublishers(conn);
                } else {
                    System.out.println("Invalid input, please try again!");
                }

                ResultSet rs = stmt.executeQuery(DataGroupcol);
                // return rs;
            } catch (Exception e) {
                System.out.println(e);
                // return null;
            }
        }

    }

    // List all publishers
    public ResultSet listPublishers(Connection conn) {
        try {
            String sql = "Select * from Publishers";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
            // while (rs.next()) {
            // String pName = rs.getString("PublisherName");
            // String pAddress = rs.getString("PublisherAddress");
            // String pPhone = rs.getString("PublisherPhone");
            // String pEmail = rs.getString("PublisherEmail");
            // }
        } catch (Exception e) {
            System.out.println(e);
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
            // while (rs.next()) {
            // String bBookTitle = rs.getString("BookTitle");
            // String pYearPublished = rs.getString("YearPublished");
            // String bNumberPages = rs.getString("NumberPages");
            // }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    // removes book
    public void removeBook(Connection conn, String uBook) {
        String sql = ("delete from " + uBook);
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println(uBook + " was sucessfully deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
