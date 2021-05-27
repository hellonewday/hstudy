package model.accounts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("token")
    @Expose
    private String token;

    /**
     * No args constructor for use in serialization
     */
    public LoginResponse() {
    }

    /**
     * @param success
     * @param token
     */
    public LoginResponse(Boolean success, String token) {
        super();
        this.success = success;
        this.token = token;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
