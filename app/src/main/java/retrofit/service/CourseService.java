package retrofit.service;

import model.courses.CourseResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CourseService {
    @GET("/courses")
    Call<CourseResponse> listCourses();

    @GET("/courses")
    Call<CourseResponse> listSearchCourses(@Query("q") String query);

}
