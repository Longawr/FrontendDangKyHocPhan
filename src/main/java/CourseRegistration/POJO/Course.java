package CourseRegistration.POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Objects;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Course {
    private int id;
    private int semesterId;
    private int year;
    private String room;
    private String day;
    private String period;
    private Integer maxSlot;
    private Subject subjectBySubjectId;
    private String teacherName;
    private Integer registerSlot;
    private final BooleanProperty checked;

    public Course() {
        this.checked = new SimpleBooleanProperty(false);
    }

    public Course(int semesterId, int year, String teacherName, Subject subject, String room, String day, String period, Integer maxSlot) {
        this.semesterId = semesterId;
        this.year = year;
        this.teacherName = teacherName;
        this.room = room;
        this.day = day;
        this.period = period;
        this.maxSlot = maxSlot;
        this.registerSlot = 0;
        this.subjectBySubjectId = subject;
        this.checked = new SimpleBooleanProperty(false);
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Integer getMaxSlot() {
        return maxSlot;
    }

    public void setMaxSlot(Integer maxSlot) {
        this.maxSlot = maxSlot;
    }

    public Integer getRegisterSlot() {
        return registerSlot;
    }

    public void setRegisterSlot(Integer registerSlot) {
        this.registerSlot = registerSlot;
    }

    public void setChecked(boolean checked) {
        this.checked.set(checked);
    }
    public BooleanProperty checkedProperty() {
        return checked;
    }
    public boolean isChecked() {
        return checked.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (id != course.id) return false;
        if (semesterId != course.semesterId) return false;
        if (year != course.year) return false;
        if (!Objects.equals(room, course.room)) return false;
        if (!Objects.equals(day, course.day)) return false;
        if (!Objects.equals(period, course.period)) return false;
        if (!Objects.equals(maxSlot, course.maxSlot)) return false;
        return Objects.equals(registerSlot, course.registerSlot);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + semesterId;
        result = 31 * result + year;
        result = 31 * result + (room != null ? room.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (period != null ? period.hashCode() : 0);
        result = 31 * result + (maxSlot != null ? maxSlot.hashCode() : 0);
        result = 31 * result + (maxSlot != null ? registerSlot.hashCode() : 0);
        return result;
    }

    public Subject getSubjectBySubjectId() {
        return subjectBySubjectId;
    }

    public void setSubjectBySubjectId(Subject subjectBySubjectId) {
        this.subjectBySubjectId = subjectBySubjectId;
    }

}
