package retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl("http://203.171.21.65:8088").addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }
}
