package CourseRegistration.Controller;

import CourseRegistration.DAO.CourseDAO;
import CourseRegistration.DAO.CourseRegistrationDAO;
import CourseRegistration.DAO.CurrentSemesterDAO;
import CourseRegistration.DAO.SemesterDAO;
import CourseRegistration.Main.App;
import CourseRegistration.POJO.Course;
import CourseRegistration.POJO.Currentsemester;
import CourseRegistration.POJO.Semester;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class TeacherSemesterController implements Initializable {
    public Label logout;
    public Button addButton;
    public Button delButton;
    @FXML
    private TextField searchBar;
    @FXML
    TableView<Semester> table;
    @FXML
    TableColumn<Semester, Integer> col_id;
    @FXML
    TableColumn<Semester, Integer> col_year;
    @FXML
    TableColumn<Semester, Date> col_dateStart;
    @FXML
    TableColumn<Semester, Date> col_dateEnd;
    @FXML
    Label currentSemesterLabel;
    ObservableList<Semester> list = FXCollections.observableArrayList();
    FilteredList<Semester> filterList = new FilteredList<>(list);
    private Currentsemester curSem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Semester> semesterList = SemesterDAO.getSemesterList();
        list.addAll(semesterList);
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_year.setCellValueFactory(new PropertyValueFactory<>("year"));
        col_dateStart.setCellValueFactory(new PropertyValueFactory<>("dateStart"));
        col_dateEnd.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        table.setItems(list);
        curSem = CurrentSemesterDAO.getCurrentSemester();
        currentSemesterLabel.setText(curSem.getId() + "/" + curSem.getYear() + "-" + (curSem.getYear() + 1));
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
    public void onSearch() {
        String data = searchBar.getText().toLowerCase();
        filterList.setPredicate(
                semester -> {
                    if (data.isEmpty()) return true;
                    if (String.valueOf(semester.getId()).contains(data)) return true;
                    if (String.valueOf(semester.getYear()).contains(data)) return true;
                    return (semester.getYear() + " - " + semester.getId()).contains(data);
                }
        );
        table.setItems(filterList);
    }

    @FXML
    public void onAdd() throws IOException {
        table.setItems(list);
        Dialog<Semester> dialog = newDialog();
        Optional<Semester> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (SemesterDAO.addSemester(result.get()))
                table.getItems().add(result.get());
        }
    }

    @FXML
    public void onRemove() {
        table.setItems(list);
        if (table.getSelectionModel().getSelectedItem() != null) {
            Semester selectedSem = table.getSelectionModel().getSelectedItem();
            if (selectedSem.getYear() == curSem.getYear() && selectedSem.getId() == curSem.getId()) {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Waring");
                warning.setContentText("Bạn không thể xóa học kì hiện tại!!");
                warning.setHeaderText(null);
                warning.showAndWait();
            }
            else {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Delete");
                confirm.setContentText("Bạn có chắc chắn muốn xóa học kỳ này không?");
                confirm.setHeaderText(null);
                Optional<ButtonType> option = confirm.showAndWait();
                if (option.isPresent())
                    if (option.get() == ButtonType.OK) {
                        List<Course> cList = CourseDAO.getCourseListBySemester(selectedSem.getId(), selectedSem.getYear());
                        if (cList.size() > 0) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Warning");
                            alert.setContentText("Vui lòng xóa các học phần có trong học kỳ này!!");
                            alert.setHeaderText(null);
                            alert.showAndWait();
                        }
                        else {
                            if (CourseRegistrationDAO.getCourseRegistrationBySemester(selectedSem.getId(), selectedSem.getYear()) != null) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setContentText("Vui lòng xóa các kỳ đăng ký học phần có trong học kỳ này!!");
                                alert.setHeaderText(null);
                                alert.showAndWait();
                            }
                            else {
                                if (SemesterDAO.removeSemesterByID(selectedSem.getYear(), selectedSem.getId()))
                                    table.getItems().remove(selectedSem);
                            }
                        }

                    }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Vui lòng chọn học kỳ cần xóa!!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    @FXML
    void setCurrent(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Semester sem = table.getSelectionModel().getSelectedItem();
            curSem = new Currentsemester(sem.getId(), sem.getYear());
            CurrentSemesterDAO.removeCurrentSemester();
            CurrentSemesterDAO.addCurrentSemester(curSem);
            currentSemesterLabel.setText(curSem.getId() + "/" + curSem.getYear() + "-" + (curSem.getYear() + 1));
        }
    }

    public Dialog<Semester> newDialog() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/Controller/SemesterDialog.fxml"));
        DialogPane content = fxmlLoader.load();
        Dialog<Semester> dialog = new Dialog<>();
        dialog.setTitle("Thông tin học kỳ");
        dialog.setDialogPane(content);
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Objects.requireNonNull(this.getClass().getResource("/assets/img/SchoolLogo.png")).toString()));
        SemesterDialogController sdc = fxmlLoader.getController();
        Button btn = (Button) dialog.getDialogPane().lookupButton(ButtonType.APPLY);
        btn.addEventFilter(ActionEvent.ACTION, event -> {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Warning");
            warning.setHeaderText(null);
            if (sdc.getDateStart() == null || sdc.getDateEnd() == null) {
                warning.setContentText("Vui lòng nhập hết thông tin!");
                warning.showAndWait();
                event.consume();
            }
            else if (sdc.getDateStart().toLocalDate().isAfter(sdc.getDateEnd().toLocalDate())) {
                warning.setContentText("Ngày kết thúc phải sau ngày bắt đầu!");
                warning.showAndWait();
                event.consume();
            }
            else {
                List<Semester> semesterList = SemesterDAO.getSemesterList();
                for (Semester sem : semesterList)
                    if (!sdc.getDateStart().toLocalDate().isAfter(sem.getDateEnd().toLocalDate())) {
                       if (!sdc.getDateEnd().toLocalDate().isBefore(sem.getDateStart().toLocalDate())) {
                           warning.setContentText("Thời gian của học kỳ không được trùng với thời gian của học kỳ khác!");
                           warning.showAndWait();
                           event.consume();
                           break;
                       }
                    }
            }
        });
        dialog.setResultConverter(button -> {
            if (button == ButtonType.APPLY)
                return new Semester(sdc.getId(), sdc.getYear(), sdc.getDateStart(), sdc.getDateEnd());
            return null;
        });
        return dialog;
    }
}
