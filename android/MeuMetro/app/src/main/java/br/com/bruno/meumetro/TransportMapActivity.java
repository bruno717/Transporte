package br.com.bruno.meumetro;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.bruno.meumetro.interfaces.IServiceResponse;
import br.com.bruno.meumetro.managers.AnalyticsManager;
import br.com.bruno.meumetro.managers.SharedPreferenceManager;
import br.com.bruno.meumetro.models.MetropolitanMap;
import br.com.bruno.meumetro.rest.MetropolitanMapService;
import br.com.bruno.meumetro.utils.BitmapUtils;
import br.com.bruno.meumetro.widgets.TouchImageView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bruno on 14/05/2017.
 */

public class TransportMapActivity extends AppCompatActivity {

    @BindView(R.id.meu_metro_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_transport_map_web_view)
    WebView mWebView;
    @BindView(R.id.imageView)
    TouchImageView imageView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_map);
        ButterKnife.bind(this);

        setupToolbar();
        setupMetropolitanMapImage();
        loadMetropolitanMap();

        setupWebView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        AnalyticsManager.generateLogScreenOpen(getString(R.string.activity_transport_map));
    }

    private void setupToolbar() {
        mToolbar.setNavigationIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.md_nav_back));
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.activity_transport_map_toolbar_title);
    }

    private void loadMetropolitanMap() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
            Date date = dateFormat.parse("2020-02-22T21:24:00");
            new MetropolitanMapService().getMetropolitanMap(date.getTime(), new IServiceResponse<MetropolitanMap>() {
                @Override
                public void onSuccess(MetropolitanMap metropolitanMap) {
                    SharedPreferenceManager.saveMetropolitanMap(metropolitanMap);
                    setupMetropolitanMapImage();
                }

                @Override
                public void onError() {
                    progressBar.setVisibility(View.GONE);
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setupMetropolitanMapImage() {
        MetropolitanMap metropolitanMap = SharedPreferenceManager.getMetropolitanMap();
        if (metropolitanMap != null) {
            progressBar.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            Bitmap bitmap = BitmapUtils.base64ToBitmap(metropolitanMap.getMap());
            imageView.setImageBitmap(bitmap);
        }
    }

    private void setupWebView() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mWebView.getSettings().setUseWideViewPort(true);
            mWebView.getSettings().setBuiltInZoomControls(true);
            mWebView.getSettings().setDisplayZoomControls(false);
            mWebView.getSettings().setLoadWithOverviewMode(true);
            mWebView.loadUrl("file:///android_asset/transport_map.html");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}