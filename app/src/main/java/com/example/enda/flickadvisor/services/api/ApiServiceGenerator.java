package com.example.enda.flickadvisor.services.api;

import com.example.enda.flickadvisor.models.UserTbl;
import com.example.enda.flickadvisor.util.UserSerializer;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import io.realm.RealmObject;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by enda on 18/02/16.
 */

// https://futurestud.io/blog/android-basic-authentication-with-retrofit
public class ApiServiceGenerator {

//    private static final String API_BASE_URL = "http://10.0.2.2:9000/";
    private static final String API_BASE_URL = "http://10.0.3.2:9000/";
//    private static final String API_BASE_URL = "http://192.168.0.21:9000/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Gson gson = new GsonBuilder()

            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
//            .registerTypeAdapter(Movie.class, new MovieSerializer())
//            .registerTypeAdapter(MovieReview.class, new MovieReviewSerializer())
//            .registerTypeAdapter(Genre.class, new GenreSerializer())
            .registerTypeAdapter(UserTbl.class, new UserSerializer())
//            .registerTypeAdapter(Series.class, new SeriesSerializer())
//            .registerTypeAdapter(SeriesReview.class, new SeriesReviewSerializer())
//            .registerTypeAdapter(UserMovie.class, new UserMovieSerializer())
//            .registerTypeAdapter(UserSeries.class, new UserSeriesSerializer())
            .create();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson));

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, final String authToken) {
        if (authToken != null) {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder builder = original.newBuilder()
                            .header("Authorization", authToken)
                            .method(original.method(), original.body());

                    Request request = builder.build();
                    return chain.proceed(request);
                }
            });
        }

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}
