package CourseRegistration.Main;

import CourseRegistration.Controller.CurrentUser;
import CourseRegistration.POJO.Student;
import CourseRegistration.POJO.Teacher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;

public class App extends Application {
    private static Scene scene;
    private static Stage stage;
    private static final CurrentUser currentUser = CurrentUser.getInstance();
    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;
        Parent root = loadFXML("Login");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/Controller/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void changeScene(String fxml) throws IOException {
        scene = new Scene(loadFXML(fxml));
        stage.setScene(scene);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    public static void exit() {
        Alert confirmExit = new Alert(Alert.AlertType.CONFIRMATION);
        confirmExit.setTitle("Exit");
        confirmExit.setHeaderText(null);
        confirmExit.setContentText("Bạn có chắc chắn muốn thoát?");
        Optional<ButtonType> option = confirmExit.showAndWait();
        if (option.isPresent())
            if (option.get() == ButtonType.OK) {
                System.exit(0);
            }
    }

    public static void minimize() {
        stage.setIconified(true);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void setUser(Teacher tch) {
        currentUser.setCurrentTeacher(tch);
    }
    public static Teacher getCurrentTeacher() {
        return currentUser.getCurrentTeacher();
    }
    public static void setUser(Student stu) {
        currentUser.setCurrentStudent(stu);
    }
    public static Student getCurrentStudent() {
        return currentUser.getCurrentStudent();
    }
}