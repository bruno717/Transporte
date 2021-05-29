package br.com.bruno.meumetro.fragments;

import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Locale;

import br.com.bruno.meumetro.LinesInformationActivity;
import br.com.bruno.meumetro.R;
import br.com.bruno.meumetro.adapters.StationsOfTheLineAdapter;
import br.com.bruno.meumetro.managers.AnalyticsManager;
import br.com.bruno.meumetro.models.Line;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bruno on 04/09/2016.
 */
public class StationsOfTheLineFragment extends Fragment {

    public static final String STATIONS_OF_THE_LINE_FRAGMENT_LINE_INTENT_KEY = "STATIONS_OF_THE_LINE_FRAGMENT_LINE_INTENT_KEY";

    @BindView(R.id.meu_metro_recycler_view)
    RecyclerView mRecyclerView;

    private Line mLine;

    public StationsOfTheLineFragment() {
    }

    public static StationsOfTheLineFragment newInstance(Line line) {

        StationsOfTheLineFragment fragment = new StationsOfTheLineFragment();

        if (line != null) {
            try {
                Bundle bundle = new Bundle();
                String json = new ObjectMapper().writeValueAsString(line);
                bundle.putString(STATIONS_OF_THE_LINE_FRAGMENT_LINE_INTENT_KEY, json);
                fragment.setArguments(bundle);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_stations_of_the_line, container, false);
        ButterKnife.bind(this, view);

        setupToolbar();
        setupRecyclerView();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        AnalyticsManager.generateLogScreenOpen(getString(R.string.frag_stations_of_the_line_screen));
    }

    private void setupToolbar() {

        if (getArguments() != null) {
            try {
                mLine = new ObjectMapper().readValue(getArguments().getString(STATIONS_OF_THE_LINE_FRAGMENT_LINE_INTENT_KEY), Line.class);
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupRecyclerView() {
        if (getArguments() != null) {
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.setAdapter(new StationsOfTheLineAdapter(getActivity(), mLine.getStations(), mLine.getColorBackground()));
        }
    }
}
