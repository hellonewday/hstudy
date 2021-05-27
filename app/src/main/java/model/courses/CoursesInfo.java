package model.courses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CoursesInfo implements Serializable {
    @SerializedName("counts")
    private int counts;

    @SerializedName("data")
    private List<Course> courses;

    public CoursesInfo(int counts, List<Course> courses) {
        this.counts = counts;
        this.courses = courses;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
