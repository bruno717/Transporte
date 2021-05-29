package br.com.bruno.meumetro.utils;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.ArrayList;
import java.util.List;

import br.com.bruno.meumetro.BuildConfig;

public class AdViewUtils {

    public static void requestAd(AdView adView) {
        RequestConfiguration.Builder config = new RequestConfiguration.Builder();
        if (BuildConfig.DEBUG) {
            List<String> testDeviceIds = new ArrayList<>();
            testDeviceIds.add("603B3916C858A875BD001CEFF6936C8A");
            config.setTestDeviceIds(testDeviceIds);
        }
        MobileAds.setRequestConfiguration(config.build());
        AdRequest build = new AdRequest.Builder().build();
        adView.loadAd(build);
    }

}
