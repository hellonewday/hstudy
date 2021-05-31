package retrofit.service;

import model.courses.CourseResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CourseService {
    @GET("/courses")
    Call<CourseResponse> listCourses();

    @GET("/courses/{q}")
    Call<CourseResponse> listSearchCourses(@Path("q") String query);

}
