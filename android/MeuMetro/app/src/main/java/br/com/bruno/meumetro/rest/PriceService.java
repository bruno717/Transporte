package br.com.bruno.meumetro.rest;

import br.com.bruno.meumetro.interfaces.IServiceResponse;
import br.com.bruno.meumetro.models.Price;
import br.com.bruno.meumetro.rest.interfaces.IPriceService;
import br.com.bruno.meumetro.rest.managers.RestManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class PriceService {

    public void loadPrice(final IServiceResponse<Price> callback) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(RestManager.BASE_URL)
                .client(RestManager.clientTimeout())
                .build();

        IPriceService service = retrofit.create(IPriceService.class);

        Call<Price> call = service.getPrice(RestManager.basicAuthenticate());

        call.enqueue(new Callback<Price>() {
            @Override
            public void onResponse(Call<Price> call, Response<Price> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError();
                }
            }

            @Override
            public void onFailure(Call<Price> call, Throwable t) {
                callback.onError();
            }
        });

    }


}
