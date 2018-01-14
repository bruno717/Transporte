package br.com.bruno.meumetro.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import br.com.bruno.meumetro.PlacesNearbyActivity;
import br.com.bruno.meumetro.R;
import br.com.bruno.meumetro.adapters.HasInTheStationAdapter;
import br.com.bruno.meumetro.managers.LinearLayoutManagerEnabledScroll;
import br.com.bruno.meumetro.models.Station;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bruno on 07/09/2016.
 */
public class InformationStationFragment extends Fragment {

    private static final String INFORMATION_STATION_FRAGMENT_STATION_KEY = "INFORMATION_STATION_FRAGMENT_STATION_KEY";
    private static final String INFORMATION_STATION_FRAGMENT_COLOR_KEY = "INFORMATION_STATION_FRAGMENT_COLOR_KEY";

    @BindView(R.id.frag_information_station_text_view_operation_sunday_friday)
    TextView mTextViewOperationSundayFriday;

    @BindView(R.id.frag_information_station_text_view_operation_saturday)
    TextView mTextViewOperationSaturday;

    @BindView(R.id.frag_information_station_text_view_localization)
    TextView mTextViewLocalization;

    @BindView(R.id.meu_metro_recycler_view)
    RecyclerView mRecyclerView;

    private Station mStation;
    private int colorLine;

    public InformationStationFragment() {
    }

    public static InformationStationFragment newInstance(Station station, int colorLine) {
        InformationStationFragment fragment = new InformationStationFragment();
        if (station != null && colorLine > 0) {
            try {
                Bundle args = new Bundle();
                ObjectMapper mapper = new ObjectMapper();
                args.putString(INFORMATION_STATION_FRAGMENT_STATION_KEY, mapper.writeValueAsString(station));
                args.putInt(INFORMATION_STATION_FRAGMENT_COLOR_KEY, colorLine);
                fragment.setArguments(args);
            } catch (JsonProcessingException e) {
                Crashlytics.logException(e);
                e.printStackTrace();
            }
        }
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_information_station, container, false);
        ButterKnife.bind(this, view);

        setupValues();
        setupToolbar();
        setupInfoInTheViews();
        setupRecyclerView();
        setupStatusBar();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PlacesNearbyActivity.class));
            }
        });

        return view;
    }

    private void setupValues() {
        if (getArguments() != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mStation = mapper.readValue(getArguments().getString(INFORMATION_STATION_FRAGMENT_STATION_KEY), Station.class);
                colorLine = getArguments().getInt(INFORMATION_STATION_FRAGMENT_COLOR_KEY);
            } catch (IOException e) {
                Crashlytics.logException(e);
                e.printStackTrace();
            }
        }
    }

    private void setupToolbar() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(mStation.getName());
    }

    private void setupStatusBar() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(), colorLine));
        }
    }

    private void setupInfoInTheViews() {
        mTextViewOperationSundayFriday.setText(mStation.getOperationSundayToFriday());
        mTextViewOperationSaturday.setText(mStation.getOperationSaturday());
        mTextViewLocalization.setText(mStation.getLocation());
    }

    private void setupRecyclerView() {
        LinearLayoutManagerEnabledScroll manager = new LinearLayoutManagerEnabledScroll(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setScrollEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(new HasInTheStationAdapter(mStation.getAtTheStation()));
    }
}
