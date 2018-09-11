import java.sql.*;
import java.util.*;


public class Music {

    public static void getAlbumFromArtist() {

        Connection c = null;
        Statement stmt = null;
        Scanner scan = new Scanner(System.in);
        ResultSet rs = null;
        String artname = "error";
        int num = 0;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:chinook.db");

            System.out.println("Opened database successfully");

            System.out.println("Input Artist Name (Exactly)");
            artname = scan.nextLine();
            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT a.Title, a.AlbumId  " + "FROM Album a, Artist art " + "WHERE a.ArtistId = art.ArtistId AND art.Name = '"+artname+"';");


            while (rs.next()) {
                int id = rs.getInt("AlbumId");
                String name = rs.getString("Title");

                System.out.println("ID = " + id);
                System.out.println("ALBUM NAME = " + name);
                System.out.println("ARTIST NAME = " + artname);
                System.out.println();
                num++;
            }

            if (num == 0) {
                System.out.println("No Albums found!!!");
            }


            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        System.out.println();
        System.out.println();
    }


    public static void getCustomerHistory() {

        Connection c = null;
        Statement stmt = null;
        Scanner scan = new Scanner(System.in);
        ResultSet rs = null;
        String customer = "error";

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:chinook.db");

            System.out.println("Opened database successfully");

            System.out.println("Input Customer ID");
            customer = scan.nextLine();
            stmt = c.createStatement();
            rs = stmt.executeQuery("Select t.Name, a.Title, il.Quantity, i.InvoiceDate " + "From Track t, Album a, InvoiceLine il, Invoice i " + "Where i.CustomerId = '" + customer + "' and i.InvoiceId = il.InvoiceId and il.TrackId = t.TrackId " + "and t.AlbumId = a.AlbumId ");

            if(rs.next()==false){
                System.out.println("!!Invalid, That Customer ID is not in record!!");
            }

            while (rs.next()) {
                String Tid = rs.getString("Name");
                String title = rs.getString("Title");
                int quantity = rs.getInt("Quantity");
                String date = rs.getString("InvoiceDate");

                System.out.println("TRACK NAME = " + Tid);
                System.out.println("ALBUM NAME = " + title);
                System.out.println("QUANTITY = " + quantity);
                System.out.println("DATE = " + date);
                System.out.println();
            }

            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        System.out.println();
        System.out.println();
    }


    public static void getUpdateTrackPrice() {

        Connection c = null;
        Statement stmt = null;
        Scanner scan = new Scanner(System.in);
        ResultSet rs = null;
        String trackid = "error";

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:chinook.db");

            System.out.println("Opened database successfully");

            System.out.println("Input Track ID");
            trackid = scan.nextLine();
            stmt = c.createStatement();
            rs = stmt.executeQuery("Select t.UnitPrice From Track t Where t.TrackId = '" + trackid + "';");

            while (rs.next()) {

                float price = rs.getFloat("UnitPrice");

                System.out.println("TRACK ID = " + trackid);
                System.out.println("PRICE = " + price);
                System.out.println();
            }

            System.out.println("--------------------------------------------------------------------");

            System.out.println("Enter the new UnitPrice: ");
            float newprice = scan.nextFloat();

            stmt = c.createStatement();
            stmt.executeUpdate("UPDATE Track SET UnitPrice = "+ newprice + " WHERE TrackId = "+ trackid + ";" );
            rs = stmt.executeQuery("Select t.UnitPrice From Track t Where t.TrackId = '" + trackid + "';");

            float price = rs.getFloat("UnitPrice");

            System.out.println("TRACK ID = " + trackid);
            System.out.println("PRICE = " + price);
            System.out.println();

            rs.close();
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        System.out.println();
        System.out.println();
    }


    public static void main(String args[]) {

        Scanner input = new Scanner(System.in);
        int loop = 0;

        while(loop == 0) {
            System.out.println("Choose the following Options:");
            System.out.println("1: Obtain Album title(s) based on Artist name ");
            System.out.println("2: Purchase History for a Customer");
            System.out.println("3: Update Individual Track Price");
            System.out.println("4: Exit");
            System.out.println("Enter the exact number of your choice below:");

            String option = input.next();

            switch (option) {
                case "1":
                    getAlbumFromArtist();
                    break;

                case "2":
                    getCustomerHistory();
                    break;

                case "3":
                    getUpdateTrackPrice();
                    break;
                case "4":
                    loop = 1;
                    System.out.println("Goodbye!");
                    break;

            }
        }
    }
}

