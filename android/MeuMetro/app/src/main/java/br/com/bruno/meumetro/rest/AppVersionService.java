package br.com.bruno.meumetro.rest;

import android.content.Context;
import android.content.pm.PackageInfo;

import br.com.bruno.meumetro.application.MeuMetroApplication;
import br.com.bruno.meumetro.interfaces.IServiceResponse;
import br.com.bruno.meumetro.rest.managers.VersionChecker;

public class AppVersionService {

    public void verifyVersionApp(final IServiceResponse<Void> callback) {

        try {
            Context context = MeuMetroApplication.CONTEXT_GLOBAL;
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            VersionChecker versionChecker = new VersionChecker();
            String latestVersion = versionChecker.execute().get();
            if (!pInfo.versionName.equalsIgnoreCase(latestVersion)) {
                callback.onSuccess(null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            callback.onError();
        }
    }

}
