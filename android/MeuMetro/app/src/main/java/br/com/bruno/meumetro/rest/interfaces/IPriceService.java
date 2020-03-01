package br.com.bruno.meumetro.rest.interfaces;

import br.com.bruno.meumetro.models.Price;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface IPriceService {

    @Headers("Content-Type: application/json; charset=UTF-8")
    @GET("price")
    Call<Price> getPrice(@Header("security_key") String authorization);

}
