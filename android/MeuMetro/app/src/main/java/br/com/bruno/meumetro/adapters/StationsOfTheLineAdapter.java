package br.com.bruno.meumetro.adapters;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.bruno.meumetro.R;
import br.com.bruno.meumetro.fragments.InformationStationFragment;
import br.com.bruno.meumetro.models.Station;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bruno on 04/09/2016.
 */
public class StationsOfTheLineAdapter extends RecyclerView.Adapter<StationsOfTheLineAdapter.ViewHolder> {

    private List<Station> mStations;
    private int mColorBackground;
    private FragmentActivity mFragActivity;

    public StationsOfTheLineAdapter(FragmentActivity fragActivity, List<Station> stations, int colorBackground) {
        mFragActivity = fragActivity;
        mStations = stations;
        mColorBackground = colorBackground;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_station_of_the_line, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mTextViewLineName.setText(mStations.get(position).getName());
        int drawableRes = position == 0 ? R.drawable.background_line_top_radius : position == mStations.size() - 1 ? R.drawable.background_line_bottom_radius : R.drawable.background_line_rectangle;
        Drawable drawable = ContextCompat.getDrawable(holder.itemView.getContext(), drawableRes);
        DrawableCompat.setTint(DrawableCompat.wrap(drawable), ContextCompat.getColor(holder.itemView.getContext(), mColorBackground));
        holder.mView.setBackground(drawable);
    }

    @Override
    public int getItemCount() {
        return mStations.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.cell_station_of_the_line_text_view_line)
        TextView mTextViewLineName;
        @BindView(R.id.cell_station_of_the_line_view_line_vertical)
        View mView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            InformationStationFragment fragment = new InformationStationFragment();
            fragment.mStation = mStations.get(getAdapterPosition());
            fragment.colorLine = mColorBackground;
            mFragActivity.getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.meu_metro_container_fragment, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
