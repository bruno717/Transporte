package br.com.bruno.meumetro.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * Created by Bruno on 15/05/2017.
 */

public class DrawableUtils {

    public static Drawable changeColorDrawable(Context c, int iconRes, int color) {
        Drawable wrapDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(c, iconRes));
        DrawableCompat.setTint(wrapDrawable.mutate(), ContextCompat.getColor(c, color));
        return wrapDrawable;
    }
}
