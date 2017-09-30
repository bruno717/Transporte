package br.com.bruno.meumetro.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by bruno.oliveira on 06/11/2015.
 */
public class MetricsConverter {

    public static int pixelsToDpi(Context context, int size) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int i = (size * dm.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
        return i;
    }

    public static int dpiToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }
}
