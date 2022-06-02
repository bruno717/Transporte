package br.com.bruno.meumetro.rest.managers;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import br.com.bruno.meumetro.BuildConfig;
import br.com.bruno.meumetro.application.MeuMetroApplication;

public class VersionChecker extends AsyncTask<String, String, String> {

    String newVersion;

    @Override
    protected String doInBackground(String... params) {
        String url = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "&hl=en";
        try {
            Document document = Jsoup.connect(url)
                    .timeout(30000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get();
//                    .select("div.hAyfc:nth-child(4) > span:nth-child(2) > div:nth-child(1) > span:nth-child(1)")
//                    .first();
//                    .ownText();
            Context context = MeuMetroApplication.CONTEXT_GLOBAL;
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            newVersion = document.body().data().contains(pInfo.versionName) ? pInfo.versionName : "";

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return newVersion;
    }
}
