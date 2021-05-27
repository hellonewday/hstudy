package retrofit.service;

import java.util.Date;

import model.accounts.Account;
import model.accounts.LoginResponse;
import model.accounts.Student;
import model.accounts.RegisterResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AccountService {
    @GET("/students")
    Call<RegisterResponse> getStudent();

    @POST("/students/login")
    Call<LoginResponse> login(@Body Account account);

    @POST("/students")
    Call<Student> register(
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email,
            @Field("dob") Date dob
    );
}
