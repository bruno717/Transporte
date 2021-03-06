package br.com.bruno.meumetro.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import br.com.bruno.meumetro.R;
import br.com.bruno.meumetro.adapters.PlacesNearbyAdapter;
import br.com.bruno.meumetro.interfaces.IServiceResponse;
import br.com.bruno.meumetro.models.Place;
import br.com.bruno.meumetro.rest.PlaceService;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bruno on 03/12/2017.
 */

public class PlacesNearbyListFragment extends Fragment {

    @BindView(R.id.meu_metro_swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    @BindView(R.id.meu_metro_recycler_view)
    RecyclerView mRecyclerView;

    private PlacesNearbyAdapter mAdapter;

    public PlacesNearbyListFragment() {
    }

    public static PlacesNearbyListFragment newInstance() {
        return new PlacesNearbyListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_places_nearby_list, container, false);
        ButterKnife.bind(this, view);

        setupSwipeRefresh();
        setupRecyclerView();

        return view;
    }

    private void setupSwipeRefresh() {
        mSwipeRefresh.setColorSchemeResources(R.color.primary);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPlacesNearbyStation();
            }
        });
    }

    private void setupRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new PlacesNearbyAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefresh.setRefreshing(true);
        getPlacesNearbyStation();
    }

    // REST
    private void getPlacesNearbyStation() {
        new PlaceService().getPlacesNearbyStation(new IServiceResponse<List<Place>>() {
            @Override
            public void onSuccess(List<Place> places) {
                if (getActivity() != null) {
                    mAdapter.setPlaces(places);
                    mSwipeRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onError() {
                if (getActivity() != null) {
                    mSwipeRefresh.setRefreshing(false);
                    Toast.makeText(getContext(), "Erro", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
