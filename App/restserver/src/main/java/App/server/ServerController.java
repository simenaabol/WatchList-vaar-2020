package App.server;


import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
public class ServerController {


    /**
     * The endpoint for returning an arraylist of all the users and their passwords.
     *
     * @return Returns a two-dimensional arraylist of all the users and their hashed passwords.
     * @throws SQLException if it can't connect to the database or the SQL query isn't working for some reason.
     * @throws ClassNotFoundException if it can't find the class.
     */
    @GetMapping("/users")
    public ArrayList<ArrayList> index() throws SQLException, ClassNotFoundException {
        return UserController.getUsers();
    }

    @GetMapping("/users/withID")
    public ArrayList<ArrayList> withID() {
        return UserController.getUserIDs();
    }

    /**
     * The endpoint for returning the username and password of a user.
     *
     * @param username
     * @return An arraylist of the username and password of a user.
     ** @throws SQLException if it can't connect to the database or the SQL query isn't working for some reason.
     *      * @throws ClassNotFoundException if it can't find the class.
     */
    @GetMapping("/users/{username}")
    public ArrayList<String> getUser(@PathVariable String username) throws SQLException, ClassNotFoundException {
        return UserController.getUser(username);
    }

    @GetMapping("/users/byID/{ID}")
    public String getUserByID(@PathVariable String ID) throws SQLException, ClassNotFoundException {
        return UserController.getUserByID(ID);
    }

    /**
     * The endpoint for checking if a user is trying to log in with the right credentials.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return True or false based on if the log in is successful or not.
     * @throws SQLException if it can't connect to the database or the SQL query isn't working for some reason.
     * @throws ClassNotFoundException if it can't find the class.
     */
    @PostMapping("/login")
    public List<Serializable> login(@RequestParam String username, @RequestParam String password) throws SQLException, ClassNotFoundException {
        return UserController.login(username, password);
    }

    /**
     * The endpoint for registering a user.
     *
     * @param username Chosen username.
     * @param password Chosen hashed password
     * @param isAdmin If the user should be admin or not. Default should be 0.
     * @param displayName Chosen displayname.
     */
    @PostMapping("/users")
    public boolean register(@RequestParam String username, @RequestParam String password, @RequestParam int isAdmin, @RequestParam String displayName) throws SQLException, ClassNotFoundException {
        ArrayList<ArrayList> userIDList = UserController.getUserIDs();
        int lastID = 0;
        for (ArrayList<Object> item : userIDList) {
            lastID = (int) item.get(1);
        }
        UserController.registerUser(username, password, isAdmin, displayName);
        ListController.createInitialListsForUser(lastID +1);
        return !UserController.getUser(username).get(0).equals("");

    }

    @PostMapping("/users/{id}/createLists")
    public boolean createLists(@PathVariable int id) {
        return ListController.createInitialListsForUser(id);
    }


    /**
     * The endpoint for deleting a user.
     *
     * @param username The username of the user you want to delete.
     * @return Either true or false based on if the deletion was successful.
     * @throws SQLException if it can't connect to the database or the SQL query isn't working for some reason.
     * @throws ClassNotFoundException if it can't find the class.
     */
    @DeleteMapping("users/{username}")
    public boolean delete(@PathVariable String username) throws SQLException, ClassNotFoundException {
        return UserController.deleteUser(username);
    }

    /**
     * The endpoint for updating the username of a user. TODO: Finish this method.
     *
     * @param username The current username.
     * @param name The new username you want to change it to.
     * @return Currently only returns true. Must be updated.
     */
    @PatchMapping("users/{username}/changeUsername")
    public boolean updateUsername(@PathVariable String username, @RequestParam String name) {
        return true;
    }
    /**
     * The endpoint for updating the username of a user. TODO: Finish this method.
     *
     * @param username The username of the user you want to change the password of.
     * @param password The new password you want to change it to.
     * @return Currently only returns true. Must be updated.
     */
    @PatchMapping("users/{username}/changePassword")
    public boolean updatePassword(@PathVariable String username, @RequestParam String password) {
        return true;
    }

    /**
     * The endpoint for changing the admin status of a user.
     *
     * @param username The username of the user you want to change the admin status of
     * @param updateValue The value you want to update the admin status to. 1 = admin, 0 = not admin.
     * @return Either true or false based on if the update was successful.
     */
    @PatchMapping("users/{username}/updateAdmin")
    public boolean updateAdmin(@PathVariable String username, @RequestParam boolean updateValue) {
        return UserController.updateAdmin(username, updateValue);
    }

    /**
     * The endpoint for checking the admin status of a user.
     *
     * @param username The username of the user you want to check the status of.
     * @return Either true or false based on if the user has admin rights or not.
     */
    @GetMapping("users/{username}/checkAdmin")
    public boolean checkAdmin(@PathVariable String username) {
        return UserController.checkAdmin(username);
    }








//////////////////////////////////////////////////////////////////////////LISTS////////////////////////////////////////////////////////////////



    @GetMapping("list/byID/{userID}")
    public ArrayList<ArrayList> getMovies(@PathVariable int userID, @RequestParam String listName) {
            return ListController.getList(userID, listName);
    }

    @GetMapping("medium/{mediumID}")
    public ArrayList<String> getMedium(@PathVariable int mediumID) {
        return ListController.getMediumFromList(mediumID);
    }

    @GetMapping("list/allMedium")
    public ArrayList<ArrayList> getAllMedium() {
        return ListController.getAllMedium();
    }

    @GetMapping("list/filteredMedium")
    public ArrayList<ArrayList> getFilteredMedium(@RequestParam String filter) {
        return ListController.getFilteredMedium(filter);
    }

    @GetMapping("list/{userID}/checkForMedium/{mediumID}")
    public boolean checkForMediumInList(@PathVariable int userID, @RequestParam String listName, @PathVariable int mediumID) {
        return ListController.checkForMediumInList(mediumID, userID, listName);
    }

    @PostMapping("list/{userID}/addMedium/{mediumID}")
    public boolean addMediumToList(@PathVariable int userID, @RequestParam String listName, @PathVariable int mediumID) {
        return ListController.addMediumToList(userID, listName, mediumID);
    }


    //////////////////////////////////////////FRIENDS/////////////////////////////////////////////////////////////////

    @GetMapping("friends/{userID}/getAllFriends")
    public ArrayList<ArrayList> getAllFriends(@PathVariable int userID) {
        return ListController.getFriends(userID);
    }

    @GetMapping("users/findUser")
    public ArrayList<ArrayList> findUser(@RequestParam String displayName) {
        return UserController.findUser(displayName);
    }

    @PostMapping("friends/{userID}/addFriend/{friendID}")
    public boolean addFriend(@PathVariable int userID, @PathVariable int friendID) {
        return UserController.addFriend(userID, friendID);
    }


}

