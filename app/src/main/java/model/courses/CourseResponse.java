package model.courses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class CourseResponse implements Serializable {
    @SerializedName("success")
    private boolean success;

    @SerializedName("response")
    private CoursesInfo coursesInfo;

    public CourseResponse(boolean success, CoursesInfo coursesInfo) {
        this.success = success;
        this.coursesInfo = coursesInfo;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public CoursesInfo getCoursesInfo() {
        return coursesInfo;
    }

    public void setCoursesInfo(CoursesInfo coursesInfo) {
        this.coursesInfo = coursesInfo;
    }
}
