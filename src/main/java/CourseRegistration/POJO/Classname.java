package CourseRegistration.POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Classname {
    private String id;
    private Integer total;
    private Integer maleCount;
    private Integer femaleCount;

    public Classname(){}
    public Classname(String id) {
        this.id = id;
        this.total = 0;
        this.maleCount = 0;
        this.femaleCount = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getMaleCount() {
        return maleCount;
    }

    public void setMaleCount(Integer maleCount) {
        this.maleCount = maleCount;
    }

    public Integer getFemaleCount() {
        return femaleCount;
    }

    public void setFemaleCount(Integer femaleCount) {
        this.femaleCount = femaleCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Classname classname = (Classname) o;

        if (!Objects.equals(id, classname.id)) return false;
        if (!Objects.equals(total, classname.total)) return false;
        if (!Objects.equals(maleCount, classname.maleCount)) return false;
        return Objects.equals(femaleCount, classname.femaleCount);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (maleCount != null ? maleCount.hashCode() : 0);
        result = 31 * result + (femaleCount != null ? femaleCount.hashCode() : 0);
        return result;
    }
}
