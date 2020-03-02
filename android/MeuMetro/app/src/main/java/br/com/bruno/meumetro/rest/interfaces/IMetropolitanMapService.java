package br.com.bruno.meumetro.rest.interfaces;

import br.com.bruno.meumetro.models.MetropolitanMap;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface IMetropolitanMapService {

    @Headers("Content-Type: application/json; charset=UTF-8")
    @GET("image-map")
    Call<MetropolitanMap> getMetropolitanMap(@Header("security_key") String authorization, @Query("modificationDateClient") Long modificationDateMap);

}
