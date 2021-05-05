package model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Course implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("courseName")
    private String courseName;

    @SerializedName("level")
    private String level;

    @SerializedName("description")
    private String description;

    @SerializedName("courseImage")
    private String courseImage;

    @SerializedName("author")
    private String author;

    public Course() {

    }

    public Course(int id, String courseName, String level, String description, String courseImage, String author) {
        this.id = id;
        this.courseName = courseName;
        this.level = level;
        this.description = description;
        this.courseImage = courseImage;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
