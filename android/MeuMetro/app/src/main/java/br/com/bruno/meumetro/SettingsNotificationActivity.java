package br.com.bruno.meumetro;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import br.com.bruno.meumetro.adapters.SettingsAdapter;
import br.com.bruno.meumetro.database.RealmDbHelper;
import br.com.bruno.meumetro.enums.SettingsListType;
import br.com.bruno.meumetro.managers.LinearLayoutManagerEnabledScroll;
import br.com.bruno.meumetro.models.settings.DaySetting;
import br.com.bruno.meumetro.models.settings.HourSetting;
import br.com.bruno.meumetro.models.settings.LineSetting;
import br.com.bruno.meumetro.models.settings.Setting;
import br.com.bruno.meumetro.utils.ArrayUtils;
import br.com.bruno.meumetro.utils.DrawableUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmList;

/**
 * Created by Bruno on 30/04/2017.
 */

public class SettingsNotificationActivity extends AppCompatActivity implements SettingsAdapter.ISettingsAdapter {

    @Bind(R.id.meu_metro_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.activity_config_notification_switch_official)
    SwitchCompat mSwitchOfficial;
    @Bind(R.id.activity_config_notification_switch_by_user)
    SwitchCompat mSwitchByUser;
    @Bind(R.id.activity_config_notification_recycler_view_days)
    RecyclerView mRecyclerViewDays;
    @Bind(R.id.activity_config_notification_recycler_view_hours)
    RecyclerView mRecyclerViewHours;
    @Bind(R.id.activity_config_notification_recycler_view_lines)
    RecyclerView mRecyclerViewLines;
    @Bind(R.id.activity_config_notification_button_lines_days_notification)
    ImageButton mImageButtonDays;
    @Bind(R.id.activity_config_notification_button_lines_hours_notification)
    ImageButton mImageButtonHours;
    @Bind(R.id.activity_config_notification_button_lines_notification)
    ImageButton mImageButtonLines;
    @Bind(R.id.activity_config_notification_relative_layout_days)
    RelativeLayout mRelativeLayoutDays;
    @Bind(R.id.activity_config_notification_relative_layout_hours)
    RelativeLayout mRelativeLayoutHours;
    @Bind(R.id.activity_config_notification_relative_layout_lines)
    RelativeLayout mRelativeLayoutLines;

    public static final int TIME_ANIMATION = 250;

