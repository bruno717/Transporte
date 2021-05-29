package br.com.bruno.meumetro;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdView;

import br.com.bruno.meumetro.fragments.SmsDenunciationFragment;
import br.com.bruno.meumetro.utils.AdViewUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bruno on 13/05/2017.
 */

public class SmsDenunciationActivity extends AppCompatActivity {

    @BindView(R.id.meu_metro_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.adViewBanner)
    AdView adViewBanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_denunciation);
        ButterKnife.bind(this);

        setupToolbar();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.meu_metro_container_fragment, new SmsDenunciationFragment())
                .commit();

        setupBanner();
    }

    private void setupToolbar() {
        mToolbar.setNavigationIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.md_nav_back));
        setSupportActionBar(mToolbar);
    }

    private void setupBanner() {
        AdViewUtils.requestAd(adViewBanner);
    }
}
