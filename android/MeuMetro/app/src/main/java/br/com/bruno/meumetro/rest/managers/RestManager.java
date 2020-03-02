package br.com.bruno.meumetro.rest.managers;

import java.util.concurrent.TimeUnit;

import br.com.bruno.meumetro.enums.ConnectionType;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Bruno on 21/05/2017.
 */

public class RestManager {

    private static final int SECONDS_TIMEOUT = 40;

    public static final String BASE_URL = ConnectionType.HOMOLOGATION.getUrl();

    private static final String BASIC_AUTHENTICATE_USER_NAME = "50722f1edc4efb34a5adb1ebd7af0a9c"; // meumetro
    private static final String BASIC_AUTHENTICATE_PASSWORD = "e871f5a73c097ec22f27ebe917ed9f87"; // meumetrosecurity

    public static String basicAuthenticate() {
//        return "Basic " + Base64.encodeToString(String.format("%s:%s", BASIC_AUTHENTICATE_USER_NAME, BASIC_AUTHENTICATE_PASSWORD).getBytes(), Base64.NO_WRAP);
        return meumetroAuthenticate();

    }

    private static String meumetroAuthenticate() {
        return BASIC_AUTHENTICATE_PASSWORD;
    }

    public static OkHttpClient clientTimeout() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient()
                .newBuilder()
                .connectTimeout(SECONDS_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(SECONDS_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(SECONDS_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();
    }

    public static Retrofit getRetrofitConfigured() {
        return new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(RestManager.BASE_URL)
                .client(RestManager.clientTimeout())
                .build();
    }
}
