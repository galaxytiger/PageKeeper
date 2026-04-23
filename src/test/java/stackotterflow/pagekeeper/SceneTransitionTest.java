//package stackotterflow.pagekeeper;
//import javafx.stage.Stage;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.testfx.framework.junit5.ApplicationTest;
//import static org.testfx.api.FxAssert.verifyThat;
//import static org.testfx.matcher.base.NodeMatchers.isVisible;
//import static org.testfx.matcher.control.LabeledMatchers.hasText;
//
//
//
//public class SceneTransitionTest extends ApplicationTest {
//    @Override
//    public void start(Stage stage) throws Exception {
//        new HelloApplication().start(stage);
//    }
//
//    //Logs in as test user for testing purposes
//    private void logInAsValidUser() {
//        clickOn("#usernameField").write("testuser");
//        clickOn("#passwordField").write("password123");
//        clickOn("Log In");
//
//    }
//
//    private void logOut() {
//        clickOn("Logout");
//    }
//
//    @BeforeEach
//    void setUp() {
//    }
//
//    @AfterEach
//    void tearDown() {
//
//    }
//
//    @Test
//    public void logInNavigatesToDashboard() {
//        logInAsValidUser();
//        verifyThat("Welcome, testuser!", isVisible());
//        verifyThat("Refresh", isVisible());
//        verifyThat("Add Book", isVisible());
//        verifyThat("Edit", isVisible());
//        verifyThat("Delete", isVisible());
//        verifyThat("Logout", isVisible());
//        logOut();
//    }
//
//    @Test
//    public void deleteNoneSelected() {
//        logInAsValidUser();
//        clickOn("Delete");
//        verifyThat("Please select a book to delete.", isVisible());
//        verifyThat("OK", isVisible());
//        clickOn("OK");
//        logOut();
//    }
//
//    @Test
//    public void testLogOut() {
//        logInAsValidUser();
//        clickOn ("Logout)");
//        verifyThat("Log In", isVisible());
//    }
//}