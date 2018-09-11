import java.sql.*;
import java.util.*;


public class music2 {

    public static void gettopSellersVol() {

        Connection c = null;
        Statement stmt = null;
        //Scanner scan = new Scanner(System.in);
        ResultSet rs = null;
        String customer = "error";
        int num = 0;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:chinook.db");

            System.out.println("Opened database successfully");

            System.out.println("Enter CustomerID:");
            //customer = scan.nextLine();
            stmt = c.createStatement();
            rs = stmt.executeQuery("Select distinct at.ArtistId, at.Name, il.Quantity " + "From Artist at, Album ab, Track t, InvoiceLine il " + "Where il.TrackId = t.TrackId and t.AlbumId = ab.AlbumId and " + "ab.ArtistId = at.ArtistId and il.Quantity = " + "(Select MAX(Quantity) From InvoiceLine)");


            while (rs.next()) {
                int id = rs.getInt("ArtistId");
                String name = rs.getString("Name");
                int quantity = rs.getInt("Quantity");

                System.out.println("ARTIST NAME = " + name);
                System.out.println("ARTIST ID = " + id );
                System.out.println("QUANTITY = " + quantity);
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




    public static void getrecommender() {

        Connection c = null;
        Statement stmt = null;
        Scanner scan = new Scanner(System.in);
        ResultSet rs = null;
        String customer = "error";
        int num = 0;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:chinook.db");

            System.out.println("Opened database successfully");

            System.out.println("Enter CustomerID:");
            customer = scan.nextLine();
            stmt = c.createStatement();
            rs = stmt.executeQuery("Select t.Name, t.TrackId, a.Title " + "From Track t, Album a, Invoice i, InvoiceLine il " + "Where i.CustomerId = " + customer + " and i.InvoiceId = il.InvoiceId and " + "il.TrackId = t.TrackId and t.AlbumId = a.AlbumId " + "group by t.albumId " + "having count(t.albumId) >= 3");

            while (rs.next()) {
                int id = rs.getInt("TrackId");
                String name = rs.getString("Name");
                String title = rs.getString("Title");

                System.out.println("Recommended for Customer ID = " + customer);
                System.out.println("ALBUM NAME = " + title);
                System.out.println("Track NAME = " + name);
                System.out.println("Track ID = " + id);
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


    public static void main(String args[]) {

        Scanner input = new Scanner(System.in);
        int loop = 0;

        while(loop == 0) {
            System.out.println("Choose the following Options:");
            System.out.println("1: Simple Track Recommender ");
            System.out.println("2: Top sellers by volume");
            System.out.println("3: Exit");
            System.out.println("Enter the exact number of your choice below:");

            String option = input.next();

            switch (option) {
                case "1":
                    getrecommender();
                    break;

                case "2":
                    gettopSellersVol();
                    break;

                case "3":
                    loop = 1;
                    System.out.println("Goodbye!");
                    break;

            }
        }
    }

}
