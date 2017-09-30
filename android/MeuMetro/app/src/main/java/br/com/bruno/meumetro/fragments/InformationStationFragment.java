package br.com.bruno.meumetro.fragments;

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

import br.com.bruno.meumetro.R;
import br.com.bruno.meumetro.adapters.HasInTheStationAdapter;
import br.com.bruno.meumetro.managers.LinearLayoutManagerEnabledScroll;
import br.com.bruno.meumetro.models.Station;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Bruno on 07/09/2016.
 */
public class InformationStationFragment extends Fragment {

    @Bind(R.id.frag_information_station_text_view_operation_sunday_friday)
    TextView mTextViewOperationSundayFriday;
    @Bind(R.id.frag_information_station_text_view_operation_saturday)
    TextView mTextViewOperationSaturday;
    @Bind(R.id.frag_information_station_text_view_localization)
    TextView mTextViewLocalization;
    @Bind(R.id.meu_metro_recycler_view)
    RecyclerView mRecyclerView;

    public Station mStation;
    public int colorLine;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_information_station, container, false);
        ButterKnife.bind(this, view);

        setupToolbar();
        setupInfoInTheViews();
        setupRecyclerView();
        setupStatusBar();

        return view;
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
