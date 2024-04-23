
module CourseRegistration.Main {
    requires com.fasterxml.jackson.annotation;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires spring.web;
    requires spring.core;
    requires spring.boot;
    requires spring.boot.autoconfigure;

    exports CourseRegistration.Main;
    exports CourseRegistration.POJO;
    opens CourseRegistration.Main to javafx.fxml;
    opens CourseRegistration.Controller to javafx.fxml;
}
