
// slide 390
import java.sql.*;
import java.util.*;

public class list3 {
    // change to ours (setup)
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static final String DB_URL = "jdbc:derby://localhost:1527/testdb"; // “jdbc:mysql://<URL>:3306/<DB>?user=<user>&password=<pw>”
    static final String USER = "username";
    static final String PASS = "password";

    public static void main(String args[]) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Connection con = null;
        Statement stmt = null;
        try {
            // STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 3: Open a connection
            System.out.println("Connecting to database...");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt1 = con.createStatement();
            Statement stmt2 = con.createStatement();
            // STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = con.createStatement();
            // List all writing groups
            ResultSet rs1 = stmt1.executeQuery("SELECT * FROM WritingGroups");
            while (rs1.next()) {
                System.out.println(rs1.getString("GroupName")+", "+rs1.getString("HeadWriter")+", "+rs1.getString("YearFormed")+", "+rs1.getString("Subject"));
            }
            catch (Exception e) {
                System.out.println(e);
            }
            // List all the data for a group specified by the user
            // This includes all the data for the associated books and publishers
            System.out.print("Choose a data group to be listed: ");
            String three = scanner.nextLine();
            ResultSet rs2 = stmt.executeQuery("select * from " + three);
            //while (rs.next()) {
            //System.out.println(rs.getString("____")+", "+rs.getString("____")+", ...);
            //}

            // List all the publishers
            ResultSet rs3 = stmt.executeQuery("select * from Publisher");
            while (rs3.next()) {
                System.out.println(rs3.getString("PublisherName") + ", " + rs3.getString("PublisherAddress") + ", " +rs3.getString("PublisherPhone") + ", " +rs3.getString("PublisherEmail"));
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
            // STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            con.close();
        System.out.println("Goodbye!");
    }// end main
}// end FirstExample

/**
 * 
 * 
 * 
 * 
 * System.out.print("Choose a data group to be listed: ");dataGroup=
 * scan.nextline ResultSet rs=stmt.executeQuery("Select ")while(rs.next()) {
 * stmt.executeQuery("Select * from " + rs); int s = rs.getInt("") }
 * 
 * 
 */