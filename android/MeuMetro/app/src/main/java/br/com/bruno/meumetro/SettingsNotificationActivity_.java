//package br.com.bruno.meumetro;
//
//import android.animation.Animator;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.design.widget.TextInputLayout;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.SwitchCompat;
//import android.support.v7.widget.Toolbar;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.afollestad.materialdialogs.MaterialDialog;
//import com.daimajia.androidanimations.library.Techniques;
//import com.daimajia.androidanimations.library.YoYo;
//
//import br.com.bruno.meumetro.database.RealmDbHelper;
//import br.com.bruno.meumetro.models.settings.DaySetting;
//import br.com.bruno.meumetro.models.settings.HourSetting;
//import br.com.bruno.meumetro.models.settings.LineSetting;
//import br.com.bruno.meumetro.models.settings.Setting;
//import br.com.bruno.meumetro.services.managers.NotificationStatusManager;
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import io.realm.RealmList;
//
///**
// * Created by Bruno on 30/04/2017.
// */
//
//public class SettingsNotificationActivity_ extends AppCompatActivity implements View.OnFocusChangeListener {
//
//    @Bind(R.id.meu_metro_toolbar)
//    Toolbar mToolbar;
//    @Bind(R.id.activity_config_notification_switch_official)
//    SwitchCompat mSwitchOfficial;
//    @Bind(R.id.activity_config_notification_switch_by_user)
//    SwitchCompat mSwitchByUser;
//    @Bind(R.id.activity_config_notification_input_lines_days_notification)
//    TextInputLayout mInputLayoutDaysNotification;
//    @Bind(R.id.activity_config_notification_input_lines_hours_notification)
//    TextInputLayout mInputLayoutHoursNotification;
//    @Bind(R.id.activity_config_notification_input_lines_notification)
//    TextInputLayout mInputLayoutLinesNotification;
//    @Bind(R.id.activity_config_notification_button_lines_days_notification)
//    Button mButtonDaysNotification;
//    @Bind(R.id.activity_config_notification_button_lines_hours_notification)
//    Button mButtonHoursNotification;
//    @Bind(R.id.activity_config_notification_button_lines_notification)
//    Button mButtonLinesNotification;
//
//    private Integer[] mIndexLinesNotification = new Integer[]{};
//    private Integer[] mIndexDaysNotification = new Integer[]{};
//    private Integer[] mIndexHoursNotification = new Integer[]{};
//    private CharSequence[] mListLines = new CharSequence[]{};
//    private CharSequence[] mListDays = new CharSequence[]{};
//    private CharSequence[] mListHours = new CharSequence[]{};
//    private Setting mSetting;
//    private MaterialDialog mProgressDialog;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_settings_notification);
//        ButterKnife.bind(this);
//
//        setupToolbar();
//        setupWatcherListeners();
//        mSwitchOfficial.setOnCheckedChangeListener(onCheckedChangedSwitchOfficial());
//        mSwitchByUser.setOnCheckedChangeListener(onCheckedChangedSwitchByUser());
//        populateFields();
//    }
//
//
//    // UTILS
//    private void setupToolbar() {
//        mToolbar.setTitle(R.string.activity_settings_notification_toolbar_title);
//        mToolbar.setNavigationIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.md_nav_back));
//        mToolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.white));
//        setSupportActionBar(mToolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//    }
//
//    private void populateFields() {
//        Setting setting = RealmDbHelper.findFirst(Setting.class);
//
//        if (setting == null) {
//            ((View) mInputLayoutDaysNotification.getParent()).setAlpha(0);
//            ((View) mInputLayoutHoursNotification.getParent()).setAlpha(0);
//            ((View) mInputLayoutLinesNotification.getParent()).setAlpha(0);
//        } else {
//            mSwitchOfficial.setChecked(setting.getIsNotificationOfficial() != null && setting.getIsNotificationOfficial());
//            mSwitchByUser.setChecked(setting.getIsNotificationByUser() != null && setting.getIsNotificationByUser());
//            ((View) mInputLayoutDaysNotification.getParent()).setAlpha(setting.getIsNotificationOfficial() || setting.getIsNotificationByUser() ? 1 : 0);
//            ((View) mInputLayoutHoursNotification.getParent()).setAlpha(setting.getIsNotificationOfficial() || setting.getIsNotificationByUser() ? 1 : 0);
//            ((View) mInputLayoutLinesNotification.getParent()).setAlpha(setting.getIsNotificationOfficial() || setting.getIsNotificationByUser() ? 1 : 0);
//            String text = "";
//            mIndexDaysNotification = new Integer[setting.getDays().size()];
//            mListDays = new CharSequence[setting.getDays().size()];
//            for (int i = 0; i < setting.getDays().size(); i++) {
//                if (text.length() > 0) {
//                    text += ", " + setting.getDays().get(i).getDay();
//                } else {
//                    text = setting.getDays().get(i).getDay();
//                }
//                mListDays[i] = setting.getDays().get(i).getDay();
//                mIndexDaysNotification[i] = setting.getDays().get(i).getPositionInList();
//            }
//            mInputLayoutDaysNotification.getEditText().setText(text);
//            text = "";
//            mIndexHoursNotification = new Integer[setting.getHours().size()];
//            mListHours = new CharSequence[setting.getHours().size()];
//            for (int i = 0; i < setting.getHours().size(); i++) {
//                if (text.length() > 0) {
//                    text += ", " + setting.getHours().get(i).getHour();
//                } else {
//                    text = setting.getHours().get(i).getHour();
//                }
//                mListHours[i] = setting.getHours().get(i).getHour();
//                mIndexHoursNotification[i] = setting.getHours().get(i).getPositionInList();
//            }
//            mInputLayoutHoursNotification.getEditText().setText(text);
//
//            text = "";
//            mIndexLinesNotification = new Integer[setting.getLines().size()];
//            mListLines = new CharSequence[setting.getLines().size()];
//            for (int i = 0; i < setting.getLines().size(); i++) {
//                if (text.length() > 0) {
//                    text += ", " + setting.getLines().get(i).getLine();
//                } else {
//                    text = setting.getLines().get(i).getLine();
//                }
//                mListLines[i] = setting.getLines().get(i).getLine();
//                mIndexLinesNotification[i] = setting.getLines().get(i).getPositionInList();
//            }
//            mInputLayoutLinesNotification.getEditText().setText(text);
//
//
//        }
//    }
//
//    private void populateObject() {
//        if (mSetting != null) {
//            RealmList<DaySetting> daySettings = new RealmList<>();
//            RealmList<HourSetting> hourSettings = new RealmList<>();
//            RealmList<LineSetting> lineSettings = new RealmList<>();
//            for (CharSequence sequenceDay : mListDays) {
//                DaySetting daySetting = new DaySetting();
//                daySetting.setDay(sequenceDay.toString());
//                daySettings.add(daySetting);
//            }
//            for (int i = 0; i < mIndexDaysNotification.length; i++) {
//                daySettings.get(i).setPositionInList(mIndexDaysNotification[i]);
//            }
//            for (CharSequence sequenceHours : mListHours) {
//                HourSetting hourSetting = new HourSetting();
//                hourSetting.setHour(sequenceHours.toString());
//                hourSettings.add(hourSetting);
//            }
//            for (int i = 0; i < mIndexHoursNotification.length; i++) {
//                hourSettings.get(i).setPositionInList(mIndexHoursNotification[i]);
//            }
//            for (CharSequence sequenceLines : mListLines) {
//                LineSetting lineSetting = new LineSetting();
//                lineSetting.setLine(sequenceLines.toString());
//                lineSettings.add(lineSetting);
//            }
//            for (int i = 0; i < mIndexLinesNotification.length; i++) {
//                lineSettings.get(i).setPositionInList(mIndexLinesNotification[i]);
//            }
//            mSetting.setDays(daySettings);
//            mSetting.setHours(hourSettings);
//            mSetting.setLines(lineSettings);
//            if (mSetting.getIsNotificationOfficial() == null)
//                mSetting.setIsNotificationOfficial(false);
//            if (mSetting.getIsNotificationByUser() == null)
//                mSetting.setIsNotificationByUser(false);
//        } else {
//            mSetting = new Setting();
//            mSetting.setIsNotificationOfficial(false);
//            mSetting.setIsNotificationByUser(false);
//        }
//
//    }
//
//    private boolean verifyFields() {
//        boolean isNotEmpty = true;
//
//        if (mSetting == null) {
//            mSetting = new Setting();
//        }
//        if (mSetting.getIsNotificationByUser() == null)
//            mSetting.setIsNotificationByUser(false);
//        if (mSetting.getIsNotificationOfficial() == null)
//            mSetting.setIsNotificationOfficial(false);
//
//        if (!mSetting.getIsNotificationOfficial() && !mSetting.getIsNotificationByUser()) {
//            return true;
//        }
//
//        if (mInputLayoutLinesNotification.getEditText().getText().toString().trim().length() == 0) {
//            mInputLayoutLinesNotification.setErrorEnabled(true);
//            mInputLayoutLinesNotification.setError(getString(R.string.activity_settings_notification_input_lines_error_empty));
//            isNotEmpty = false;
//        }
//        if (mInputLayoutDaysNotification.getEditText().getText().toString().trim().length() == 0) {
//            mInputLayoutDaysNotification.setErrorEnabled(true);
//            mInputLayoutDaysNotification.setError(getString(R.string.activity_settings_notification_input_days_error_empty));
//            isNotEmpty = false;
//        }
//        if (mInputLayoutHoursNotification.getEditText().getText().toString().trim().length() == 0) {
//            mInputLayoutHoursNotification.setErrorEnabled(true);
//            mInputLayoutHoursNotification.setError(getString(R.string.activity_settings_notification_input_hours_error_empty));
//            isNotEmpty = false;
//        }
//
//        return isNotEmpty;
//    }
//
//    private void showProgressDialog() {
//        mProgressDialog = new MaterialDialog.Builder(this)
//                .title(R.string.meu_metro_progress_dialog_title_wait)
//                .content(R.string.activity_settings_notification_progress_dialog_saving_settings)
//                .progress(true, 0)
//                .cancelable(false)
//                .show();
//    }
//
//    private void showOrHideFields(boolean switchOfficial, boolean switchByUser) {
//        if (!switchOfficial && !switchByUser) {
//            YoYo.with(Techniques.FadeOutUp)
//                    .duration(200).withListener(animatorListener()).playOn((View) mInputLayoutDaysNotification.getParent());
//            YoYo.with(Techniques.FadeOutUp)
//                    .duration(200).withListener(animatorListener()).playOn((View) mButtonHoursNotification.getParent());
//            YoYo.with(Techniques.FadeOutUp)
//                    .duration(200).withListener(animatorListener()).playOn((View) mButtonLinesNotification.getParent());
//        } else {
//            if (((View) mInputLayoutDaysNotification.getParent()).getAlpha() == 0) {
//                YoYo.with(Techniques.FadeInDown)
//                        .duration(200).withListener(animatorListener()).playOn((View) mInputLayoutDaysNotification.getParent());
//                YoYo.with(Techniques.FadeInDown)
//                        .duration(200).withListener(animatorListener()).playOn((View) mButtonHoursNotification.getParent());
//                YoYo.with(Techniques.FadeInDown)
//                        .duration(200).withListener(animatorListener()).playOn((View) mButtonLinesNotification.getParent());
//            }
//        }
//
//        mInputLayoutDaysNotification.setEnabled(mSwitchOfficial.isChecked() || mSwitchByUser.isChecked());
//        mInputLayoutHoursNotification.setEnabled(mSwitchOfficial.isChecked() || mSwitchByUser.isChecked());
//        mInputLayoutLinesNotification.setEnabled(mSwitchOfficial.isChecked() || mSwitchByUser.isChecked());
//        mButtonDaysNotification.setEnabled(mSwitchOfficial.isChecked() || mSwitchByUser.isChecked());
//        mButtonHoursNotification.setEnabled(mSwitchOfficial.isChecked() || mSwitchByUser.isChecked());
//        mButtonLinesNotification.setEnabled(mSwitchOfficial.isChecked() || mSwitchByUser.isChecked());
//    }
//
//    // ONCLICK
//    @OnClick(R.id.activity_config_notification_button_lines_days_notification)
//    public void onClickShowModalDays() {
//        new MaterialDialog.Builder(this)
//                .title(getString(R.string.activity_settings_notification_hint_lines_days_utilization).replace("*", ""))
//                .items(getResources().getStringArray(R.array.activity_settings_notification_list_days))
//                .itemsCallbackMultiChoice(mIndexDaysNotification, new MaterialDialog.ListCallbackMultiChoice() {
//                    @Override
//                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
//                        mIndexDaysNotification = which;
//                        mListDays = text;
//                        String strLines = "";
//                        for (CharSequence c : text) {
//                            if (strLines.length() > 0) {
//                                strLines += ", " + c.toString();
//                            } else {
//                                strLines = c.toString();
//                            }
//                        }
//                        mInputLayoutDaysNotification.getEditText().setText(strLines);
//                        return false;
//                    }
//                })
//                .positiveText(R.string.meu_metro_message_positive)
//                .negativeText(R.string.meu_metro_message_negative)
//                .show();
//    }
//
//    @OnClick(R.id.activity_config_notification_button_lines_hours_notification)
//    public void onClickShowModalHours() {
//        new MaterialDialog.Builder(this)
//                .title(getString(R.string.activity_settings_notification_hint_lines_hours_utilization).replace("*", ""))
//                .items(getResources().getStringArray(R.array.activity_settings_notification_list_hours))
//                .itemsCallbackMultiChoice(mIndexHoursNotification, new MaterialDialog.ListCallbackMultiChoice() {
//                    @Override
//                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
//                        mIndexHoursNotification = which;
//                        mListHours = text;
//                        String strLines = "";
//                        for (CharSequence c : text) {
//                            if (strLines.length() > 0) {
//                                strLines += ", " + c.toString();
//                            } else {
//                                strLines = c.toString();
//                            }
//                        }
//                        mInputLayoutHoursNotification.getEditText().setText(strLines);
//                        return false;
//                    }
//                })
//                .positiveText(R.string.meu_metro_message_positive)
//                .negativeText(R.string.meu_metro_message_negative)
//                .show();
//    }
//
//    @OnClick(R.id.activity_config_notification_button_lines_notification)
//    public void onClickShowModalLines() {
//        new MaterialDialog.Builder(this)
//                .title(getString(R.string.activity_settings_notification_hint_lines).replace("*", ""))
//                .items(getResources().getStringArray(R.array.activity_edit_status_list_lines))
//                .itemsCallbackMultiChoice(mIndexLinesNotification, new MaterialDialog.ListCallbackMultiChoice() {
//                    @Override
//                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
//                        mIndexLinesNotification = which;
//                        mListLines = text;
//                        String strLines = "";
//                        for (CharSequence c : text) {
//                            if (strLines.length() > 0) {
//                                strLines += ", " + c.toString();
//                            } else {
//                                strLines = c.toString();
//                            }
//                        }
//                        mInputLayoutLinesNotification.getEditText().setText(strLines);
//                        return false;
//                    }
//                })
//                .positiveText(R.string.meu_metro_message_positive)
//                .negativeText(R.string.meu_metro_message_negative)
//                .show();
//    }
//
//    private CompoundButton.OnCheckedChangeListener onCheckedChangedSwitchOfficial() {
//        return new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean on) {
//                if (mSetting == null)
//                    mSetting = new Setting();
//
//                mSetting.setIsNotificationOfficial(on);
//                showOrHideFields(on, mSwitchByUser.isChecked());
//            }
//        };
//    }
//
//    private CompoundButton.OnCheckedChangeListener onCheckedChangedSwitchByUser() {
//        return new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean on) {
//                if (mSetting == null)
//                    mSetting = new Setting();
//
//                mSetting.setIsNotificationByUser(on);
//                showOrHideFields(mSwitchOfficial.isChecked(), on);
//            }
//        };
//    }
//
//    // LISTENERS
//    private void setupWatcherListeners() {
//        EditText editText = mInputLayoutDaysNotification.getEditText();
//        if (editText != null) {
//            editText.setOnFocusChangeListener(this);
//            editText.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    mInputLayoutDaysNotification.setError(null);
//                    mInputLayoutDaysNotification.setErrorEnabled(false);
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                }
//            });
//        }
//
//        editText = mInputLayoutHoursNotification.getEditText();
//        if (editText != null) {
//            editText.setOnFocusChangeListener(this);
//            editText.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    mInputLayoutHoursNotification.setError(null);
//                    mInputLayoutHoursNotification.setErrorEnabled(false);
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                }
//            });
//        }
//
//        editText = mInputLayoutLinesNotification.getEditText();
//        if (editText != null) {
//            editText.setOnFocusChangeListener(this);
//            editText.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    mInputLayoutLinesNotification.setError(null);
//                    mInputLayoutLinesNotification.setErrorEnabled(false);
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                }
//            });
//        }
//    }
//
//    @Override
//    public void onFocusChange(View v, boolean hasFocus) {
//        if (hasFocus) {
//            v.clearFocus();
//            switch (((View) v.getParent().getParent()).getId()) {
//                case R.id.activity_config_notification_input_lines_days_notification:
//                    onClickShowModalDays();
//                    break;
//
//                case R.id.activity_config_notification_input_lines_hours_notification:
//                    onClickShowModalHours();
//                    break;
//
//                case R.id.activity_config_notification_input_lines_notification:
//                    onClickShowModalLines();
//                    break;
//            }
//        }
//    }
//
//    private Animator.AnimatorListener animatorListener() {
//        return new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//                mSwitchByUser.setClickable(false);
//                mSwitchOfficial.setClickable(false);
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                mSwitchByUser.setClickable(true);
//                mSwitchOfficial.setClickable(true);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        };
//    }
//
//    // MENU
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_done, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.menu_done:
//                if (verifyFields()) {
//                    showProgressDialog();
//                    populateObject();
//                    RealmDbHelper.deleteAll(Setting.class);
//                    RealmDbHelper.save(mSetting);
////                    NotificationStatusManager.stopNotificationStatusService(this);
////                    NotificationStatusManager.startNotificationStatusOfficialService(this);
////                    NotificationStatusManager.startNotificationStatusByUserService(this);
//                    mProgressDialog.dismiss();
//                    Toast.makeText(this, R.string.activity_settings_notification_message_settings_saved, Toast.LENGTH_SHORT).show();
//                    onBackPressed();
//                }
//                return true;
//
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//}
