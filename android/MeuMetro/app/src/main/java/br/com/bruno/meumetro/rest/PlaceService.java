package br.com.bruno.meumetro.rest;

import java.util.List;

import br.com.bruno.meumetro.interfaces.IServiceResponse;
import br.com.bruno.meumetro.models.Place;
import br.com.bruno.meumetro.rest.interfaces.IPlaceService;
import br.com.bruno.meumetro.rest.managers.RestManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Bruno on 03/12/2017.
 */

public class PlaceService {

    public void getPlacesNearbyStation(double latitude, double longitude, final IServiceResponse<List<Place>> callback) {

        Retrofit retrofit = RestManager.getRetrofitConfigured();
        IPlaceService service = retrofit.create(IPlaceService.class);
        Call<List<Place>> call = service.getPlacesNearbyStation(latitude, longitude, RestManager.basicAuthenticate());

        call.enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError();
                }
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                callback.onError();
            }
        });

    }
}
