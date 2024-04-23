package CourseRegistration.POJO;

import java.io.Serializable;

public class CurrentsemesterPK implements Serializable {
    private int id;
    private int year;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

        CurrentsemesterPK that = (CurrentsemesterPK) o;

        if (id != that.id) return false;
        return year == that.year;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + year;
        return result;
    }
}
