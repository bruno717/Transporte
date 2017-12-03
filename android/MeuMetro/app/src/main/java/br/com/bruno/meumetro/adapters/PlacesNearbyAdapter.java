package br.com.bruno.meumetro.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.bruno.meumetro.R;
import br.com.bruno.meumetro.models.Place;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bruno on 09/06/2017.
 */
public class PlacesNearbyAdapter extends RecyclerView.Adapter<PlacesNearbyAdapter.ViewHolder> {

    private List<Place> mPlaces;

    public PlacesNearbyAdapter(List<Place> places) {
        mPlaces = places;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_place, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextViewName.setText(mPlaces.get(position).getName());
        holder.mTextViewAddress.setText(mPlaces.get(position).getAddress());
//        holder.mImageViewIcon.setur(mPlaces.get(position).getAddress());

    }

    @Override
    public int getItemCount() {
        return mPlaces != null ? mPlaces.size() : 0;
    }

    public void setPlaces(List<Place> places) {
        mPlaces = places;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cell_places_nearby_text_view_name)
        TextView mTextViewName;
        @BindView(R.id.cell_places_nearby_text_view_address)
        TextView mTextViewAddress;
        @BindView(R.id.cell_places_nearby_image_view_icon_type)
        ImageView mImageViewIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
