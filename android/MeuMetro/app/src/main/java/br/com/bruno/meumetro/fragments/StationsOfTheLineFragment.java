package br.com.bruno.meumetro.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import br.com.bruno.meumetro.LinesInformationActivity;
import br.com.bruno.meumetro.R;
import br.com.bruno.meumetro.adapters.StationsOfTheLineAdapter;
import br.com.bruno.meumetro.models.Line;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bruno on 04/09/2016.
 */
public class StationsOfTheLineFragment extends Fragment {

    @Bind(R.id.meu_metro_recycler_view)
    RecyclerView mRecyclerView;

    public Line mLine;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_stations_of_the_line, container, false);
        ButterKnife.bind(this, view);

        setupToolbar();
        setupRecyclerView();

        return view;
    }

    private void setupToolbar() {
        Toolbar toolbar = ((LinesInformationActivity) getActivity()).getToolbar();
        ActionBar actionBar = ((LinesInformationActivity) getActivity()).getSupportActionBar();
        if (toolbar != null && actionBar != null) {
            actionBar.setTitle(String.format(Locale.US, "%s", mLine.getLineType().getName()));
            toolbar.setBackgroundResource(mLine.getColorBackground());
            toolbar.setTitleTextColor(ContextCompat.getColor(getActivity(), android.R.color.white));
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(), mLine.getColorBackground()));
        }
    }

    private void setupRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(new StationsOfTheLineAdapter(getActivity(), mLine.getStations(), mLine.getColorBackground()));
    }
}
