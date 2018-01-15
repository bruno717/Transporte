package br.com.bruno.meumetro;

import android.location.Address;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import br.com.bruno.meumetro.adapters.TabsViewPagerAdapter;
import br.com.bruno.meumetro.fragments.PlacesNearbyListFragment;
import br.com.bruno.meumetro.fragments.PlacesNearbyMapFragment;
import br.com.bruno.meumetro.managers.GeoLocationManager;
import br.com.bruno.meumetro.models.Station;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bruno on 03/12/2017.
 */

public class PlacesNearbyActivity extends AppCompatActivity {

    public static final String PLACES_NEARBY_ACTIVITY_ADDRESS_INTENT_KEY = "PLACES_NEARBY_ACTIVITY_ADDRESS_INTENT_KEY";
    public static final String PLACES_NEARBY_ACTIVITY_COLOR_INTENT_KEY = "PLACES_NEARBY_ACTIVITY_COLOR_INTENT_KEY";

    @BindView(R.id.meu_metro_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.meu_metro_tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.meu_metro_view_pager)
    ViewPager mViewPager;

    private TabsViewPagerAdapter mViewPagerAdapter;
    private Station mStation;
    private int mColorLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_nearby);
        ButterKnife.bind(this);

        setupToolbar();
        setupTabLayout();
    }

    private void setupToolbar() {
        if (getIntent() != null) {
            try {
                mStation = new ObjectMapper().readValue(getIntent().getStringExtra(PLACES_NEARBY_ACTIVITY_ADDRESS_INTENT_KEY), Station.class);
                mColorLine = getIntent().getIntExtra(PLACES_NEARBY_ACTIVITY_COLOR_INTENT_KEY, 0);
                mToolbar.setNavigationIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.md_nav_back));
                mToolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
                mToolbar.setSubtitleTextColor(ContextCompat.getColor(this, android.R.color.white));
                mToolbar.setTitle(R.string.activity_places_nearby_toolbar_title);
                mToolbar.setSubtitle(mStation.getName());
                if (mColorLine > 0)
                    mToolbar.setBackgroundResource(mColorLine);
                setSupportActionBar(mToolbar);
                if (getSupportActionBar() != null)
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } catch (IOException e) {
                Crashlytics.logException(e);
                e.printStackTrace();
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
                getWindow().setStatusBarColor(ContextCompat.getColor(this, mColorLine));
            }
        }
    }

    private void setupTabLayout() {
        Address address = GeoLocationManager.getInfoAddress(this, mStation.getLocation());
        mViewPagerAdapter = new TabsViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(PlacesNearbyMapFragment.newInstance(address), getString(R.string.activity_places_nearby_tab_layout_tab_map).toUpperCase());
        mViewPagerAdapter.addFragment(PlacesNearbyListFragment.newInstance(address), getString(R.string.activity_places_nearby_tab_layout_tab_list).toUpperCase());
        mTabLayout.setBackgroundResource(mColorLine);
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
