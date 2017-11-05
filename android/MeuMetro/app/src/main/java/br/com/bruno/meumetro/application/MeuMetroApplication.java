package br.com.bruno.meumetro.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import br.com.bruno.meumetro.database.MigrationMyDataBase;
import br.com.bruno.meumetro.managers.SharedPreferenceManager;
import br.com.bruno.meumetro.models.Device;
import br.com.bruno.meumetro.rest.DeviceService;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Bruno on 22/04/2017.
 */

public class MeuMetroApplication extends MultiDexApplication {

    public static Context CONTEXT_GLOBAL;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        CONTEXT_GLOBAL = getApplicationContext();

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(MigrationMyDataBase.VERSION)
                .migration(new MigrationMyDataBase())
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        verifyIfHasTokenNotification();
    }

    private void verifyIfHasTokenNotification() {
        Device device = SharedPreferenceManager.getDeviceToken(getApplicationContext());
        if (device != null) {
            new DeviceService().saveTokenDevice(device, true);
        }
    }
}
