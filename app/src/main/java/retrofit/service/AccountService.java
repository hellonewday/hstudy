package retrofit.service;

import java.util.Date;

import model.BasicResponse;
import model.accounts.Account;
import model.accounts.LoginResponse;
import model.accounts.RegisterResponse;
import model.accounts.StudentCourseResponse;
import model.accounts.StudentResponse;
import model.accounts.StudentVideoCounts;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AccountService {
    @GET("/students/{sid}")
    Call<StudentResponse> getStudent(@Path("sid") int sid);

    @GET("/students/{sid}/courses")
    Call<StudentCourseResponse> getStudentCourses(@Path("sid") int sid);

    @GET("/students/{sid}/videos")
    Call<StudentVideoCounts> countStudentVideos(@Path("sid") int sid);


    @POST("/students/login")
    Call<LoginResponse> login(@Body Account account);

    @POST("/students")
    @FormUrlEncoded
    Call<BasicResponse> register(
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email,
            @Field("dob") String dob
    );

    @PATCH("/students/{sid}")
    @FormUrlEncoded
    Call<BasicResponse> updateStudent(
            @Path("sid") int sid,
            @Field("username") String username,
            @Field("email") String email,
            @Field("dob") String dob
    );

    @PATCH("/students/{username}/forgot")
    @FormUrlEncoded
    Call<BasicResponse> forgotPassword(
            @Path("username") String username,
            @Field("password") String password
    );

    @PATCH("/students/{username}/change")
    @FormUrlEncoded
    Call<BasicResponse> changePassword(
            @Path("username") String username,
            @Field("old") String oldPassword,
            @Field("new") String newPassword
    );


}
