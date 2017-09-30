package br.com.bruno.meumetro.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import br.com.bruno.meumetro.application.MeuMetroApplication;
import br.com.bruno.meumetro.models.Device;
import br.com.bruno.meumetro.services.MeuMetroFirebaseInstanceIdService;

/**
 * Created by Bruno on 03/08/2017.
 */

public class SharedPreferenceManager {

    public static void saveDeviceToken(Context context, Device device) {

        context = context != null ? context : MeuMetroApplication.CONTEXT_GLOBAL;
        if (context != null) {
            try {
                String jsonDevice = new ObjectMapper().writeValueAsString(device);
                SharedPreferences.Editor editor = context.getSharedPreferences(context.getPackageName(), 0).edit();
                editor.putString(MeuMetroFirebaseInstanceIdService.KEY_PREFERENCE_DEVICE, jsonDevice);
                editor.apply();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteDeviceToken(Context context) {
        context = context != null ? context : MeuMetroApplication.CONTEXT_GLOBAL;
        if (context != null) {
            SharedPreferences.Editor editor = context.getSharedPreferences(context.getPackageName(), 0).edit();
            editor.remove(MeuMetroFirebaseInstanceIdService.KEY_PREFERENCE_DEVICE).apply();
        }
    }

    public static Device getDeviceToken(Context context) {
        context = context != null ? context : MeuMetroApplication.CONTEXT_GLOBAL;
        String json = "";
        if (context != null) {
            SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), 0);
            json = preferences.getString(MeuMetroFirebaseInstanceIdService.KEY_PREFERENCE_DEVICE, "");
        }
        if (json.length() > 0) {
            try {
                return new ObjectMapper().readValue(json, Device.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
