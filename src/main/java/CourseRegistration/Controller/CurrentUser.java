package CourseRegistration.Controller;

import CourseRegistration.POJO.Student;
import CourseRegistration.POJO.Teacher;

public class CurrentUser {
    private final static CurrentUser instance = new CurrentUser();
    private Teacher currentTeacher = new Teacher();
    private Student currentStudent = new Student();

    public static CurrentUser getInstance() {
        return instance;
    }

    public Teacher getCurrentTeacher() {
        return currentTeacher;
    }

    public void setCurrentTeacher(Teacher teacher) {
        currentTeacher = teacher;
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student student) {
        currentStudent = student;
    }
}
