package br.com.bruno.meumetro.rest;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.net.HttpURLConnection;

import br.com.bruno.meumetro.application.MeuMetroApplication;
import br.com.bruno.meumetro.interfaces.IServiceResponse;
import br.com.bruno.meumetro.rest.interfaces.IAppVersionService;
import br.com.bruno.meumetro.rest.managers.RestManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AppVersionService {

    public void verifyVersionApp(final IServiceResponse<Void> callback) {

        Retrofit retrofit = RestManager.getRetrofitConfigured();
        IAppVersionService service = retrofit.create(IAppVersionService.class);

        try {
            Context context = MeuMetroApplication.CONTEXT_GLOBAL;
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            Call<Void> call = service.verifyVersionApp(RestManager.basicAuthenticate(), pInfo.versionName);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == HttpURLConnection.HTTP_BAD_REQUEST) {
                        callback.onSuccess(null);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    callback.onError();
                }
            });

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            callback.onError();
        }
    }
}
