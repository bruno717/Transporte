package br.com.bruno.meumetro.application;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;
import com.crashlytics.android.Crashlytics;

import br.com.bruno.meumetro.database.MigrationMyDataBase;
import br.com.bruno.meumetro.managers.SharedPreferenceManager;
import br.com.bruno.meumetro.models.Device;
import br.com.bruno.meumetro.rest.DeviceService;
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

        CONTEXT_GLOBAL = getApplicationContext();

        Fabric.with(this, new Crashlytics());

        Utils.init(this);

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(MigrationMyDataBase.VERSION)
                .migration(new MigrationMyDataBase())
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        verifyIfHasTokenNotification();
    }

    private void verifyIfHasTokenNotification() {
        Device device = SharedPreferenceManager.getDeviceToken();
        if (device != null) {
            new DeviceService().saveTokenDevice(device, true);
        }
    }
}
