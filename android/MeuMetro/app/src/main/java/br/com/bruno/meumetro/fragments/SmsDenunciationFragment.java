package br.com.bruno.meumetro.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Locale;

import br.com.bruno.meumetro.R;
import br.com.bruno.meumetro.enums.lines.LineType;
import br.com.bruno.meumetro.managers.AnalyticsManager;
import br.com.bruno.meumetro.utils.StringUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bruno on 02/10/2016.
 */

public class SmsDenunciationFragment extends Fragment implements View.OnFocusChangeListener, View.OnClickListener {

    private final String NUMBER_METRO = "11973332252";
    private final String NUMBER_CPTM = "11971504949";

    private final static int REQUEST_SEND_SMS = 101;

    @BindView(R.id.meu_metro_main_view)
    CoordinatorLayout mMainView;
    @BindView(R.id.frag_sms_denunciation_input_line)
    TextInputLayout mInputLayoutLine;
    @BindView(R.id.frag_sms_denunciation_input_direction_train)
    TextInputLayout mInputLayoutDirectionTrain;
    @BindView(R.id.frag_sms_denunciation_input_next_station)
    TextInputLayout mInputLayoutNextStation;
    @BindView(R.id.frag_sms_denunciation_input_car_number)
    TextInputLayout mInputLayoutCarNumber;
    @BindView(R.id.frag_sms_denunciation_input_subject)
    TextInputLayout mInputLayoutSubject;
    @BindView(R.id.frag_sms_denunciation_input_details)
    TextInputLayout mInputLayoutDetails;
    @BindView(R.id.frag_sms_denunciation_radio_button_cptm)
    RadioButton mRadioButtonCPTM;
    @BindView(R.id.frag_sms_denunciation_radio_button_metro)
    RadioButton mRadioButtonMetro;

