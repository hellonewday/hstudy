package model.accounts;

//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class StudentResponse {

    @SerializedName("success")
    @Expose
    private Boolean success; // true false
    @SerializedName("data")
    @Expose
    private Student data; // object

    /**
     * No args constructor for use in serialization
     *
     */
    public StudentResponse() {
    }

    /**
     *
     * @param data
     * @param success
     */
    public StudentResponse(Boolean success, Student data) {
        super();
        this.success = success;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Student getData() {
        return data;
    }

    public void setData(Student data) {
        this.data = data;
    }

}