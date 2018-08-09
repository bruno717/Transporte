package br.com.bruno.meumetro.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import br.com.bruno.meumetro.MainActivity;
import br.com.bruno.meumetro.R;
import br.com.bruno.meumetro.adapters.StatusLineOfficialAdapter;
import br.com.bruno.meumetro.database.LineDbHelper;
import br.com.bruno.meumetro.enums.StatusType;
import br.com.bruno.meumetro.interfaces.IServiceResponse;
import br.com.bruno.meumetro.managers.AnalyticsManager;
import br.com.bruno.meumetro.managers.LineManager;
import br.com.bruno.meumetro.managers.SharedPreferenceManager;
import br.com.bruno.meumetro.models.Line;
import br.com.bruno.meumetro.rest.StatusLineService;
import br.com.bruno.meumetro.utils.ConnectionUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Bruno on 13/11/2016.
 */
public class StatusLineByUserFragment extends Fragment implements StatusLineOfficialAdapter.IStatusLineOfficialAdapter {

    @BindView(R.id.meu_metro_main_view)
    RelativeLayout mMainView;
    @BindView(R.id.meu_metro_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.meu_metro_swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private Boolean mHasLoadList = true;
    private List<Line> mLines;
    private MaterialDialog mProgressDialog;
    private StatusLineOfficialAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_status_line_by_user, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        setupSwipeRefresh();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity().getIntent().getBooleanExtra(MainActivity.MAIN_ACTIVITY_INTENT_KEY_IS_TAB_USER, false)) {
            getActivity().getIntent().removeExtra(MainActivity.MAIN_ACTIVITY_INTENT_KEY_IS_TAB_USER);
            setupRecyclerView();
        }
        AnalyticsManager.generateLogScreenOpen(getString(R.string.frag_status_line_by_user_screen));
    }

    // UTILS
    public void setupRecyclerView() {
        if (getActivity() != null && mHasLoadList) {
            mSwipeRefresh.setRefreshing(true);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(manager);
            getSituationLines();
            mHasLoadList = false;
        }
    }

    private void setupList(List<Line> lines) {
        if (mAdapter == null) {
            mAdapter = new StatusLineOfficialAdapter(lines, StatusLineByUserFragment.this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(lines);
        }
    }

    private void setupSwipeRefresh() {
        mSwipeRefresh.setColorSchemeResources(R.color.primary);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSituationLines();
            }
        });
    }

    private void showProgressDialog() {
        if (getActivity() != null)
            mProgressDialog = new MaterialDialog.Builder(getActivity())
                    .title(R.string.meu_metro_progress_dialog_title_wait)
                    .content(R.string.activity_edit_status_dialog_message_refreshing_status)
                    .progress(true, 0)
                    .cancelable(false)
                    .show();
    }

    // REQUEST
    private void getSituationLines() {
        if (ConnectionUtils.isConnected(getActivity())) {
            new StatusLineService().getLinesStatusLinesByUser(new IServiceResponse<List<Line>>() {
                @Override
                public void onSuccess(List<Line> lines) {
                    if (getActivity() != null) {
                        lines = LineManager.sortLines(lines);
                        LineManager.saveLines(lines, StatusType.USER);
                        mLines = lines;
                        mSwipeRefresh.setRefreshing(false);
                        SharedPreferenceManager.saveDateLastRefresh(SharedPreferenceManager.LINE_BY_USER);
                        setupList(lines);
                    }
                }

                @Override
                public void onError() {
                    if (getActivity() != null) {
                        List<Line> lines = LineManager.sortLines(LineDbHelper.getLinesByTypeStatus(StatusType.USER));
                        mLines = Realm.getDefaultInstance().copyFromRealm(lines);
                        setupList(lines);
                        mSwipeRefresh.setRefreshing(false);
                        Toast.makeText(getActivity(), R.string.frag_status_line_official_load_error, Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            setupList(LineManager.sortLines(LineDbHelper.getLinesByTypeStatus(StatusType.USER)));
            Toast.makeText(getActivity(), R.string.meu_metro_message_no_connection, Toast.LENGTH_LONG).show();
            mSwipeRefresh.setRefreshing(false);
        }
    }

    private void editStatusLine(Line line) {
        if (ConnectionUtils.isConnected(getActivity())) {
            showProgressDialog();
            new StatusLineService().updateStatusLine(line, new IServiceResponse<Line>() {
                @Override
                public void onSuccess(Line line) {
                    mProgressDialog.dismiss();
                    mSwipeRefresh.setRefreshing(true);
                    getSituationLines();
                }

                @Override
                public void onError() {
                    mProgressDialog.dismiss();
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), R.string.activity_edit_status_message_error, Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), R.string.meu_metro_message_no_connection, Toast.LENGTH_LONG).show();
        }
    }

    // LISTENERS
    @Override
    public void onClickItemEditStatus(final int index) {
        if (ConnectionUtils.isConnected(getActivity())) {
            new MaterialDialog.Builder(getActivity())
                    .title(mLines.get(index).getName())
                    .items(getResources().getStringArray(R.array.activity_edit_status_list_status))
                    .positiveText("Alterar")
                    .negativeText(R.string.meu_metro_message_negative)
                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                            if (text != null && !text.toString().isEmpty()) {
                                Line line = mLines.get(index);
                                line.setSituation(text.toString());
                                editStatusLine(line);
                            }
                            return false;
                        }
                    })
                    .show();
        } else {
            Toast.makeText(getActivity(), R.string.meu_metro_message_no_connection, Toast.LENGTH_LONG).show();
        }
    }
}
