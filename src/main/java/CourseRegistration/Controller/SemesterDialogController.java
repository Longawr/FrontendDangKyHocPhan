package CourseRegistration.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class SemesterDialogController implements Initializable {
    @FXML
    private Spinner<Integer> id;
    @FXML
    private Spinner<Integer> year;
    @FXML
    private DatePicker dateStart;
    @FXML
    private DatePicker dateEnd;

    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Integer> value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, 1);
        id.setValueFactory(value);
        value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1990, 2025, 2021);
        year.setValueFactory(value);
    }

    @FXML
    public int getId() {
        return id.getValue();
    }

    @FXML
    public int getYear() {
        return year.getValue();
    }

    @FXML
    public Date getDateStart() {
        if (dateStart.getValue() == null) return null;
        return Date.valueOf(dateStart.getValue());
    }

    @FXML
    public Date getDateEnd() {
        if (dateEnd.getValue() == null) return null;
        return Date.valueOf(dateEnd.getValue());
    }

}
