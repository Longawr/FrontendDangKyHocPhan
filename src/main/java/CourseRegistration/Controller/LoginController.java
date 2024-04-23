package CourseRegistration.Controller;

import CourseRegistration.DAO.AccountDAO;
import CourseRegistration.DAO.StudentDAO;
import CourseRegistration.DAO.TeacherDAO;
import CourseRegistration.DAO.TokenDAO;
import CourseRegistration.Main.App;
import CourseRegistration.POJO.Account;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class LoginController {
    public Button btnLogin;
    public Label forgotLink;
    @FXML
    private TextField userField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label warning;
    private Parent root;

    @FXML
    public void onSubmit(Event event){
        boolean check = false;
        String username = userField.getText();
        String password = passwordField.getText();
        if (TokenDAO.getToken(username, password) == HttpStatus.OK) {
            Account acc = AccountDAO.getAccountByID(username);
            try {
                Thread.sleep(300);
                FXMLLoader fxmlLoader;
                if (acc.getRole().equals("GV")) {
                    fxmlLoader = new FXMLLoader(App.class.getResource("/Controller/TeacherDashboard.fxml"));
                    root = fxmlLoader.load();
                    TeacherDashboardController dashboardController = fxmlLoader.getController();
                    dashboardController.setCurrentTeacher(TeacherDAO.getTeacherByUsername(username), fxmlLoader);
                } else if (acc.getRole().equals("SV")) {
                    fxmlLoader = new FXMLLoader(App.class.getResource("/Controller/StudentDashboard.fxml"));
                    root = fxmlLoader.load();
                    StudentDashboardController dashboardController = fxmlLoader.getController();
                    dashboardController.setCurrentStudent(StudentDAO.getStudentByUsername(username), fxmlLoader);
                }
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
            } catch (IOException | InterruptedException ioException) {
                ioException.printStackTrace();
            }
            check = true;
        }
        if (!check) {
            try {
                Thread.sleep(300);
                warning.setVisible(true);
            } catch (InterruptedException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @FXML
    public void submitKeyPressed(KeyEvent k){
        if (k.getCode().equals(KeyCode.ENTER))
            onSubmit(k);
    }

    @FXML
    public void exit() {
        App.exit();
    }

    @FXML
    public void minimize() {
        App.minimize();
    }
}
