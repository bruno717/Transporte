package br.com.bruno.meumetro.rest.interfaces;

import java.util.List;

import br.com.bruno.meumetro.models.Place;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Bruno on 03/12/2017.
 */

public interface IPlaceService {

    @Headers("Content-Type: application/json; charset=UTF-8")
    @GET("places")
    Call<List<Place>> getPlacesNearbyStation(@Query("latitude") Double latitude, @Query("longitude") Double longitude, @Header("security_key") String authorization);
}
