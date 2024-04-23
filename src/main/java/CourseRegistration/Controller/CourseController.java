package CourseRegistration.Controller;

import CourseRegistration.DAO.CourseDAO;
import CourseRegistration.DAO.CurrentSemesterDAO;
import CourseRegistration.Main.App;
import CourseRegistration.POJO.Course;
import CourseRegistration.POJO.Currentsemester;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class CourseController implements Initializable {
    public Button addButton;
    public Button delButton;
    public Label logout;
    @FXML
    private Label curSemLabel;
    @FXML
    private TextField searchBar;
    @FXML
    TableView<Course> table;
    @FXML
    TableColumn<Course, String> col_id;
    @FXML
    TableColumn<Course, String> col_courseName;
    @FXML
    TableColumn<Course, Integer> col_credits;
    @FXML
    TableColumn<Course, String> col_headTeacher;
    @FXML
    TableColumn<Course, String> col_room;
    @FXML
    TableColumn<Course, String> col_day;
    @FXML
    TableColumn<Course, String> col_period;
    @FXML
    TableColumn<Course, Integer> col_maxSlot;
    ObservableList<Course> list = FXCollections.observableArrayList();
    FilteredList<Course> filterList = new FilteredList<>(list);
    private Currentsemester curSem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        curSem = CurrentSemesterDAO.getCurrentSemester();
        curSemLabel.setText(curSem.getId() + "/" + curSem.getYear() + "-" + (curSem.getYear() + 1));
        List<Course> courseList = CourseDAO.getCourseListBySemester(curSem.getId(), curSem.getYear());
        list.addAll(courseList);
        col_id.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getSubjectBySubjectId().getId()));
        col_courseName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getSubjectBySubjectId().getName()));
        col_credits.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getSubjectBySubjectId().getCredits()).asObject());
        col_headTeacher.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getTeacherName()));
        col_room.setCellValueFactory(new PropertyValueFactory<>("room"));
        col_day.setCellValueFactory(new PropertyValueFactory<>("day"));
        col_period.setCellValueFactory(new PropertyValueFactory<>("period"));
        col_maxSlot.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getMaxSlot()).asObject());
        table.setItems(list);
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
        App.changeScene("TeacherDashboard");
    }

    @FXML
    public void logoutClicked() throws IOException, InterruptedException {
        Thread.sleep(300);
        App.changeScene("Login");
    }

    @FXML
    public void onReturn() throws IOException, InterruptedException {
        Thread.sleep(300);
        App.changeScene("TeacherDashboard");
    }

    @FXML
    public void onAdd() throws IOException {
        table.setItems(list);
        Dialog<Course> dialog = newDialog();
        Optional<Course> result = dialog.showAndWait();
        if (result.isPresent()) {
            CourseDAO.addCourse(result.get());
            table.getItems().add(result.get());
        }
    }

    @FXML
    public void onRemove() {
        table.setItems(list);
        if (table.getSelectionModel().getSelectedItem() != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Delete");
            confirm.setContentText("Bạn có chắc chắn muốn xóa học phần này không?");
            confirm.setHeaderText(null);
            Optional<ButtonType> option = confirm.showAndWait();
            if (option.isPresent())
                if (option.get() == ButtonType.OK) {
                    Course selectedItem = table.getSelectionModel().getSelectedItem();
                    if (selectedItem.getRegisterSlot() > 0) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setContentText("Không thể xóa học phần đã có sinh viên đăng ký!");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                    }
                    else if (CourseDAO.removeCourseByID(selectedItem.getId()))
                        table.getItems().remove(selectedItem);
                }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Vui lòng chọn học phần cần xóa!!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    @FXML
    public void onSearch() {
        String data = searchBar.getText().toLowerCase();
        filterList.setPredicate(
                course -> {
                    if (data.isEmpty()) return true;
                    if (course.getTeacherName().toLowerCase().contains(data)) return true;
                    if (course.getDay().toLowerCase().contains(data)) return true;
                    if (course.getPeriod().toLowerCase().contains(data)) return true;
                    if (course.getRoom().toLowerCase().contains(data)) return true;
                    if (course.getSubjectBySubjectId().getId().toLowerCase().contains(data)) return true;
                    return (course.getSubjectBySubjectId().getName().toLowerCase()).contains(data);
                }
        );
        table.setItems(filterList);
    }

    public Dialog<Course> newDialog() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/Controller/CourseDialog.fxml"));
        DialogPane content = fxmlLoader.load();
        Dialog<Course> dialog = new Dialog<>();
        dialog.setTitle("Thông tin học phần");
        dialog.setDialogPane(content);
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Objects.requireNonNull(this.getClass().getResource("/assets/img/SchoolLogo.png")).toString()));
        CourseDialogController cdc = fxmlLoader.getController();
        cdc.setSemesterId(curSem.getId());
        cdc.setYear(curSem.getYear());
        Button btn = (Button) dialog.getDialogPane().lookupButton(ButtonType.APPLY);
        btn.addEventFilter(ActionEvent.ACTION, event -> {
            if (cdc.getSubjectName() == null || cdc.getRoom().equals("") || cdc.getHeadTeacher().equals("") || cdc.getMaxSlot() == null) {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Warning");
                warning.setContentText("Vui lòng nhập hết thông tin!");
                warning.setHeaderText(null);
                warning.showAndWait();
                event.consume();
            }
            List<Course> list = CourseDAO.getCourseListBySemester(curSem.getId(), curSem.getYear());
            for (Course item : list)
                if (cdc.getRoom().equals(item.getRoom()) && cdc.getDay().equals(item.getDay()) && cdc.getPeriod().equals(item.getPeriod())) {
                    Alert warning = new Alert(Alert.AlertType.WARNING);
                    warning.setTitle("Warning");
                    warning.setContentText("Có học phần trùng giờ và trùng ngày học phòng này!!");
                    warning.setHeaderText(null);
                    warning.showAndWait();
                    event.consume();
                }
        });
        dialog.setResultConverter(button -> {
            if (button == ButtonType.APPLY)
                return new Course(cdc.getSemesterId(), cdc.getYear(), cdc.getHeadTeacher(), cdc.getSubjectName(), cdc.getRoom(), cdc.getDay(), cdc.getPeriod(), cdc.getMaxSlot());
            return null;
        });
        return dialog;
    }
    @FXML
    public void onCourseDetail(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            Course item = table.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/Controller/CourseDetail.fxml"));
            Parent root = fxmlLoader.load();
            CourseDetailController controller = fxmlLoader.getController();
            controller.setCurrentCourse(item);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
}
