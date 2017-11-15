package br.com.bruno.meumetro;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_map);
        ButterKnife.bind(this);

        setupToolbar();
        setupWebView();
    }


    private void setupToolbar() {
        mToolbar.setNavigationIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.md_nav_back));
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.activity_transport_map_toolbar_title);
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