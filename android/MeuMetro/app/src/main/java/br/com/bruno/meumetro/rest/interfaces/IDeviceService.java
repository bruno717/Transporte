package br.com.bruno.meumetro.rest.interfaces;

import br.com.bruno.meumetro.models.Device;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Bruno on 21/05/2017.
 */

public interface IDeviceService {

    @Headers("Content-Type: application/json")
    @POST("/device")
    Call<Void> saveTokenDevice(@Header("security_key") String authorization, @Body Device device);
}
