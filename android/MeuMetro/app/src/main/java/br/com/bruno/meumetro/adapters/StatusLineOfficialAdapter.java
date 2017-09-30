package br.com.bruno.meumetro.adapters;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import br.com.bruno.meumetro.LinesInformationActivity;
import br.com.bruno.meumetro.R;
import br.com.bruno.meumetro.enums.StatusType;
import br.com.bruno.meumetro.models.Line;
import br.com.bruno.meumetro.utils.DrawableUtils;
import br.com.bruno.meumetro.utils.MetricsConverter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bruno on 30/08/2016.
 */
public class StatusLineOfficialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HIDE = 0;
    private static final int PADDING_BOTTOM = 16;
    private final int CELLS_EXTRA = 2;

    private List<Line> mLines;
    private IStatusLineOfficialAdapter mListener;

    public StatusLineOfficialAdapter(List<Line> lines) {
        mLines = lines;
    }

    public StatusLineOfficialAdapter(List<Line> lines, IStatusLineOfficialAdapter listener) {
        mLines = lines;
        mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 || position == 7 ? CellType.COMPANY_CELL.ordinal() : CellType.LINE_CELL.ordinal();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == CellType.COMPANY_CELL.ordinal()) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_status_line_official_company, parent, false);
            return new ViewHolderCompany(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_status_line_official, parent, false);
            return new ViewHolderLine(view);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Context context = holder.itemView.getContext();

        if (holder.getItemViewType() == CellType.COMPANY_CELL.ordinal()) {
            ViewHolderCompany holderCompany = (ViewHolderCompany) holder;
            holderCompany.mTextView.setText(position == 0 ? R.string.meu_metro_metro : R.string.meu_metro_cptm);
            holderCompany.mImageView.setImageResource(position == 0 ? R.mipmap.ic_metro : R.mipmap.ic_cptm);
        } else {
            int index = position > 7 ? position - 2 : position - 1;
            if (index < mLines.size()) {
                Line line = mLines.get(index);
                ViewHolderLine holderLine = (ViewHolderLine) holder;
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holderLine.mLinearLayoutDescription.getLayoutParams();
                params.height = 0;
                holderLine.mLinearLayoutDescription.setLayoutParams(params);
                holderLine.mTextViewLineName.setText(line.getName());
                holderLine.mTextViewLineStatus.setText(line.getSituation());
                holderLine.mViewLineColor.setBackgroundColor(ContextCompat.getColor(context, line.getColorBackground()));
                holderLine.mViewLineColorStatus.setBackgroundColor(ContextCompat.getColor(context, getColorStatus(context, line)));
                if (line.getStatusType() == StatusType.OFFICIAL) {
                    if (line.getDescription() != null && line.getDescription().length() > 0) {
                        holderLine.mTextViewDescription.setText(line.getDescription());
                        holderLine.mTextViewDescriptionInvisible.setText(line.getDescription());
                        holderLine.mButtonShowDetails.setImageDrawable(DrawableUtils.changeColorDrawable(context, R.mipmap.ic_visibility_black_24dp, R.color.primary));
                        holderLine.mButtonShowDetails.setEnabled(true);
                    } else {
                        holderLine.mTextViewDescriptionInvisible.setText("");
                        holderLine.mButtonShowDetails.setImageDrawable(DrawableUtils.changeColorDrawable(context, R.mipmap.ic_visibility_black_24dp, R.color.grey_item_disabled));
                        holderLine.mButtonShowDetails.setEnabled(false);
                    }
                } else {
                    holderLine.mButtonShowDetails.setImageDrawable(DrawableUtils.changeColorDrawable(context, R.mipmap.ic_mode_edit_white_24dp, R.color.primary));
                }
            }
        }
    }

    private int getColorStatus(Context c, Line line) {
        return line.getSituation().equalsIgnoreCase(c.getString(R.string.service_notification_status_normal_operation)) ? R.color.green_status_ok
                : line.getSituation().equalsIgnoreCase(c.getString(R.string.service_notification_status_paralyzed_operation))
                || line.getSituation().toLowerCase().contains(c.getString(R.string.service_notification_status_finish_operation).toLowerCase())
                ? R.color.red_status_stopped : R.color.yellow_status_slow;
    }

    private void expandedViewDescription(final ViewHolderLine holder) {
        int animHeight;
        final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.mLinearLayoutDescription.getLayoutParams();
        params.height = holder.mLinearLayoutDescription.getHeight();
        if (params.height == 0) {
            animHeight = holder.mTextViewDescriptionInvisible.getHeight() + MetricsConverter.dpiToPixels(holder.itemView.getContext(), PADDING_BOTTOM);
        } else {
            animHeight = HIDE;
        }

        ValueAnimator animationSize = ValueAnimator.ofInt(holder.mLinearLayoutDescription.getHeight(), animHeight);
        animationSize.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.height = (Integer) animation.getAnimatedValue();
                holder.mLinearLayoutDescription.requestLayout();
            }
        });
        animationSize.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                holder.mButtonShowDetails.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                holder.mButtonShowDetails.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animationSize.start();
    }

    @Override
    public int getItemCount() {
        return mLines.size() > 0 ? mLines.size() + CELLS_EXTRA : 0;
    }

    public void setItems(List<Line> items){
        mLines = items;
        notifyDataSetChanged();
    }

    public class ViewHolderLine extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.cell_status_line_official_view_line_color)
        View mViewLineColor;
        @Bind(R.id.cell_status_line_official_view_line_color_status)
        View mViewLineColorStatus;
        @Bind(R.id.cell_status_line_official_text_view_line_name)
        TextView mTextViewLineName;
        @Bind(R.id.cell_status_line_official_text_view_line_status)
        TextView mTextViewLineStatus;
        @Bind(R.id.cell_status_line_official_text_view_line_description)
        TextView mTextViewDescription;
        @Bind(R.id.cell_status_line_official_text_view_line_description_invisible)
        TextView mTextViewDescriptionInvisible;
        @Bind(R.id.cell_status_line_official_linear_layout_line_description)
        LinearLayout mLinearLayoutDescription;
        @Bind(R.id.cell_status_line_official_button_show_details)
        ImageButton mButtonShowDetails;

        public ViewHolderLine(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), LinesInformationActivity.class);
            String jsonLine = "";
            try {
                int index = getAdapterPosition() > 7 ? getAdapterPosition() - 2 : getAdapterPosition() - 1;
                mLines.get(index).putStations(v.getContext());
                jsonLine = new ObjectMapper().writeValueAsString(mLines.get(index));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            intent.putExtra(LinesInformationActivity.class.getName(), jsonLine);
            v.getContext().startActivity(intent);
        }

        @OnClick(R.id.cell_status_line_official_button_show_details)
        public void onClickActionIcon() {
            int index = getAdapterPosition() > 7 ? getAdapterPosition() - 2 : getAdapterPosition() - 1;
            if (mLines.get(index).getStatusType() == StatusType.OFFICIAL) {
                expandedViewDescription(this);
            } else {
                if (mListener != null)
                    mListener.onClickItemEditStatus(index);
            }
        }
    }

    public class ViewHolderCompany extends RecyclerView.ViewHolder {

        @Bind(R.id.cell_status_line_official_company_image_view)
        ImageView mImageView;
        @Bind(R.id.cell_status_line_official_company_text_view)
        TextView mTextView;

        public ViewHolderCompany(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private enum CellType {
        COMPANY_CELL,
        LINE_CELL
    }

    public interface IStatusLineOfficialAdapter {
        void onClickItemEditStatus(int index);
    }
}
