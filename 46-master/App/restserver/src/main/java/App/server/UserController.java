package App.server;

import java.io.Serializable;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class UserController {


    /**
     * Method for registering a user.
     *
     * @param username The username that is going to be registered.
     * @param password The password that is going to be registered.
     * @param isAdmin A int that is either 0 or 1 and determines if the user has admin rights or not.
     * @param displayName The display name that is going to be registered.
     * @return A boolean that is either true or false depending on if the registering worked or not.
     */
    public static boolean registerUser (String username, String password, int isAdmin, String displayName) {
        try {
            if (checkForUser(username)) return false;

            Class.forName("com.mysql.cj.jdbc.Driver");
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");

            Connection conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/simentaa_pu46?useSSL=false", "simentaa_pu46", "Kake46");

            String query = "INSERT INTO Bruker (BrukerID, Brukernavn, Passord, Admin, Dato, Visningsnavn) VALUES(default" + "," + "\"" + username + "\"" + "," + "\"" + password + "\"" + "," + "\"" + 1 + "\"" + "," + "\"" + formatter.format(new Date(System.currentTimeMillis())) + "\"" + "," + "\"" + displayName + "\"" + ");";
            Statement st = conn.createStatement();

            st.execute(query);
            st.close();
            conn.close();
            return true;



        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
            return false;

        }
    }

    /**
     * A method for logging the user in and out. It checks whether the username and password is the same as in the database, and if so it returns true, else false.
     *
     * @param username The username that the user is trying to log in with.
     * @param password The password that the user is trying to log in with.
     * @return Returns true or false depending on if the login was successful. Also returns the username
     * @throws SQLException if it can't connect to the database or the SQL query isn't working for some reason.
     * @throws ClassNotFoundException if it can't find the class.
     */
    public static List<Serializable> login(String username, String password) throws SQLException, ClassNotFoundException {
        if (! checkForUser(username)) return Arrays.asList(false);
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/simentaa_pu46?useSSL=false", "simentaa_pu46", "Kake46");

        String query = "SELECT * FROM Bruker WHERE Brukernavn ='" + username + "'";
        Statement ps = conn.createStatement();
        ResultSet rs = ps.executeQuery(query);
        //This if statement is necessary to prevent it from throwing an error.
        if (rs.next()) {
            if (rs.getString("Passord").equals(password)) {

                return Arrays.asList(true, rs.getInt("BrukerID"));
            }
        }
        conn.close();
        return Arrays.asList(false);


    }

    /**
     * A method to check if a user exists in the database.
     *
     * @param username The username that is getting checked against the database.
     * @return Returns a boolean based on if the user exists or not.
     * @throws SQLException if it can't connect to the database or the SQL query isn't working for some reason.
     * @throws ClassNotFoundException if it can't find the class.
     */
    public static boolean checkForUser(String username) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/simentaa_pu46?useSSL=false", "simentaa_pu46", "Kake46");
        String query = "SELECT * FROM Bruker WHERE Brukernavn='" + username + "'";
        Statement ps = conn.createStatement();
        ResultSet rs = ps.executeQuery(query);
        if (rs.next()) {
            rs.close();
            conn.close();
            return true;
        }
        rs.close();
        return false;
    }

    /**
     * A method for returning the username and hashed password of a user.
     *
     * @param username The username of the user you want to return the username and password of
     * @return An arraylist with the username and password.
     * @throws SQLException if it can't connect to the database or the SQL query isn't working for some reason.
     * @throws ClassNotFoundException if it can't find the class.
     */
    public static ArrayList<String> getUser(String username) throws SQLException, ClassNotFoundException {
        if (!checkForUser(username)) return new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/simentaa_pu46?useSSL=false", "simentaa_pu46", "Kake46");
        String query = "SELECT  * FROM Bruker WHERE Brukernavn = '" + username + "'";
        Statement ps = conn.createStatement();
        ResultSet rs = ps.executeQuery(query);
        ArrayList<String> list = new ArrayList<>();
        if (rs.next()) {
            list.add(rs.getString("Brukernavn"));
            list.add(rs.getString("Passord"));

        }
        conn.close();
        return list;
    }

    public static String getUserByID(String ID) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/simentaa_pu46?useSSL=false", "simentaa_pu46", "Kake46");
        String query = "SELECT  * FROM Bruker WHERE BrukerID = '" + ID + "'";
        Statement ps = conn.createStatement();
        ResultSet rs = ps.executeQuery(query);
        if (rs.next()) {
            return rs.getString("Brukernavn");

        }
        conn.close();
        return "";
    }

    /**
     * A method for listing out all the username and password combinations.
     *
     * @return A two dimensional arraylist with all the username and password combinations.
     * @throws SQLException if it can't connect to the database or the SQL query isn't working for some reason.
     * @throws ClassNotFoundException if it can't find the class.
     */
    public static ArrayList<ArrayList> getUsers() throws SQLException, ClassNotFoundException {
        ArrayList<ArrayList> list = new ArrayList<>();
        ArrayList<String> userList;
        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/simentaa_pu46?useSSL=false", "simentaa_pu46", "Kake46");
        String query = "SELECT  * FROM Bruker";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery(query);
        while (rs.next()) {
            userList = new ArrayList<>();
            userList.add(rs.getString("Brukernavn"));
            userList.add(rs.getString("Passord"));
            list.add(userList);
        }
        conn.close();
        return list;
    }

    public static ArrayList<ArrayList> getUserIDs() {
        try {
            ArrayList<ArrayList> list = new ArrayList<>();
            ArrayList<Object> userList;
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/simentaa_pu46?useSSL=false", "simentaa_pu46", "Kake46");
            String query = "SELECT  * FROM Bruker";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                userList = new ArrayList<>();
                userList.add(rs.getString("Brukernavn"));
                userList.add(rs.getInt("BrukerID"));
                list.add(userList);
            }
            conn.close();
            return list;

        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    /**
     *A method for deleting a user from the database.
     *
     * @param username The username of the user you want to delete.
     * @return Returns either true or false based on if the deletion was successful.
     */
    public static boolean deleteUser(String username) {
        try {


            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/simentaa_pu46?useSSL=false", "simentaa_pu46", "Kake46");

            String query = "DELETE FROM Bruker WHERE Brukernavn='" + username + "'";
            Statement ps = conn.createStatement();
            ps.execute(query);

            conn.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * A method for changing the admin status of a user in the database.
     *
     * @param username The user you want change the admin status of.
     * @param updateValue The value you want to update it to. 1 if you want them to become admin, or 0 if you want to remove their admin rights.
     * @return true if the value was updated successfully, else false.
     */
    public static boolean updateAdmin(String username, boolean updateValue) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/simentaa_pu46?useSSL=false", "simentaa_pu46", "Kake46");

            String query = "UPDATE Bruker SET Admin = " + updateValue + " WHERE Brukernavn = '" + username + "'";
            Statement ps = conn.createStatement();
            ps.execute(query);

            conn.close();
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * A method for checking if a user is admin or not.
     * @param username The username of the user you want to figure out if is admin or not.
     * @return Returns either true or false based on if the user is admin or not. Returns false on error.
     */
    public static boolean checkAdmin(String username) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/simentaa_pu46?useSSL=false", "simentaa_pu46", "Kake46");
            String query = "SELECT  Admin FROM Bruker WHERE Brukernavn ='" + username + "'";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery(query);
            if (rs.next()) {
                return rs.getBoolean("Admin");
            }
            return false;


        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }


    public static ArrayList<ArrayList> findUser(String displayName) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/simentaa_pu46?useSSL=false", "simentaa_pu46", "Kake46");

            String query = "SELECT BrukerID, Brukernavn, Admin, Dato, Visningsnavn FROM Bruker WHERE Visningsnavn LIKE '" + displayName + "%'";
            Statement ps = conn.createStatement();
            ResultSet rs = ps.executeQuery(query);
            ArrayList<ArrayList> returnList = new ArrayList<>();

            while (rs.next()) {
                ArrayList<Object> list = new ArrayList<>();
                list.add(rs.getInt("BrukerID"));
                list.add(rs.getString("Brukernavn"));
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


    public static boolean addFriend(int userID, int friendID) {
        try {
            ArrayList<ArrayList> list = ListController.getFriends(userID);
            for (ArrayList<Object> item : list) {
                if (item.get(0).equals(friendID)) {
                    return false;
                }
            }
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no:3306/simentaa_pu46?useSSL=false", "simentaa_pu46", "Kake46");

            String query = "INSERT INTO BrukerFølgerBruker(BrukerIDFølger, BrukerIDFølges) VALUES ('" + userID + "','" + friendID + "')";
            Statement ps = conn.createStatement();
            ps.execute(query);
            conn.close();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }




}
