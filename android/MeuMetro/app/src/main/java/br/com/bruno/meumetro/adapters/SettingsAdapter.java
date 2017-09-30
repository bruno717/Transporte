package br.com.bruno.meumetro.adapters;

import android.animation.Animator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;

import br.com.bruno.meumetro.R;
import br.com.bruno.meumetro.SettingsNotificationActivity;
import br.com.bruno.meumetro.enums.SettingsListType;
import br.com.bruno.meumetro.utils.DrawableUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bruno on 09/06/2017.
 */
public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    private List<String> mItems;
    private SettingsListType mListType;
    private ISettingsAdapter mListener;

    public SettingsAdapter(CharSequence[] items, SettingsListType listType, ISettingsAdapter listener) {
        mListType = listType;
        mListener = listener;
        mItems = new ArrayList<>();
        for (CharSequence sequence : items) {
            mItems.add(sequence.toString());
        }
    }

    public SettingsAdapter(List<String> items, SettingsListType listType, ISettingsAdapter listener) {
        mListType = listType;
        mListener = listener;
        mItems = items;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_settings, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mImageButtonRemove.setImageDrawable(DrawableUtils.changeColorDrawable(holder.itemView.getContext(), R.mipmap.ic_delete_black_24dp, R.color.line_3_red));
        holder.mTextView.setText(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.cell_settings_text_view)
        TextView mTextView;
        @Bind(R.id.cell_settings_image_button_remove)
        ImageButton mImageButtonRemove;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.cell_settings_image_button_remove)
        public void onClickRemove() {
            int position = getAdapterPosition();
            mListener.onClickRemove(position, mListType);
            YoYo.with(Techniques.SlideOutLeft).duration(SettingsNotificationActivity.TIME_ANIMATION).withListener(animatorListener()).playOn(itemView);
        }

        private Animator.AnimatorListener animatorListener() {
            return new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    int position = getAdapterPosition();
                    notifyItemRemoved(position);
                    mItems.remove(position);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            };
        }
    }

    public interface ISettingsAdapter {
        void onClickRemove(int position, SettingsListType listType);
    }
}
