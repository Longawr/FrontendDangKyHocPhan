package CourseRegistration.POJO;

import java.io.Serializable;
import java.util.Objects;

public class CourseattendPK implements Serializable {
    private String studentId;
    private int courseId;

    public CourseattendPK() {}
    public CourseattendPK(String studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseattendPK that = (CourseattendPK) o;

        if (courseId != that.courseId) return false;
        return Objects.equals(studentId, that.studentId);
    }

    @Override
    public int hashCode() {
        int result = studentId != null ? studentId.hashCode() : 0;
        result = 31 * result + courseId;
        return result;
    }
}
