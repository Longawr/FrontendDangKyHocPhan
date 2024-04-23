package CourseRegistration.POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Date;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Semester {
    private int id;
    private int year;
    private Date dateStart;
    private Date dateEnd;

    public Semester(int id, int year, Date dateStart, Date dateEnd) {
        this.id = id;
        this.year = year;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public Semester() {
    }

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

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Semester semester = (Semester) o;

        if (id != semester.getId()) return false;
        if (year != semester.getYear()) return false;
        if (!Objects.equals(dateStart, semester.dateStart)) return false;
        return Objects.equals(dateEnd, semester.dateEnd);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + year;
        result = 31 * result + (dateStart != null ? dateStart.hashCode() : 0);
        result = 31 * result + (dateEnd != null ? dateEnd.hashCode() : 0);
        return result;
    }
}
