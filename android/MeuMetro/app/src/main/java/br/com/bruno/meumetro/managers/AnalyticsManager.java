package br.com.bruno.meumetro.managers;

import android.content.Context;
import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.google.firebase.analytics.FirebaseAnalytics;

import br.com.bruno.meumetro.enums.ConnectionType;
import br.com.bruno.meumetro.rest.managers.RestManager;

public class AnalyticsManager {

    private static final String SCREEN_OPEN_EVENT = "screen_open";

    public static void generateLogScreenOpen(String param) {
        if (RestManager.BASE_URL.equals(ConnectionType.PRODUCTION.getUrl())) {
            Context context = ActivityUtils.getTopActivity();
            FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(context);

            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, param);
            analytics.logEvent(SCREEN_OPEN_EVENT, bundle);
        }
    }
}
