package retrofit;

import retrofit.service.AccountService;
import retrofit.service.CourseService;
import retrofit.service.ManagerService;

public class APIUtils {
    private APIUtils() {

    }

    public static CourseService getCourseService() {
        return RetrofitClient.getClient().create(CourseService.class);
    }

    public static AccountService getAccountService() {
        return RetrofitClient.getClient().create(AccountService.class);
    }

    public static ManagerService getManagerService(){
        return RetrofitClient.getClient().create(ManagerService.class);
    }
}