    private Integer[] mIndexLinesNotification = new Integer[]{};
    private Integer[] mIndexDaysNotification = new Integer[]{};
    private Integer[] mIndexHoursNotification = new Integer[]{};
    private CharSequence[] mListLines = new CharSequence[]{};
    private CharSequence[] mListDays = new CharSequence[]{};
    private CharSequence[] mListHours = new CharSequence[]{};
    private Setting mSetting;
    private MaterialDialog mProgressDialog;
    private SettingsAdapter mAdapterDays;
    private SettingsAdapter mAdapterHours;
    private SettingsAdapter mAdapterLines;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_notification_new);
        ButterKnife.bind(this);

        setupToolbar();
        mSwitchOfficial.setOnCheckedChangeListener(onCheckedChangedSwitchOfficial());
        mSwitchByUser.setOnCheckedChangeListener(onCheckedChangedSwitchByUser());
        setupRecyclerViews();
        populateFields();

        mImageButtonDays.setImageDrawable(DrawableUtils.changeColorDrawable(this, R.mipmap.ic_add_black_24dp, R.color.primary));
        mImageButtonHours.setImageDrawable(DrawableUtils.changeColorDrawable(this, R.mipmap.ic_add_black_24dp, R.color.primary));
        mImageButtonLines.setImageDrawable(DrawableUtils.changeColorDrawable(this, R.mipmap.ic_add_black_24dp, R.color.primary));
    }

    // UTILS
    private void setupToolbar() {
        mToolbar.setTitle(R.string.activity_settings_notification_toolbar_title);
        mToolbar.setNavigationIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.md_nav_back));
        mToolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.white));
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void populateFields() {
        Setting setting = RealmDbHelper.findFirst(Setting.class);

        if (setting == null) {
            ((View) mRecyclerViewDays.getParent().getParent()).setAlpha(0);
            ((View) mRecyclerViewHours.getParent().getParent()).setAlpha(0);
            ((View) mRecyclerViewLines.getParent().getParent()).setAlpha(0);
        } else {
            mSwitchOfficial.setChecked(setting.getIsNotificationOfficial() != null && setting.getIsNotificationOfficial());
            mSwitchByUser.setChecked(setting.getIsNotificationByUser() != null && setting.getIsNotificationByUser());
            ((View) mRecyclerViewDays.getParent().getParent()).setAlpha(setting.getIsNotificationOfficial() || setting.getIsNotificationByUser() ? 1 : 0);
            ((View) mRecyclerViewHours.getParent().getParent()).setAlpha(setting.getIsNotificationOfficial() || setting.getIsNotificationByUser() ? 1 : 0);
            ((View) mRecyclerViewLines.getParent().getParent()).setAlpha(setting.getIsNotificationOfficial() || setting.getIsNotificationByUser() ? 1 : 0);

            mIndexDaysNotification = new Integer[setting.getDays().size()];
            mListDays = new CharSequence[setting.getDays().size()];
            for (int i = 0; i < setting.getDays().size(); i++) {
                mListDays[i] = setting.getDays().get(i).getDay();
                mIndexDaysNotification[i] = setting.getDays().get(i).getPositionInList();
            }

            mIndexHoursNotification = new Integer[setting.getHours().size()];
            mListHours = new CharSequence[setting.getHours().size()];
            for (int i = 0; i < setting.getHours().size(); i++) {
                mListHours[i] = setting.getHours().get(i).getHour();
                mIndexHoursNotification[i] = setting.getHours().get(i).getPositionInList();
            }

            mIndexLinesNotification = new Integer[setting.getLines().size()];
            mListLines = new CharSequence[setting.getLines().size()];
            for (int i = 0; i < setting.getLines().size(); i++) {
                mListLines[i] = setting.getLines().get(i).getLine();
                mIndexLinesNotification[i] = setting.getLines().get(i).getPositionInList();
            }

            mAdapterDays = new SettingsAdapter(setting.getDaysString(), SettingsListType.DAYS, this);
            mRecyclerViewDays.setAdapter(mAdapterDays);
            mAdapterHours = new SettingsAdapter(setting.getHoursString(), SettingsListType.HOURS, this);
            mRecyclerViewHours.setAdapter(mAdapterHours);
            mAdapterLines = new SettingsAdapter(setting.getLinesString(), SettingsListType.LINES, this);
            mRecyclerViewLines.setAdapter(mAdapterLines);
        }
    }

    private void populateObject() {
        if (mSetting != null) {
            RealmList<DaySetting> daySettings = new RealmList<>();
            RealmList<HourSetting> hourSettings = new RealmList<>();
            RealmList<LineSetting> lineSettings = new RealmList<>();
            for (CharSequence sequenceDay : mListDays) {
                DaySetting daySetting = new DaySetting();
                daySetting.setDay(sequenceDay.toString());
                daySettings.add(daySetting);
            }
            for (int i = 0; i < mIndexDaysNotification.length; i++) {
                daySettings.get(i).setPositionInList(mIndexDaysNotification[i]);
            }
            for (CharSequence sequenceHours : mListHours) {
                HourSetting hourSetting = new HourSetting();
                hourSetting.setHour(sequenceHours.toString());
                hourSettings.add(hourSetting);
            }
            for (int i = 0; i < mIndexHoursNotification.length; i++) {
                hourSettings.get(i).setPositionInList(mIndexHoursNotification[i]);
            }
            for (CharSequence sequenceLines : mListLines) {
                LineSetting lineSetting = new LineSetting();
                lineSetting.setLine(sequenceLines.toString());
                lineSettings.add(lineSetting);
            }
            for (int i = 0; i < mIndexLinesNotification.length; i++) {
                lineSettings.get(i).setPositionInList(mIndexLinesNotification[i]);
            }
            mSetting.setDays(daySettings);
            mSetting.setHours(hourSettings);
            mSetting.setLines(lineSettings);
            if (mSetting.getIsNotificationOfficial() == null)
                mSetting.setIsNotificationOfficial(false);
            if (mSetting.getIsNotificationByUser() == null)
                mSetting.setIsNotificationByUser(false);
        } else {
            mSetting = new Setting();
            mSetting.setIsNotificationOfficial(false);
            mSetting.setIsNotificationByUser(false);
        }

    }

    private boolean verifyFields() {
        boolean isNotEmpty = true;

        if (mSetting == null) {
            mSetting = new Setting();
        }
        if (mSetting.getIsNotificationByUser() == null)
            mSetting.setIsNotificationByUser(false);
        if (mSetting.getIsNotificationOfficial() == null)
            mSetting.setIsNotificationOfficial(false);

        if (!mSetting.getIsNotificationOfficial() && !mSetting.getIsNotificationByUser()) {
            mListDays = new CharSequence[0];
            mListHours = new CharSequence[0];
            mListLines = new CharSequence[0];
            mIndexDaysNotification = new Integer[0];
            mIndexHoursNotification = new Integer[0];
            mIndexLinesNotification = new Integer[0];
            return true;
        }

        if (mAdapterDays == null || mAdapterDays.getItemCount() == 0) {
            YoYo.with(Techniques.Shake).playOn(mRelativeLayoutDays);
            isNotEmpty = false;
        }

        if (mAdapterHours == null || mAdapterHours.getItemCount() == 0) {
            YoYo.with(Techniques.Shake).playOn(mRelativeLayoutHours);
            isNotEmpty = false;
        }

        if (mAdapterLines == null || mAdapterLines.getItemCount() == 0) {
            YoYo.with(Techniques.Shake).playOn(mRelativeLayoutLines);
            isNotEmpty = false;
        }

        return isNotEmpty;
    }

    private void showProgressDialog() {
        mProgressDialog = new MaterialDialog.Builder(this)
                .title(R.string.meu_metro_progress_dialog_title_wait)
                .content(R.string.activity_settings_notification_progress_dialog_saving_settings)
                .progress(true, 0)
                .cancelable(false)
                .show();
    }

    private void showOrHideFields(boolean switchOfficial, boolean switchByUser) {
        if (!switchOfficial && !switchByUser) {
            YoYo.with(Techniques.FadeOutUp).duration(200).withListener(animatorListener()).playOn((View) mRecyclerViewDays.getParent().getParent());
            YoYo.with(Techniques.FadeOutUp).duration(200).withListener(animatorListener()).playOn((View) mRecyclerViewHours.getParent().getParent());
            YoYo.with(Techniques.FadeOutUp).duration(200).withListener(animatorListener()).playOn((View) mRecyclerViewLines.getParent().getParent());
        } else {
            if (((View) mRecyclerViewDays.getParent().getParent()).getAlpha() == 0) {
                YoYo.with(Techniques.FadeInDown).duration(200).withListener(animatorListener()).playOn((View) mRecyclerViewDays.getParent().getParent());
                YoYo.with(Techniques.FadeInDown).duration(200).withListener(animatorListener()).playOn((View) mRecyclerViewHours.getParent().getParent());
                YoYo.with(Techniques.FadeInDown).duration(200).withListener(animatorListener()).playOn((View) mRecyclerViewLines.getParent().getParent());
            }
        }
    }

    private LinearLayoutManagerEnabledScroll getLinearManager() {
        LinearLayoutManagerEnabledScroll manager = new LinearLayoutManagerEnabledScroll(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setScrollEnabled(false);
        return manager;
    }

    private void setupRecyclerViews() {
        mRecyclerViewDays.setLayoutManager(getLinearManager());
        mRecyclerViewHours.setLayoutManager(getLinearManager());
        mRecyclerViewLines.setLayoutManager(getLinearManager());
        mRecyclerViewDays.setItemAnimator(null);
        mRecyclerViewHours.setItemAnimator(null);
        mRecyclerViewLines.setItemAnimator(null);
    }

    // ONCLICK
    @OnClick(R.id.activity_config_notification_button_lines_days_notification)
    public void onClickShowModalDays() {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.activity_settings_notification_hint_lines_days_utilization).replace("*", ""))
                .items(getResources().getStringArray(R.array.activity_settings_notification_list_days))
                .itemsCallbackMultiChoice(mIndexDaysNotification, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        mIndexDaysNotification = which;
                        mListDays = text;

                        mAdapterDays = new SettingsAdapter(mListDays, SettingsListType.DAYS, SettingsNotificationActivity.this);
                        mRecyclerViewDays.setAdapter(mAdapterDays);
                        return false;
                    }
                })
                .positiveText(R.string.meu_metro_message_positive)
                .negativeText(R.string.meu_metro_message_negative)
                .show();
    }

    @OnClick(R.id.activity_config_notification_button_lines_hours_notification)
    public void onClickShowModalHours() {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.activity_settings_notification_hint_lines_hours_utilization).replace("*", ""))
                .items(getResources().getStringArray(R.array.activity_settings_notification_list_hours))
                .itemsCallbackMultiChoice(mIndexHoursNotification, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        mIndexHoursNotification = which;
                        mListHours = text;

                        mAdapterHours = new SettingsAdapter(mListHours, SettingsListType.HOURS, SettingsNotificationActivity.this);
                        mRecyclerViewHours.setAdapter(mAdapterHours);
                        return false;
                    }
                })
                .positiveText(R.string.meu_metro_message_positive)
                .negativeText(R.string.meu_metro_message_negative)
                .show();
    }

    @OnClick(R.id.activity_config_notification_button_lines_notification)
    public void onClickShowModalLines() {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.activity_settings_notification_hint_lines).replace("*", ""))
                .items(getResources().getStringArray(R.array.activity_edit_status_list_lines))
                .itemsCallbackMultiChoice(mIndexLinesNotification, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        mIndexLinesNotification = which;
                        mListLines = text;

                        mAdapterLines = new SettingsAdapter(mListLines, SettingsListType.LINES, SettingsNotificationActivity.this);
                        mRecyclerViewLines.setAdapter(mAdapterLines);
                        return false;
                    }
                })
                .positiveText(R.string.meu_metro_message_positive)
                .negativeText(R.string.meu_metro_message_negative)
                .show();
    }

    private CompoundButton.OnCheckedChangeListener onCheckedChangedSwitchOfficial() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean on) {
                if (mSetting == null)
                    mSetting = new Setting();

                mSetting.setIsNotificationOfficial(on);
                showOrHideFields(on, mSwitchByUser.isChecked());
            }
        };
    }

    private CompoundButton.OnCheckedChangeListener onCheckedChangedSwitchByUser() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean on) {
                if (mSetting == null)
                    mSetting = new Setting();

                mSetting.setIsNotificationByUser(on);
                showOrHideFields(mSwitchOfficial.isChecked(), on);
            }
        };
    }

    // LISTENERS
    private Animator.AnimatorListener animatorListener() {
        return new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mSwitchByUser.setClickable(false);
                mSwitchOfficial.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mSwitchByUser.setClickable(true);
                mSwitchOfficial.setClickable(true);
                mImageButtonDays.setClickable(((View) mRecyclerViewDays.getParent().getParent()).getAlpha() > 0);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        };
    }

    // MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_done:
                if (verifyFields()) {
                    showProgressDialog();
                    populateObject();
                    RealmDbHelper.deleteAll(Setting.class);
                    RealmDbHelper.save(mSetting);
                    mProgressDialog.dismiss();
                    Toast.makeText(this, R.string.activity_settings_notification_message_settings_saved, Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // LISTENERS
    @Override
    public void onClickRemove(int position, SettingsListType listType) {
        switch (listType) {
            case DAYS:
                mListDays = ArrayUtils.removeElements(mListDays, mListDays[position]);
                mIndexDaysNotification = ArrayUtils.removeElements(mIndexDaysNotification, mIndexDaysNotification[position]);
                break;

            case HOURS:
                mListHours = ArrayUtils.removeElements(mListHours, mListHours[position]);
                mIndexHoursNotification = ArrayUtils.removeElements(mIndexHoursNotification, mIndexHoursNotification[position]);
                break;

            case LINES:
                mListLines = ArrayUtils.removeElements(mListLines, mListLines[position]);
                mIndexLinesNotification = ArrayUtils.removeElements(mIndexLinesNotification, mIndexLinesNotification[position]);
                break;
        }
    }
}
