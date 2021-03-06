package br.com.bruno.meumetro;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.ads.AdView;

import java.io.IOException;

import br.com.bruno.meumetro.fragments.StationsOfTheLineFragment;
import br.com.bruno.meumetro.models.Line;
import br.com.bruno.meumetro.utils.AdViewUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bruno on 04/09/2016.
 */
public class LinesInformationActivity extends AppCompatActivity {

    @BindView(R.id.meu_metro_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.adViewBanner)
    AdView adViewBanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lines_information);
        ButterKnife.bind(this);

        setupBanner();
        setupToolbar();
        setupFragment();
    }

    private void setupToolbar() {
        mToolbar.setNavigationIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.md_nav_back));
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupFragment() {
        Line line = null;
        try {
            line = new ObjectMapper().readValue(getIntent().getStringExtra(LinesInformationActivity.class.getName()), Line.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.meu_metro_container_fragment, StationsOfTheLineFragment.newInstance(line))
                .commit();
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    private void setupBanner() {
        AdViewUtils.requestAd(adViewBanner);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
