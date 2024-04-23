package CourseRegistration.POJO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Student {
    private String id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String sex;
    private Classname classnameByClassName;
    private Account accountByAccount;
    @JsonIgnore
    private Collection<Courseattend> courseattendsById;

    public Student() {
    }

    public Student(String id, String firstName, String lastName, Date dateOfBirth, String sex, Classname className, Account acc) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.classnameByClassName = className;
        this.accountByAccount = acc;
        this.accountByAccount.setRole("SV");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (!Objects.equals(id, student.id)) return false;
        if (!Objects.equals(firstName, student.firstName)) return false;
        if (!Objects.equals(lastName, student.lastName)) return false;
        if (!Objects.equals(dateOfBirth, student.dateOfBirth)) return false;
        return Objects.equals(sex, student.sex);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        return result;
    }

    public Classname getClassnameByClassName() {
        return classnameByClassName;
    }

    public void setClassnameByClassName(Classname classnameByClassName) {
        this.classnameByClassName = classnameByClassName;
    }

    public Account getAccountByAccount() {
        return accountByAccount;
    }

    public void setAccountByAccount(Account accountByAccount) {
        this.accountByAccount = accountByAccount;
    }


    public Collection<Courseattend> getCourseattendsById() {
        return courseattendsById;
    }

    public void setCourseattendsById(Collection<Courseattend> courseattendsById) {
        this.courseattendsById = courseattendsById;
    }
}
