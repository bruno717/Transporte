package br.com.bruno.meumetro.rest;

//import com.crashlytics.android.Crashlytics;

import java.io.IOException;
import java.util.List;

import br.com.bruno.meumetro.interfaces.IServiceResponse;
import br.com.bruno.meumetro.models.Line;
import br.com.bruno.meumetro.rest.interfaces.IStatusLineService;
import br.com.bruno.meumetro.rest.managers.RestManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Bruno on 21/04/2017.
 */

public class StatusLineService {

    public void updateStatusLine(Line line, final IServiceResponse<Line> callback) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(RestManager.BASE_URL)
                .client(RestManager.clientTimeout())
                .build();

        IStatusLineService service = retrofit.create(IStatusLineService.class);
        Call<Line> call = service.updateStatusLine(RestManager.basicAuthenticate(), line);

        call.enqueue(new Callback<Line>() {
            @Override
            public void onResponse(Call<Line> call, Response<Line> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError();
                }
            }

            @Override
            public void onFailure(Call<Line> call, Throwable t) {
                callback.onError();
            }
        });
    }

    public void getLinesStatusLinesOfficial(final IServiceResponse<List<Line>> callback) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(RestManager.BASE_URL)
                .client(RestManager.clientTimeout())
                .build();

        IStatusLineService service = retrofit.create(IStatusLineService.class);
        Call<List<Line>> call = service.getLinesStatusLinesOfficial(RestManager.basicAuthenticate());

        call.enqueue(new Callback<List<Line>>() {
            @Override
            public void onResponse(Call<List<Line>> call, Response<List<Line>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError();
                }
            }

            @Override
            public void onFailure(Call<List<Line>> call, Throwable t) {
                callback.onError();
            }
        });
    }

    public void getLinesStatusLinesByUser(final IServiceResponse<List<Line>> callback) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(RestManager.BASE_URL)
                .client(RestManager.clientTimeout())
                .build();

        IStatusLineService service = retrofit.create(IStatusLineService.class);
        Call<List<Line>> call = service.getLinesStatusLinesByUser(RestManager.basicAuthenticate());

        call.enqueue(new Callback<List<Line>>() {
            @Override
            public void onResponse(Call<List<Line>> call, Response<List<Line>> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError();
                }
            }

            @Override
            public void onFailure(Call<List<Line>> call, Throwable t) {
                callback.onError();
            }
        });
    }

    public List<Line> getLinesStatusLinesOfficialSync() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(RestManager.BASE_URL)
                .client(RestManager.clientTimeout())
                .build();

        IStatusLineService service = retrofit.create(IStatusLineService.class);
        Call<List<Line>> call = service.getLinesStatusLinesOfficial(RestManager.basicAuthenticate());

        try {
            Response<List<Line>> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
        } catch (IOException e) {
//            Crashlytics.logException(e);
            e.printStackTrace();
        }

        return null;
    }

    public List<Line> getLinesStatusLinesByUserSync() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(RestManager.BASE_URL)
                .client(RestManager.clientTimeout())
                .build();

        IStatusLineService service = retrofit.create(IStatusLineService.class);
        Call<List<Line>> call = service.getLinesStatusLinesByUser(RestManager.basicAuthenticate());

        try {
            Response<List<Line>> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
