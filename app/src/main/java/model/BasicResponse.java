package model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BasicResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("message")
    @Expose
    private String message;


    public BasicResponse() {
    }

    /**
     * @param success
     */
    public BasicResponse(Boolean success, String message) {
        super();
        this.success = success;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "BasicResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }


}