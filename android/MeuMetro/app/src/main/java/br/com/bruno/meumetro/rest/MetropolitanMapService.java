package br.com.bruno.meumetro.rest;

import java.net.HttpURLConnection;

import br.com.bruno.meumetro.interfaces.IServiceResponse;
import br.com.bruno.meumetro.models.MetropolitanMap;
import br.com.bruno.meumetro.rest.interfaces.IMetropolitanMapService;
import br.com.bruno.meumetro.rest.managers.RestManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MetropolitanMapService {

    public void getMetropolitanMap(Long modificationDate, final IServiceResponse<MetropolitanMap> callback) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(RestManager.BASE_URL)
                .client(RestManager.clientTimeout())
                .build();

        IMetropolitanMapService service = retrofit.create(IMetropolitanMapService.class);

        Call<MetropolitanMap> call = service.getMetropolitanMap(RestManager.basicAuthenticate(), modificationDate);

        call.enqueue(new Callback<MetropolitanMap>() {
            @Override
            public void onResponse(Call<MetropolitanMap> call, Response<MetropolitanMap> response) {
                if (response.code() != HttpURLConnection.HTTP_NO_CONTENT) {
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onError();
                    }
                }
            }

            @Override
            public void onFailure(Call<MetropolitanMap> call, Throwable t) {
                callback.onError();
            }
        });

    }

}
