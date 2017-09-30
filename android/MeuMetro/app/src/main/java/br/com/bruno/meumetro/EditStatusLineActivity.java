//package br.com.bruno.meumetro;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.design.widget.Snackbar;
//import android.support.design.widget.TextInputLayout;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ScrollView;
//
//import com.afollestad.materialdialogs.MaterialDialog;
//
//import java.util.List;
//
//import br.com.bruno.meumetro.database.LineDbHelper;
//import br.com.bruno.meumetro.database.RealmDbHelper;
//import br.com.bruno.meumetro.enums.StatusType;
//import br.com.bruno.meumetro.enums.lines.LineType;
//import br.com.bruno.meumetro.interfaces.IServiceResponse;
//import br.com.bruno.meumetro.models.Line;
//import br.com.bruno.meumetro.rest.StatusLineService;
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
///**
// * Created by Bruno on 18/02/2017.
// */
//
//public class EditStatusLineActivity extends AppCompatActivity implements View.OnFocusChangeListener {
//
//    @Bind(R.id.meu_metro_main_view)
//    ScrollView mMainView;
//    @Bind(R.id.meu_metro_toolbar)
//    Toolbar mToolbar;
//    @Bind(R.id.activity_edit_status_input_line)
//    TextInputLayout mInputLayoutLine;
//    @Bind(R.id.activity_edit_status_input_status)
//    TextInputLayout mInputLayoutStatus;
//    @Bind(R.id.activity_edit_status_input_description)
//    TextInputLayout mInputLayoutDescription;
//
//    private int mIndexLine = -1;
//    private int mIndexStatus = -1;
//    private MaterialDialog mProgressDialog;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_status_line);
//        ButterKnife.bind(this);
//
//        setupToolbar();
//        setupListenersFields();
//    }
//
//
//    // UTILS
//    private void setupToolbar() {
//        mToolbar.setTitle(R.string.activity_edit_status_toolbar_title);
//        mToolbar.setNavigationIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.md_nav_back));
//        mToolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), android.R.color.white));
//        setSupportActionBar(mToolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//    }
//
//    private boolean verifyFields() {
//        boolean isNotEmpty = true;
//
//        if (mInputLayoutLine.getEditText().getText().toString().trim().length() == 0) {
//            mInputLayoutLine.setErrorEnabled(true);
//            mInputLayoutLine.setError(getString(R.string.activity_edit_status_input_line_error_empty));
//            isNotEmpty = false;
//        }
//        if (mInputLayoutStatus.getEditText().getText().toString().trim().length() == 0) {
//            mInputLayoutStatus.setErrorEnabled(true);
//            mInputLayoutStatus.setError(getString(R.string.activity_edit_status_input_status_error_empty));
//            isNotEmpty = false;
//        }
//
//        return isNotEmpty;
//    }
//
//    private Line populateObject() {
//        Line line = new Line();
//        line.setSituation(mInputLayoutStatus.getEditText().getText().toString());
//        line.setDescription(mInputLayoutDescription.getEditText().getText().toString());
//        line.setLineType(LineType.getLineTypeByPosition(mIndexLine));
//        line.setName(LineType.getLineTypeByPosition(mIndexLine).getName());
//        List<Line> lines = LineDbHelper.getLinesByTypeStatus(StatusType.USER);
//        for (Line l : lines) {
//            if (l.getName().equals(line.getName())) {
//                line.setId(l.getId());
//                break;
//            }
//        }
//        return line;
//    }
//
//    private void showProgressDialog() {
//        mProgressDialog = new MaterialDialog.Builder(this)
//                .title(R.string.meu_metro_progress_dialog_title_wait)
//                .content(R.string.activity_edit_status_dialog_message_refreshing_status)
//                .progress(true, 0)
//                .cancelable(false)
//                .show();
//    }
//
//    // REQUEST
//    private void editStatusLine() {
//        showProgressDialog();
//        new StatusLineService().updateStatusLine(populateObject(), new IServiceResponse<Line>() {
//            @Override
//            public void onSuccess(Line line) {
//                mProgressDialog.dismiss();
//                setResult(Activity.RESULT_OK);
//                finish();
//            }
//
//            @Override
//            public void onError() {
//                mProgressDialog.dismiss();
//                Snackbar.make(mMainView, R.string.activity_edit_status_message_error, Snackbar.LENGTH_LONG).setAction(R.string.meu_metro_message_positive, null)
//                        .show();
//            }
//        });
//    }
//
//    // ONCLICK
//    @OnClick(R.id.activity_edit_status_button_line)
//    public void onClickShowModalLines() {
//        new MaterialDialog.Builder(this)
//                .title(getString(R.string.activity_edit_status_input_hint_line).replace("*", ""))
//                .items(getResources().getStringArray(R.array.activity_edit_status_list_lines))
//                .itemsCallbackSingleChoice(mIndexLine, new MaterialDialog.ListCallbackSingleChoice() {
//                    @Override
//                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
//                        mIndexLine = which;
//                        mInputLayoutLine.getEditText().setText(text);
//                        return false;
//                    }
//                }).show();
//    }
//
//    @OnClick(R.id.activity_edit_status_button_status)
//    public void onClickShowModalStatus() {
//        new MaterialDialog.Builder(this)
//                .title(getString(R.string.activity_edit_status_input_hint_status).replace("*", ""))
//                .items(getResources().getStringArray(R.array.activity_edit_status_list_status))
//                .itemsCallbackSingleChoice(mIndexStatus, new MaterialDialog.ListCallbackSingleChoice() {
//                    @Override
//                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
//                        mIndexStatus = which;
//                        mInputLayoutStatus.getEditText().setText(text);
//                        return false;
//                    }
//                }).show();
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
//                    editStatusLine();
//                }
//                return true;
//
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    // LISTENERS
//    private void setupListenersFields() {
//        EditText editText = mInputLayoutLine.getEditText();
//        if (editText != null) {
//            editText.setOnFocusChangeListener(this);
//            editText.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                    mInputLayoutLine.setError(null);
//                    mInputLayoutLine.setErrorEnabled(false);
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//
//                }
//            });
//        }
//
//        editText = mInputLayoutStatus.getEditText();
//        if (editText != null) {
//            editText.setOnFocusChangeListener(this);
//            editText.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                    mInputLayoutStatus.setError(null);
//                    mInputLayoutStatus.setErrorEnabled(false);
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//
//                }
//            });
//        }
//
//        editText = mInputLayoutDescription.getEditText();
//        if (editText != null) {
//            editText.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//
//                }
//            });
//        }
//
//    }
//
//    @Override
//    public void onFocusChange(View v, boolean hasFocus) {
//        if (hasFocus) {
//            v.clearFocus();
//            switch (((View) v.getParent()).getId()) {
//                case R.id.activity_edit_status_button_line:
//                    onClickShowModalLines();
//                    break;
//
//                case R.id.activity_edit_status_button_status:
//                    onClickShowModalStatus();
//                    break;
//            }
//        }
//    }
//}