    private int mIndexLine = -1;
    private int mIndexDirection = -1;
    private int mIndexNextStation = -1;
    private int mIndexSubject = -1;
    private final int MAX_LENGTH_DETAILS_DEFAULT = 142;
    private int mCountLengthLine = 0;
    private int mCountLengthDirection = 0;
    private int mCountLengthNexStation = 0;
    private int mCountLengthNumberCar = 0;
    private int mCountLengthSubject = 0;
    private int mCountLengthDetails = 0;
    private String mNumberSend;
    private MaterialDialog mProgressDialog;
    private boolean isScreenShow = true;
    private boolean isNeedBackScreen = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_sms_denunciation, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        setupToolbar();
        setupWatcherListeners();
        hasPermissionSendSms();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        isScreenShow = true;
        AnalyticsManager.generateLogScreenOpen(getString(R.string.frag_sms_denunciation));
        if (isNeedBackScreen) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getActivity().onBackPressed();
                }
            }, 200);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        isScreenShow = false;
        Log.i("EINSTEIN", "onStop");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_SEND_SMS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (verifyFields()) {
                    sendSmsDenunciation();
                }
            } else {
                Toast.makeText(getActivity(), "Permissão não concedida!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // UTILS
    private void setupToolbar() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.frag_sms_denunciation_toolbar_title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void showProgressDialog() {
        mProgressDialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.meu_metro_progress_dialog_title_wait)
                .content(R.string.frag_sms_denunciation_dialog_content)
                .progress(true, 0)
                .cancelable(false)
                .show();
    }

    private void sendSmsDenunciation() {
        showProgressDialog();
        String message = String.format(Locale.US, "L: %s\n", mInputLayoutLine.getEditText().getText().toString());
        message += String.format(Locale.US, "S: %s\n", mInputLayoutDirectionTrain.getEditText().getText().toString());
        message += String.format(Locale.US, "P: %s\n", mInputLayoutNextStation.getEditText().getText().toString());
        message += String.format(Locale.US, "C: %s\n", mInputLayoutCarNumber.getEditText().getText().toString());
        message += String.format(Locale.US, "T: %s\n", mInputLayoutSubject.getEditText().getText().toString());
        message += String.format(Locale.US, "Cm: %s", mInputLayoutDetails.getEditText().getText().toString());
        message = StringUtils.removeAccent(message);

        try {
            String SENT = "sent";
            String DELIVERED = "delivered";
            Intent sentIntent = new Intent(SENT);
            Intent deliveryIntent = new Intent(DELIVERED);
            PendingIntent sentPI = PendingIntent.getBroadcast(getActivity(), 0, sentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent deliverPI = PendingIntent.getBroadcast(getActivity(), 0, deliveryIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            getActivity().registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    getActivity().unregisterReceiver(this);
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            Toast.makeText(context, R.string.frag_sms_denunciation_message_success, Toast.LENGTH_LONG).show();
                            if (getActivity() != null && isScreenShow) {
                                getActivity().onBackPressed();
                            } else if (!isNeedBackScreen) {
                                isNeedBackScreen = true;
                            }
                            break;

                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            if (getActivity() != null)
                                Snackbar.make(mMainView, R.string.frag_sms_denunciation_message_error_send_sms, Snackbar.LENGTH_INDEFINITE)
                                        .setActionTextColor(ContextCompat.getColor(getActivity(), android.R.color.holo_red_light))
                                        .setAction(R.string.meu_metro_message_positive, SmsDenunciationFragment.this)
                                        .show();
                            break;

                        default:
                            if (getActivity() != null)
                                Snackbar.make(mMainView, R.string.frag_sms_denunciation_message_error_send_sms, Snackbar.LENGTH_INDEFINITE)
                                        .setActionTextColor(ContextCompat.getColor(getActivity(), android.R.color.holo_red_light))
                                        .setAction(R.string.meu_metro_message_positive, SmsDenunciationFragment.this)
                                        .show();
                            break;
                    }
                    if (mProgressDialog != null)
                        mProgressDialog.dismiss();
                }
            }, new IntentFilter(SENT));

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(mNumberSend, null, message, sentPI, deliverPI);
        } catch (Exception e) {
            e.printStackTrace();
            if (getActivity() != null)
                Snackbar.make(mMainView, R.string.frag_sms_denunciation_message_error_send_sms, Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(ContextCompat.getColor(getActivity(), android.R.color.holo_red_light))
                        .setAction(R.string.meu_metro_message_positive, SmsDenunciationFragment.this)
                        .show();
        }
    }

    private boolean hasPermissionSendSms() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, REQUEST_SEND_SMS);
            }
        }
        return true;
    }

    private boolean verifyFields() {
        boolean isNotEmpty = true;

        if (mInputLayoutLine.getEditText().getText().toString().trim().length() == 0) {
            mInputLayoutLine.setErrorEnabled(true);
            mInputLayoutLine.setError(getString(R.string.frag_sms_denunciation_empty_field_line));
            isNotEmpty = false;
        }
        if (mInputLayoutDirectionTrain.getEditText().getText().toString().trim().length() == 0) {
            mInputLayoutDirectionTrain.setErrorEnabled(true);
            mInputLayoutDirectionTrain.setError(getString(R.string.frag_sms_denunciation_empty_field_direction));
            isNotEmpty = false;
        }
        if (mInputLayoutNextStation.getEditText().getText().toString().trim().length() == 0) {
            mInputLayoutNextStation.setErrorEnabled(true);
            mInputLayoutNextStation.setError(getString(R.string.frag_sms_denunciation_empty_field_next_station));
            isNotEmpty = false;
        }
        if (mInputLayoutSubject.getEditText().getText().toString().trim().length() == 0) {
            mInputLayoutSubject.setErrorEnabled(true);
            mInputLayoutSubject.setError(getString(R.string.frag_sms_denunciation_empty_field_subject));
            isNotEmpty = false;
        }
        if (!mRadioButtonCPTM.isChecked() && !mRadioButtonMetro.isChecked()) {
            YoYo.with(Techniques.Shake).playOn((View) mRadioButtonMetro.getParent());
            isNotEmpty = false;
        }

        if (mInputLayoutDetails.getEditText().getText().length() > mInputLayoutDetails.getCounterMaxLength()) {
            Toast.makeText(getActivity(), R.string.frag_sms_denunciation_message_max_length, Toast.LENGTH_LONG).show();
            isNotEmpty = false;
        }

        return isNotEmpty;
    }

    private void countLengthFieldDetails() {
        int count = MAX_LENGTH_DETAILS_DEFAULT - mCountLengthLine - mCountLengthDirection - mCountLengthNexStation - mCountLengthNumberCar - mCountLengthSubject - mCountLengthSubject;
        mInputLayoutDetails.setCounterMaxLength(count);
        mInputLayoutDetails.getEditText().setFilters(new InputFilter[]{new InputFilter.LengthFilter(count)});
    }

    // ONCLICK
    @OnClick({R.id.frag_sms_denunciation_radio_button_metro, R.id.frag_sms_denunciation_radio_button_cptm})
    public void onRadioButtonClicked(RadioButton radioButton) {

        switch (radioButton.getId()) {
            case R.id.frag_sms_denunciation_radio_button_metro:
                mNumberSend = NUMBER_METRO;
                break;

            case R.id.frag_sms_denunciation_radio_button_cptm:
                mNumberSend = NUMBER_CPTM;
                break;
        }
    }

    @OnClick(R.id.frag_sms_denunciation_button_lines)
    public void onClickShowModalLines() {
        new MaterialDialog.Builder(getActivity())
                .title(getString(R.string.frag_sms_denunciation_input_hint_lines).replace("*", ""))
                .items(getResources().getStringArray(R.array.activity_edit_status_list_lines))
                .itemsCallbackSingleChoice(mIndexLine, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        mIndexLine = which;
                        mInputLayoutLine.getEditText().setText(text);

                        mIndexDirection = -1;
                        mIndexNextStation = -1;
                        mInputLayoutDirectionTrain.getEditText().getText().clear();
                        mInputLayoutNextStation.getEditText().getText().clear();
                        return false;
                    }
                }).show();
    }

    @OnClick(R.id.frag_sms_denunciation_button_direction)
    public void onClickShowModalDirection() {
        LineType lineType = LineType.getLineTypeByPosition(mIndexLine);
        if (lineType != null) {
            new MaterialDialog.Builder(getActivity())
                    .title(getString(R.string.frag_sms_denunciation_input_hint_direction_train).replace("*", ""))
                    .items(getResources().getStringArray(lineType.getDirectionRes()))
                    .itemsCallbackSingleChoice(mIndexDirection, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                            mIndexDirection = which;
                            mInputLayoutDirectionTrain.getEditText().setText(text);
                            return false;
                        }
                    }).show();
        } else {
            Toast.makeText(getContext(), R.string.frag_sms_denunciation_empty_field_line, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.frag_sms_denunciation_button_next_station)
    public void onClickShowModalNextStation() {
        LineType lineType = LineType.getLineTypeByPosition(mIndexLine);
        if (lineType != null) {
            new MaterialDialog.Builder(getActivity())
                .title(getString(R.string.frag_sms_denunciation_input_hint_next_station).replace("*", ""))
                .items(getResources().getStringArray(lineType.getSeasonsRes()))
                .itemsCallbackSingleChoice(mIndexNextStation, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        mIndexNextStation = which;
                        mInputLayoutNextStation.getEditText().setText(text);
                        return false;
                    }
                }).show();
        } else {
            Toast.makeText(getContext(), R.string.frag_sms_denunciation_empty_field_line, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.frag_sms_denunciation_button_subject)
    public void onClickShowModalSubject() {
        new MaterialDialog.Builder(getActivity())
                .title(getString(R.string.frag_sms_denunciation_input_hint_subject).replace("*", ""))
                .items(getResources().getStringArray(R.array.frag_sms_denunciation_subject))
                .itemsCallbackSingleChoice(mIndexSubject, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        mIndexSubject = which;
                        mInputLayoutSubject.getEditText().setText(text);
                        return false;
                    }
                }).show();
    }

    //MENU
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_done, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_done:
                if (hasPermissionSendSms() && verifyFields()) {
                    sendSmsDenunciation();
                }
                return true;

            case android.R.id.home:
                getActivity().onBackPressed();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    // LISTENERS
    private void setupWatcherListeners() {
        EditText editText = mInputLayoutLine.getEditText();
        if (editText != null) {
            editText.setOnFocusChangeListener(this);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    mInputLayoutLine.setError(null);
                    mInputLayoutLine.setErrorEnabled(false);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mCountLengthLine = s.length();
                    countLengthFieldDetails();
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        editText = mInputLayoutDirectionTrain.getEditText();
        if (editText != null) {
            editText.setOnFocusChangeListener(this);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    mInputLayoutDirectionTrain.setError(null);
                    mInputLayoutDirectionTrain.setErrorEnabled(false);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mCountLengthDirection = s.length();
                    countLengthFieldDetails();
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        editText = mInputLayoutNextStation.getEditText();
        if (editText != null) {
            editText.setOnFocusChangeListener(this);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    mInputLayoutNextStation.setError(null);
                    mInputLayoutNextStation.setErrorEnabled(false);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mCountLengthNexStation = s.length();
                    countLengthFieldDetails();
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        editText = mInputLayoutSubject.getEditText();
        if (editText != null) {
            editText.setOnFocusChangeListener(this);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    mInputLayoutSubject.setError(null);
                    mInputLayoutSubject.setErrorEnabled(false);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mCountLengthSubject = s.length();
                    countLengthFieldDetails();
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        editText = mInputLayoutCarNumber.getEditText();
        if (editText != null) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    mInputLayoutCarNumber.setError(null);
                    mInputLayoutCarNumber.setErrorEnabled(false);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mCountLengthNumberCar = s.length();
                    countLengthFieldDetails();
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            v.clearFocus();
            switch (((View) v.getParent().getParent()).getId()) {
                case R.id.frag_sms_denunciation_input_line:
                    onClickShowModalLines();
                    break;

                case R.id.frag_sms_denunciation_input_direction_train:
                    onClickShowModalDirection();
                    break;

                case R.id.frag_sms_denunciation_input_next_station:
                    onClickShowModalNextStation();
                    break;

                case R.id.frag_sms_denunciation_input_subject:
                    onClickShowModalSubject();
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        // CONSUMIR CLICK DO SNACKBAR
    }
}
