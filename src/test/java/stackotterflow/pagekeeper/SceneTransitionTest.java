//package stackotterflow.pagekeeper;
//import javafx.stage.Stage;
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
//        //new Main().start(stage);
//    }
//
//    //Tests the login scene
//    private void loginAsValidUser() {
//        clickOn("#usernameField").write("testuser");
//        clickOn("#passwordField").write("password123");
//        clickOn("#loginButton");
//
//        verifyThat("#libraryRoot", isVisible());
//    }
//
//    @Test
//    public void testLoginNavigatesToLibrary() {
//        loginAsValidUser();
//        verifyThat("#userBooksTable", isVisible());
//    }
//}