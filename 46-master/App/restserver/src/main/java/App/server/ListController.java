package App.server;

import java.io.Serializable;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ListController {

    public static boolean createInitialListsForUser(int userID) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            Connection conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/simentaa_pu46?useSSL=false", "simentaa_pu46", "Kake46");

            String query = "INSERT INTO Liste (BrukerID, ListeTypeID, ListeNavn, Beskrivelse) VALUES ('" + userID + "',1,'SkalSe','Filmene og seriene jeg skal se')," +
                    "('" + userID + "',2,'SerNå','Filmene og seriene jeg ser nå')," +
                    "('" + userID + "',3,'HarSett','Filmene og seriene jeg har sett')," +
                    "('" + userID + "',4,'MineFavoritter','Mine favoritt filmer og serier')";
            Statement st = conn.createStatement();

            st.execute(query);
            st.close();
            conn.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }


    public static ArrayList<ArrayList> getList(int userID, String listName) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/simentaa_pu46?useSSL=false", "simentaa_pu46", "Kake46");
            String query = "SELECT MediumID FROM Liste NATURAL JOIN ListeAvMedium NATURAL JOIN ListeType WHERE BrukerID ='" + userID + "' AND Type ='" + listName + "'";
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery(query);
            ArrayList<Integer> list = new ArrayList<>();
            while (rs.next()) {
                list.add(rs.getInt("MediumID"));
            }

            ArrayList<ArrayList> returnList = new ArrayList<>();

            for (int item : list) {
                returnList.add(getMediumFromList(item));
            }
            conn.close();
            return returnList;
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<ArrayList>();
        }
    }

    public static ArrayList<String> getMediumFromList(int mediumID) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/simentaa_pu46?useSSL=false", "simentaa_pu46", "Kake46");
            String query = "SELECT * FROM Medium WHERE MediumID ='" + mediumID + "'";
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery(query);
            ArrayList<String> list = new ArrayList<>();
            while (rs.next()) {
                list.add(rs.getString("Tittel"));
                list.add(rs.getString("Regissør"));
                list.add(rs.getString("Beskrivelse"));
            }

            conn.close();
            return list;

        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    public static ArrayList<ArrayList> getAllMedium() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/simentaa_pu46?useSSL=false", "simentaa_pu46", "Kake46");
            String query = "SELECT * FROM Medium";
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery(query);
            ArrayList<ArrayList> returnList = new ArrayList<>();
            while (rs.next()) {
                ArrayList<Object> list = new ArrayList<>();
                list.add(rs.getInt("MediumID"));
                list.add(rs.getString("Tittel"));
                list.add(rs.getString("Regissør"));
                list.add(rs.getString("Beskrivelse"));
                returnList.add(list);
            }
            conn.close();
            return returnList;

        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }

    }

    public static ArrayList<ArrayList> getFilteredMedium(String filter) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/simentaa_pu46?useSSL=false", "simentaa_pu46", "Kake46");
            String query = "SELECT * FROM Medium WHERE Tittel LIKE '" + filter + "%'";
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery(query);
            ArrayList<ArrayList> returnList = new ArrayList<>();
            while (rs.next()) {
                ArrayList<Object> list = new ArrayList<>();
                list.add(rs.getInt("MediumID"));
                list.add(rs.getString("Tittel"));
                list.add(rs.getString("Regissør"));
                list.add(rs.getString("Beskrivelse"));
                returnList.add(list);
            }
            conn.close();
            return returnList;

        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }

    }

    public static boolean checkForMediumInList(int mediumID, int userID, String listName) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/simentaa_pu46?useSSL=false", "simentaa_pu46", "Kake46");
            String q = "SELECT MediumID FROM Liste NATURAL JOIN ListeAvMedium NATURAL JOIN ListeType WHERE BrukerID ='" + userID + "' AND Type ='" + listName + "'";
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery(q);
            ArrayList<Integer> newList = new ArrayList<>();
            while (rs.next()) {
                newList.add(rs.getInt("MediumID"));
            }

            for (int item : newList) {
                if (item == mediumID) {
                    return true;
                }
            }
            conn.close();
            return false;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }

    public static boolean addMediumToList(int userID, String listName, int mediumID) {
        try {
            if (checkForMediumInList(mediumID, userID, listName)) return false;

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/simentaa_pu46?useSSL=false", "simentaa_pu46", "Kake46");

            String query = "INSERT INTO ListeAvMedium(MediumID, ListeID) SELECT MediumID, ListeID FROM Medium, Liste NATURAL JOIN ListeType WHERE MediumID ='" + mediumID + "' AND BrukerID ='" + userID + "' AND Type = '" + listName + "'";
            Statement st = conn.createStatement();
            st.execute(query);
            st.close();
            conn.close();
            return true;
        } catch (Exception e ) {
            System.out.println(e);
            return false;
        }
    }


    public static ArrayList<ArrayList> getFriends(int userID) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/simentaa_pu46?useSSL=false", "simentaa_pu46", "Kake46");

            String query = "SELECT BrukerID, Visningsnavn FROM BrukerFølgerBruker INNER JOIN Bruker ON BrukerIDFølges = BrukerID WHERE BrukerIDFølger ='" + userID + "'";
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery(query);
            ArrayList<ArrayList> returnList = new ArrayList<>();

            while (rs.next()) {
                ArrayList<Object> list = new ArrayList<>();
                list.add(rs.getInt("BrukerID"));
                list.add(rs.getString("Visningsnavn"));
                returnList.add(list);
            }
            ps.close();
            conn.close();
            return returnList;
        } catch (Exception e ) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }





}



