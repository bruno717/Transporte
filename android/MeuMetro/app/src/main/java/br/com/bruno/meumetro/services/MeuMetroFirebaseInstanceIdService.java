package br.com.bruno.meumetro.services;

import android.provider.Settings;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import br.com.bruno.meumetro.managers.SharedPreferenceManager;
import br.com.bruno.meumetro.models.Device;
import br.com.bruno.meumetro.rest.DeviceService;
import br.com.bruno.meumetro.utils.ConnectionUtils;

/**
 * Created by Bruno on 18/05/2017.
 */

public class MeuMetroFirebaseInstanceIdService extends FirebaseInstanceIdService {

    public static final String KEY_PREFERENCE_DEVICE = "KEY_PREFERENCE_DEVICE";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();

        Log.i("MESSAGING", "Token: " + token);

        String idDevice = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        Device device = new Device();
        device.setIdDevice(idDevice);
        device.setTokenDevice(token);
        if (ConnectionUtils.isConnected(getApplicationContext())) {
            new DeviceService().saveTokenDevice(device, false);
        } else {
            SharedPreferenceManager.saveDeviceToken(getApplicationContext(), device);
        }
    }
}
