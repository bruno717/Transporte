package br.com.bruno.meumetro.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.blankj.utilcode.util.ActivityUtils;
import com.crashlytics.android.Crashlytics;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import br.com.bruno.meumetro.adapters.StatusLineOfficialAdapter;
import br.com.bruno.meumetro.application.MeuMetroApplication;
import br.com.bruno.meumetro.models.Device;
import br.com.bruno.meumetro.services.MeuMetroFirebaseInstanceIdService;

/**
 * Created by Bruno on 03/08/2017.
 */

public class SharedPreferenceManager {

    public static void saveDeviceToken(Device device) {

        Context context = ActivityUtils.getTopActivity();
        context = context != null ? context : MeuMetroApplication.CONTEXT_GLOBAL;
        try {
            String jsonDevice = new ObjectMapper().writeValueAsString(device);
            SharedPreferences.Editor editor = context.getSharedPreferences(context.getPackageName(), 0).edit();
            editor.putString(MeuMetroFirebaseInstanceIdService.KEY_PREFERENCE_DEVICE, jsonDevice);
            editor.apply();
        } catch (JsonProcessingException e) {
            Crashlytics.logException(e);
            e.printStackTrace();
        }
    }

    public static void deleteDeviceToken() {
        Context context = ActivityUtils.getTopActivity();
        context = context != null ? context : MeuMetroApplication.CONTEXT_GLOBAL;
        if (context != null) {
            SharedPreferences.Editor editor = context.getSharedPreferences(context.getPackageName(), 0).edit();
            editor.remove(MeuMetroFirebaseInstanceIdService.KEY_PREFERENCE_DEVICE).apply();
        }
    }

    public static Device getDeviceToken() {
        Context context = ActivityUtils.getTopActivity();
        context = context != null ? context : MeuMetroApplication.CONTEXT_GLOBAL;
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), 0);
        String json = preferences.getString(MeuMetroFirebaseInstanceIdService.KEY_PREFERENCE_DEVICE, "");

        if (json.length() > 0) {
            try {
                return new ObjectMapper().readValue(json, Device.class);
            } catch (IOException e) {
                Crashlytics.logException(e);
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void saveDateLastRefresh(int type) {
        Context context = ActivityUtils.getTopActivity();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM 'às' HH'h'mm", Locale.getDefault());
        SharedPreferences.Editor editor = context.getSharedPreferences(context.getPackageName(), 0).edit();
        editor.putString(type == LINE_OFFICIAL ? StatusLineOfficialAdapter.STATUS_LINE_OFFICIAL_ADAPTER_KEY_PREFERENCE_OFFICIAL : StatusLineOfficialAdapter.STATUS_LINE_OFFICIAL_ADAPTER_KEY_PREFERENCE_BY_USER, dateFormat.format(new Date()));
        editor.apply();
    }

    public static String getDateLastRefresh(int type) {
        Context context = ActivityUtils.getTopActivity();
        SharedPreferences preferences = context.getSharedPreferences(context.getPackageName(), 0);
        return preferences.getString(type == LINE_OFFICIAL ? StatusLineOfficialAdapter.STATUS_LINE_OFFICIAL_ADAPTER_KEY_PREFERENCE_OFFICIAL : StatusLineOfficialAdapter.STATUS_LINE_OFFICIAL_ADAPTER_KEY_PREFERENCE_BY_USER, "");
    }

    public static final int LINE_OFFICIAL = 0;
    public static final int LINE_BY_USER = 1;
}
