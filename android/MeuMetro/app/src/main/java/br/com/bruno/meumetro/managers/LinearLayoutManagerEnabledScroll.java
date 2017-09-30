package br.com.bruno.meumetro.managers;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * Created by Bruno on 08/09/2016.
 */
public class LinearLayoutManagerEnabledScroll extends LinearLayoutManager {

    private boolean isScrollEnabled = true;

    public LinearLayoutManagerEnabledScroll(Context context) {
        super(context);
    }

    public LinearLayoutManagerEnabledScroll(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public LinearLayoutManagerEnabledScroll(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }

    public void setScrollEnabled(boolean scrollEnabled) {
        isScrollEnabled = scrollEnabled;
    }

}