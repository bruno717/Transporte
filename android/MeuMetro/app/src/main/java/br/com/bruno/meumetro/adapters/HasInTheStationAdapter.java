package br.com.bruno.meumetro.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import br.com.bruno.meumetro.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bruno on 08/09/2016.
 */
public class HasInTheStationAdapter extends RecyclerView.Adapter<HasInTheStationAdapter.ViewHolder> {

    private String[] mTexts;

    public HasInTheStationAdapter(String[] texts) {
        mTexts = texts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_has_in_the_station, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mTexts[position]);
    }

    @Override
    public int getItemCount() {
        return mTexts.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cell_has_in_the_station_text_view)
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
