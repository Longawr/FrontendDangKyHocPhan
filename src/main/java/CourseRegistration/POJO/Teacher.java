package CourseRegistration.POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Teacher {
    private String id;
    private String firstName;
    private String lastName;
    private String sex;
    private Account accountByAccount;

    public Teacher() {
    }

    public Teacher(String id, String firstName, String lastName, String sex, Account acc) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.accountByAccount = acc;
        this.accountByAccount.setRole("GV");
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

        Teacher teacher = (Teacher) o;

        if (!Objects.equals(id, teacher.id)) return false;
        if (!Objects.equals(firstName, teacher.firstName)) return false;
        if (!Objects.equals(lastName, teacher.lastName)) return false;
        return Objects.equals(sex, teacher.sex);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        return result;
    }

    public Account getAccountByAccount() {
        return accountByAccount;
    }

    public void setAccountByAccount(Account accountByAccount) {
        this.accountByAccount = accountByAccount;
    }


}
