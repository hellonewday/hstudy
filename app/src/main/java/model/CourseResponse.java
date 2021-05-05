package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("result")
    private List<Course> courseList;

    public CourseResponse(boolean success, List<Course> courseList) {
        this.success = success;
        this.courseList = courseList;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }
}
