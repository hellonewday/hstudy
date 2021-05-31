package retrofit.service;

import model.BasicResponse;
import model.manager.VideoResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ManagerService {

    @GET("/manager/attendance/{courseId}")
    Call<VideoResponse> getVideoForStudent(
            @Path("courseId") int id,
            @Query("user") int user
    );

    @POST("/manager/enroll/{courseId}")
    Call<BasicResponse> enrollCourseForStudent(
            @Path("courseId") int id,
            @Query("user") int user
    );

    @POST("/manager/watch/{videoId}")
    Call<BasicResponse> watchVideoForStudent(
            @Path("videoId") int id,
            @Query("user") int user
    );




}
