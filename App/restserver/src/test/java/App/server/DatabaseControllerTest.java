package App.server;
import static org.junit.Assert.*;

import org.junit.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DatabaseControllerTest {

    /**
     * Helper method for registering a user.
     *
     * @return boolean
     */
    @BeforeClass
    public static void registerUser() {
        UserController.registerUser("test2", "test2", 0, "test2");
        UserController.registerUser("test", "test", 0, "test");
    }

    public boolean registerUser2() {
        return UserController.registerUser("test2", "test2", 0, "test2");
    }

    /**
     * Test method for testing if registering a user works as intended.
     *
     * @throws SQLException Couldn't perform the SQL query
     * @throws ClassNotFoundException Couldn't find the UserController class.
     */

    @Test
    public void testRegister() throws SQLException, ClassNotFoundException {
        assertEquals(UserController.checkForUser("test"), true);
        assertEquals(UserController.checkForUser("test2"), true);
        assertFalse(UserController.registerUser("test", "test", 0, "test"));
    }

    @Test
    public void deleteUser() throws SQLException, ClassNotFoundException {
        String randomUserName1 = "abd" + Integer.toString(new Random().nextInt());
        String randomUserName2 = "abc" + Integer.toString(new Random().nextInt());
        UserController.registerUser(randomUserName1, randomUserName1, 0, randomUserName1);
        UserController.registerUser(randomUserName2, randomUserName2, 0, randomUserName2);
        assertTrue(UserController.deleteUser(randomUserName1));
    }

    /**
     * Method for testing if login functionality works
     *
     * @throws SQLException Couldn't perform the SQL query
     * @throws ClassNotFoundException Couldn't find the UserController class.
     */
    @Test
    public void loginUserTest() throws SQLException, ClassNotFoundException {
        ArrayList<Object> list = new ArrayList<>();
        list.add(true);
        list.add(112);
        assertEquals(UserController.login("Erlendos", "82d6a3979b40fa29242cdd95255bfad486e221baeac8b67d63bbe919a30ff195"), list);
    }

    /**
     * Method for testing if changing the admin status of a user works as intended.
     */
    @Test
    public void changeAdminTest() {
        UserController.updateAdmin("test", true);
        assertEquals(UserController.checkAdmin("test"), true);
    }

    @Test
    public void getUserTest() throws SQLException, ClassNotFoundException {
        ArrayList<String> list = new ArrayList<>();
        list.add("test");
        list.add("test");
        assertEquals(UserController.getUser("test"), list);
    }

    @Test
    public void getUserByIDTest() throws SQLException, ClassNotFoundException {
        ArrayList<ArrayList> userList = UserController.getUserIDs();
        ArrayList<Object> user = userList.get(userList.size()-1);


        assertEquals(UserController.getUserByID( String.valueOf(user.get(1))), user.get(0));
    }

    @Test
    public void checkForUserTest() throws SQLException, ClassNotFoundException {
        assertEquals(UserController.checkForUser("test"), true);
    }


    @Test
    public void findUserByDisplayNameTest() {
        assertTrue(UserController.findUser("test").size() > 0);
    }

    @Test
    public void addFriendTest() {
        ArrayList<ArrayList> users = UserController.getUserIDs();
        ArrayList<Object> user1 = users.get(users.size()-1);
        ArrayList<Object> user2 = users.get(users.size()-2);

        UserController.addFriend((Integer) user1.get(1), (Integer) user2.get(1));
        ArrayList<ArrayList> returnList = new ArrayList<>();
        ArrayList<Object> list = new ArrayList<>();
        list.add(user2.get(1));
        list.add(user2.get(0));
        returnList.add(list);
        assertEquals(ListController.getFriends((Integer) user1.get(1)), returnList);
    }

    @Test
    public void defaultMediumListsTest() {
        ListController.createInitialListsForUser(141);
        ArrayList<Object> list = new ArrayList<>();
        assertEquals(ListController.getList(141, "SerPå"), list);
    }

    @Test
    public void checkForMediumTest() {
        ArrayList<ArrayList> user = UserController.getUserIDs();
        int userInt = (int) user.get(user.size()-3).get(1);

        assertEquals(ListController.addMediumToList(userInt, "HarSett", 6), true);
    }

    @Test
    public void getAllMediumTest() {
        ArrayList<ArrayList> returnList = new ArrayList();
        returnList.add(new ArrayList<Object>(Arrays.asList(1, "Joker",
                "Todd Phillips",
                "Joker er en amerikansk thrillerfilm fra 2019 med Joaquin Phoenix i hovedrollen som den fiktive tittelfiguren."
        )));
        returnList.add(new ArrayList<Object>(Arrays.asList(2,
                "1917",
                "Sam Mendes",
                "1917 er en britisk krigsfilm fra 2019 med regi av Sam Mendes. Filmen vant to Golden Globes."
        )));
        returnList.add(new ArrayList<Object>(Arrays.asList(3,
                "Parasitt",
                "Bong Joon-ho",
                "Parasitt er en sørkoreansk tragikomisk thrillerfilm fra 2019, skrevet og regissert av Bong Joon-ho."
        )));
        returnList.add(new ArrayList<Object>(Arrays.asList(4,
                "Bad Boys for Life",
                "Bilall Fallah, Adil El Arbi",
                "Bad Boys for Life er en amerikansk buddy cop actionkomediefilm fra 2020."
        )));
        returnList.add(new ArrayList<Object>(Arrays.asList(5,
                "Breaking Bad",
                "Vince Gilligan",
                "Breaking Bad er en amerikansk drama- og kriminalserie som gikk på AMC fra 2008 til 2013. Serien handlet om kjemilæreren Walter White (Bryan Cranston), som fikk diagnosen lungekreft i begynnelsen av serien"
        )));
        returnList.add(new ArrayList<Object>(Arrays.asList(6,
                "Game of Thrones",
                "David Benioff, D. B. Weiss",
                "Game of Thrones er en amerikansk fantasy- og drama-TV-serie basert på fantasyserien En sang om is og ild av forfatteren George R.R. Martin, der den første boken har tittelen A Game of Thrones."
        )));
        assertEquals(ListController.getAllMedium(), returnList);

    }

    @Test
    public void getMediumFromListTest() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Game of Thrones");
        list.add("David Benioff, D. B. Weiss");
        list.add("Game of Thrones er en amerikansk fantasy- og drama-TV-serie basert på fantasyserien En sang om is og ild av forfatteren George R.R. Martin, der den første boken har tittelen A Game of Thrones.");
        assertEquals(ListController.getMediumFromList(6), list);
    }

    @Test
    public void getFilteredMediumTest() {
        ArrayList<ArrayList> returnList = new ArrayList<>();
        ArrayList<Object> list = new ArrayList<>();
        list.add(6);
        returnList.add(list);
        assertEquals(ListController.getFilteredMedium("Game").get(0).get(0), returnList.get(0).get(0));
    }

    /**
     * As the content of these will always change they just check that there is more than 1 user in the database.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Test
    public void getUsersTest() throws SQLException, ClassNotFoundException {
        assertTrue(UserController.getUsers().size() >  1);
    }

    @Test
    public void getUserIDsTest() {
        assertTrue(UserController.getUserIDs().size() > 0);
    }

    @AfterClass
    public static void cleanup() {
        String randomUsername = "abc" + Integer.toString(new Random().nextInt());
        UserController.registerUser(randomUsername, randomUsername, 0, randomUsername);
        UserController.deleteUser("test");
        UserController.deleteUser("test2");
    }

}
