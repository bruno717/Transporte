package br.com.bruno.meumetro;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import br.com.bruno.meumetro.fragments.PlacesNearbyListFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bruno on 03/12/2017.
 */

public class PlacesNearbyActivity extends AppCompatActivity {

    @BindView(R.id.meu_metro_toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_nearby);
        ButterKnife.bind(this);

        setupToolbar();
        setupFragment();
    }

    private void setupToolbar() {
        mToolbar.setNavigationIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.md_nav_back));
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        mToolbar.setSubtitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        mToolbar.setTitle(R.string.activity_places_nearby_toolbar_title);
        mToolbar.setSubtitle("Patriarca");
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.meu_metro_container_fragment, PlacesNearbyListFragment.newInstance())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
