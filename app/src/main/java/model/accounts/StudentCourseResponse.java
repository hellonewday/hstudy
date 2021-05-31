package model.accounts;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import model.courses.Course;

public class StudentCourseResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<Course> courses;

    public StudentCourseResponse(boolean success, List<Course> courses) {
        this.success = success;
        this.courses = courses;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCoursesInfo(List<Course> courses) {
        this.courses = courses;
    }
}
