package retrofit;

import retrofit.service.CourseService;

public class APIUtils {
    private APIUtils() {

    }

    public static CourseService getCourseService() {
        return RetrofitClient.getClient().create(CourseService.class);
    }
}
