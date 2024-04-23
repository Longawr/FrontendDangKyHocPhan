package CourseRegistration.POJO;

import java.io.Serializable;

public class CourseRegistrationPK implements Serializable {
    private int id;
    private int semesterId;
    private int year;

    public CourseRegistrationPK() {
    }

    public CourseRegistrationPK(int id, int semesterId, int year) {
        this.id = id;
        this.semesterId = semesterId;
        this.year = year;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseRegistrationPK that = (CourseRegistrationPK) o;

        if (id != that.id) return false;
        if (semesterId != that.semesterId) return false;
        return year == that.year;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + semesterId;
        result = 31 * result + year;
        return result;
    }
}
