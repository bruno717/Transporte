package br.com.bruno.meumetro;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.Locale;

import br.com.bruno.meumetro.adapters.TabsViewPagerAdapter;
import br.com.bruno.meumetro.database.RealmDbHelper;
import br.com.bruno.meumetro.fragments.StatusLineByUserFragment;
import br.com.bruno.meumetro.fragments.StatusLineOfficialFragment;
import br.com.bruno.meumetro.managers.SharedPreferenceManager;
import br.com.bruno.meumetro.models.settings.Setting;
import br.com.bruno.meumetro.utils.DrawableUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Drawer.OnDrawerItemClickListener, Drawer.OnDrawerListener, TabLayout.OnTabSelectedListener {

    public static final String MAIN_ACTIVITY_INTENT_KEY_IS_TAB_USER = "MAIN_ACTIVITY_INTENT_KEY_IS_TAB_USER";

    @BindView(R.id.meu_metro_main_view)
    CoordinatorLayout mViewMain;
    @BindView(R.id.meu_metro_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.meu_metro_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.meu_metro_view_pager)
    ViewPager mViewPager;

    private Drawer mDrawer;
    private TabsViewPagerAdapter mViewPagerAdapter;
    private int mPositionTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupToolbar();
        setupNavigationDrawer(savedInstanceState);
        setupTabLayout();
        setupChangeRealmToPreference();
    }

    private void setupChangeRealmToPreference() {
        Setting setting = RealmDbHelper.findFirst(Setting.class);
        if (setting != null)
            SharedPreferenceManager.saveSettings(setting);
    }

    private void setupToolbar() {
        mToolbar.setTitle(R.string.activity_main_toolbar_title);
        setSupportActionBar(mToolbar);
    }

    private void setupNavigationDrawer(Bundle savedInstanceState) {

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withHeaderDivider(false)
                .withDisplayBelowStatusBar(true)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.START)
                .withSavedInstance(savedInstanceState)
                .withHeader(getHeaderNavigationDrawer())
                .withOnDrawerItemClickListener(this)
                .withOnDrawerListener(this)
                .withSelectedItem(-1)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withIdentifier(R.id.navigation_drawer_item_transport_map)
                                .withName(R.string.navigation_drawer_transport_map)
                                .withIcon(DrawableUtils.changeColorDrawable(this, R.mipmap.ic_map_white_24dp, R.color.primary)),
                        new PrimaryDrawerItem()
                                .withIdentifier(R.id.navigation_drawer_item_sms_denunciation)
                                .withName(R.string.navigation_drawer_sms_denunciation)
                                .withIcon(DrawableUtils.changeColorDrawable(this, R.mipmap.ic_sms_white_24dp, R.color.primary)),
                        new PrimaryDrawerItem()
                                .withIdentifier(R.id.navigation_drawer_item_settings)
                                .withName(R.string.navigation_drawer_settings)
                                .withIcon(DrawableUtils.changeColorDrawable(this, R.mipmap.ic_settings_white_24dp, R.color.primary))
                )
                .build();
    }

    private View getHeaderNavigationDrawer() {
        View header = getLayoutInflater().inflate(R.layout.navigation_drawer_header, mViewMain, false);
        TextView textViewIntegerPrice = (TextView) header.findViewById(R.id.navigation_drawer_header_text_view_integer_price);
        TextView textViewHalfPrice = (TextView) header.findViewById(R.id.navigation_drawer_header_text_view_half_price);
        TextView textViewIntegrationPrice = (TextView) header.findViewById(R.id.navigation_drawer_header_text_view_integration_price);

        textViewIntegerPrice.setText(String.format(Locale.getDefault(), getString(R.string.navigation_drawer_text_view_integer_price), getString(R.string.meu_metro_integer_price)));
        textViewHalfPrice.setText(String.format(Locale.getDefault(), getString(R.string.navigation_drawer_text_view_half_price), getString(R.string.meu_metro_half_price)));
        textViewIntegrationPrice.setText(String.format(Locale.getDefault(), getString(R.string.navigation_drawer_text_view_integration_price), getString(R.string.meu_metro_integration_price)));

        return header;
    }

    private void setupTabLayout() {
        mPositionTab = getIntent().getBooleanExtra(MAIN_ACTIVITY_INTENT_KEY_IS_TAB_USER, false) ? 1 : 0;
        mViewPagerAdapter = new TabsViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFragment(new StatusLineOfficialFragment(), getString(R.string.activity_main_tab_layout_official).toUpperCase());
        mViewPagerAdapter.addFragment(new StatusLineByUserFragment(), getString(R.string.activity_main_tab_layout_user).toUpperCase());
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(this);
        if (mTabLayout.getTabAt(mPositionTab) != null)
            mTabLayout.getTabAt(mPositionTab).select();
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

        mDrawer.deselect();
        switch (Integer.parseInt(String.valueOf(drawerItem.getIdentifier()))) {
            case R.id.navigation_drawer_item_transport_map:
                startActivity(new Intent(this, TransportMapActivity.class));
                break;

            case R.id.navigation_drawer_item_sms_denunciation:
                startActivity(new Intent(this, SmsDenunciationActivity.class));
                break;

            case R.id.navigation_drawer_item_settings:
                startActivity(new Intent(this, SettingsNotificationActivity.class));
                break;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        if (mDrawer != null && mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
        } else {
            if (mPositionTab > 0) {
                if (mTabLayout.getTabAt(0) != null)
                    mTabLayout.getTabAt(0).select();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mPositionTab = tab.getPosition();
        if (mPositionTab == 1) {
            ((StatusLineByUserFragment) mViewPagerAdapter.getItem(tab.getPosition())).setupRecyclerView();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
