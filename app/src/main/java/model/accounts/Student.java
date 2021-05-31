package model.accounts;


//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class Student {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("avatarUrl")
    @Expose
    private String avatarUrl;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;

    /**
     * No args constructor for use in serialization
     *
     */
    public Student() {
    }

    /**
     *
     * @param password
     * @param avatarUrl
     * @param dob
     * @param email
     * @param username
     */
    public Student(String email, String avatarUrl, String dob, String username, String password) {
        super();
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.dob = dob;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}