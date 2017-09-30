package br.com.bruno.meumetro.rest;

import java.io.IOException;

import br.com.bruno.meumetro.managers.SharedPreferenceManager;
import br.com.bruno.meumetro.models.Device;
import br.com.bruno.meumetro.rest.interfaces.IDeviceService;
import br.com.bruno.meumetro.rest.managers.RestManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Bruno on 21/05/2017.
 */

public class DeviceService {

    public void saveTokenDevice(final Device device, boolean isAsync) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(RestManager.BASE_URL)
                .client(RestManager.clientTimeout())
                .build();

        IDeviceService service = retrofit.create(IDeviceService.class);
        Call<Void> call = service.saveTokenDevice(RestManager.basicAuthenticate(), device);

        if (isAsync) {
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    SharedPreferenceManager.deleteDeviceToken(null);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    if (SharedPreferenceManager.getDeviceToken(null) == null) {
                        SharedPreferenceManager.saveDeviceToken(null, device);
                    }
                }
            });
        } else {
            try {
                call.execute();
                SharedPreferenceManager.deleteDeviceToken(null);
            } catch (IOException e) {
                e.printStackTrace();
                if (SharedPreferenceManager.getDeviceToken(null) == null) {
                    SharedPreferenceManager.saveDeviceToken(null, device);
                }
            }
        }
    }
}
