package br.com.bruno.meumetro.rest.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface IAppVersionService {

    @Headers("Content-Type: application/json")
    @GET("app/version")
    Call<Void> verifyVersionApp(@Header("security_key") String authorization, @Query("appVersion") String appVersion);
}
