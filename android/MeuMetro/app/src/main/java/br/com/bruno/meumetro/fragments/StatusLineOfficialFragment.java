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

import java.util.List;

import br.com.bruno.meumetro.R;
import br.com.bruno.meumetro.adapters.StatusLineOfficialAdapter;
import br.com.bruno.meumetro.database.LineDbHelper;
import br.com.bruno.meumetro.enums.StatusType;
import br.com.bruno.meumetro.interfaces.IServiceResponse;
import br.com.bruno.meumetro.managers.LineManager;
import br.com.bruno.meumetro.managers.SharedPreferenceManager;
import br.com.bruno.meumetro.models.Line;
import br.com.bruno.meumetro.rest.StatusLineService;
import br.com.bruno.meumetro.utils.ConnectionUtils;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bruno on 30/08/2016.
 */
public class StatusLineOfficialFragment extends Fragment {

    @Bind(R.id.meu_metro_main_view)
    RelativeLayout mMainView;
    @Bind(R.id.meu_metro_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.meu_metro_swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private StatusLineOfficialAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_status_line_official, container, false);
        ButterKnife.bind(this, view);

        setupRecyclerView();
        setupSwipeRefresh();

        return view;
    }

    private void setupRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mSwipeRefresh.setRefreshing(true);
        getSituationLines();
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

    private void setupList(List<Line> lines) {
        if (mAdapter == null) {
            mAdapter = new StatusLineOfficialAdapter(lines);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setItems(lines);
        }
    }

    // REQUEST
    private void getSituationLines() {
        if (ConnectionUtils.isConnected(getActivity())) {
            new StatusLineService().getLinesStatusLinesOfficial(new IServiceResponse<List<Line>>() {
                @Override
                public void onSuccess(List<Line> lines) {
                    lines = LineManager.sortLines(lines);
                    LineManager.saveLines(lines, StatusType.OFFICIAL);
                    mSwipeRefresh.setRefreshing(false);
                    SharedPreferenceManager.saveDateLastRefresh(SharedPreferenceManager.LINE_OFFICIAL);
                    setupList(lines);
                }

                @Override
                public void onError() {
                    setupList(LineManager.sortLines(LineDbHelper.getLinesByTypeStatus(StatusType.OFFICIAL)));
                    if (getActivity() != null) {
                        mSwipeRefresh.setRefreshing(false);
                        Toast.makeText(getActivity(), R.string.frag_status_line_official_load_error, Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            setupList(LineManager.sortLines(LineDbHelper.getLinesByTypeStatus(StatusType.OFFICIAL)));
            Toast.makeText(getActivity(), R.string.meu_metro_message_no_connection, Toast.LENGTH_LONG).show();
            mSwipeRefresh.setRefreshing(false);
        }
    }
}
