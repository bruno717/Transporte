package br.com.bruno.meumetro.fragments;

import android.location.Address;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import br.com.bruno.meumetro.R;
import br.com.bruno.meumetro.managers.GeoLocationManager;
import butterknife.ButterKnife;

/**
 * Created by Bruno on 14/01/2018.
 */

public class PlacesNearbyMapFragment extends Fragment implements OnMapReadyCallback {

    private static final String PLACES_NEARBY_MAP_FRAGMENT_ADDRESS_INTENT_KEY = "PLACES_NEARBY_MAP_FRAGMENT_ADDRESS_INTENT_KEY";

    private SupportMapFragment mMapFragment;

    public PlacesNearbyMapFragment() {
    }

    public static PlacesNearbyMapFragment newInstance(Address address) {
        PlacesNearbyMapFragment fragment = new PlacesNearbyMapFragment();
        if (address != null) {
            Bundle args = new Bundle();
            args.putParcelable(PLACES_NEARBY_MAP_FRAGMENT_ADDRESS_INTENT_KEY, address);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_places_nearby_map, container, false);
        ButterKnife.bind(this, view);

        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frag_places_nearby_map);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (getActivity() != null && getArguments() != null) {
            Address address = getArguments().getParcelable(PLACES_NEARBY_MAP_FRAGMENT_ADDRESS_INTENT_KEY);
            if (address != null)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(), address.getLongitude()), GeoLocationManager.APPROXIMATE_ZOOM));
        }
    }
}
