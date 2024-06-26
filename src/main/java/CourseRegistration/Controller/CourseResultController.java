package CourseRegistration.Controller;

import CourseRegistration.DAO.CourseattendDAO;
import CourseRegistration.DAO.SemesterDAO;
import CourseRegistration.Main.App;
import CourseRegistration.POJO.Courseattend;
import CourseRegistration.POJO.Semester;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CourseResultController implements Initializable {
    public Label logout;
    public Label curSemLabel;
    @FXML
    TableView<Courseattend> table;
    @FXML
    TableColumn<Courseattend, String> col_id;
    @FXML
    TableColumn<Courseattend, String> col_courseName;
    @FXML
    TableColumn<Courseattend, Integer> col_credits;
    @FXML
    TableColumn<Courseattend, String> col_headTeacher;
    @FXML
    TableColumn<Courseattend, String> col_room;
    @FXML
    TableColumn<Courseattend, String> col_day;
    @FXML
    TableColumn<Courseattend, String> col_period;
    ObservableList<Courseattend> courseattendObservablelist = FXCollections.observableArrayList();
    @FXML
    private ComboBox<Semester> semesterList;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Semester> semList = FXCollections.observableArrayList();
        List<Semester> list = SemesterDAO.getSemesterList();
        semList.addAll(list);
        semesterList.setItems(semList);
        semesterList.setConverter(new StringConverter<>() {
            @Override
            public String toString(Semester sem) {
                if (sem != null)
                    return (sem.getId() + "/" + sem.getYear() % 100 + "-" + (sem.getYear() + 1) % 100);
                return "";
            }

            @Override
            public Semester fromString(String string) {
                return semesterList.getItems().stream().filter(ap ->
                        (ap.getId() + "/" + ap.getYear() % 100 + "-" + (ap.getYear() + 1) % 100).equals(string)).findFirst().orElse(null);
            }
        });
        if (list.size() > 0) {
            semesterList.setValue(list.get(0));
            List<Courseattend> courseAttendList = CourseattendDAO.getCourseattendList(App.getCurrentStudent().getId(), semesterList.getValue());
            courseattendObservablelist.addAll(courseAttendList);
            col_id.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCourseByCourseId().getSubjectBySubjectId().getId()));
            col_courseName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCourseByCourseId().getSubjectBySubjectId().getName()));
            col_credits.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCourseByCourseId().getSubjectBySubjectId().getCredits()).asObject());
            col_headTeacher.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCourseByCourseId().getTeacherName()));
            col_room.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCourseByCourseId().getRoom()));
            col_day.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCourseByCourseId().getDay()));
            col_period.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCourseByCourseId().getPeriod()));
            table.setItems(courseattendObservablelist);
            semesterList.setOnAction(e -> getCourseResult(semesterList.getValue()));
        }
    }
    public void getCourseResult(Semester sem) {
        table.getItems().clear();
        List<Courseattend> courseAttendList = CourseattendDAO.getCourseattendList(App.getCurrentStudent().getId(), sem);
        courseattendObservablelist.addAll(courseAttendList);
        col_id.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCourseByCourseId().getSubjectBySubjectId().getId()));
        col_courseName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCourseByCourseId().getSubjectBySubjectId().getName()));
        col_credits.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getCourseByCourseId().getSubjectBySubjectId().getCredits()).asObject());
        col_headTeacher.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCourseByCourseId().getTeacherName()));
        col_room.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCourseByCourseId().getRoom()));
        col_day.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCourseByCourseId().getDay()));
        col_period.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCourseByCourseId().getPeriod()));
        table.setItems(courseattendObservablelist);
    }

    @FXML
    public void exit() {
        App.exit();
    }

    @FXML
    public void minimize() {
        App.minimize();
    }

    @FXML
    public void onDashboard() throws IOException {
        App.changeScene("StudentDashboard");
    }

    @FXML
    public void logoutClicked() throws IOException, InterruptedException {
        Thread.sleep(300);
        App.changeScene("Login");
    }

    @FXML
    public void onReturn() throws IOException, InterruptedException {
        Thread.sleep(300);
        App.changeScene("StudentDashboard");
    }

}
