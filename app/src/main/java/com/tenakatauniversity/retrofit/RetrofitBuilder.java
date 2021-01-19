package com.tenakatauniversity.retrofit;

import com.tenakatauniversity.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class RetrofitBuilder {

//    public static final String BASE_URL = "http://192.168.42.100/tenakatauniversity/public/api/"; //usb ethernet
    public static final String BASE_URL = "http://www.tenakatauniversity.mkeja.com/tenakatauniversity/public/api/";

    private final static OkHttpClient client = buildClient();

    public final static Retrofit retrofit = buildRetrofit();

    private static OkHttpClient buildClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()

                .addInterceptor(chain -> {

                    Request request = chain.request();

                    Request.Builder builder1 = request.newBuilder()

                            .addHeader("Accept", "application/json")

                            .addHeader("Connection", "close");
//
                    request = builder1.build();

                    return chain.proceed(request);

                })

                .retryOnConnectionFailure(true)

                .connectTimeout(5, TimeUnit.MINUTES)

                .writeTimeout(5, TimeUnit.MINUTES)

                .readTimeout(5, TimeUnit.MINUTES);

        return builder.build();
    }

    private static Retrofit buildRetrofit() {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }


    public static <T> T createService(Class<T> service) {
        return retrofit.create(service);
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static ApiService getApiInterface () {
        return RetrofitBuilder.createService(ApiService.class);
    }

}
