package br.com.bruno.meumetro.rest.interfaces;

import java.util.List;

import br.com.bruno.meumetro.models.Line;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;

/**
 * Created by Bruno on 21/04/2017.
 */

public interface IStatusLineService {

    @Headers("Content-Type: application/json; charset=UTF-8")
    @PUT("status/update")
    Call<Line> updateStatusLine(@Header("security_key") String authorization, @Body Line line);

    @Headers("Content-Type: application/json; charset=UTF-8")
    @GET("status/official")
    Call<List<Line>> getLinesStatusLinesOfficial(@Header("security_key") String authorization);

    @Headers("Content-Type: application/json; charset=UTF-8")
    @GET("status/by_user")
    Call<List<Line>> getLinesStatusLinesByUser(@Header("security_key") String authorization);
}
