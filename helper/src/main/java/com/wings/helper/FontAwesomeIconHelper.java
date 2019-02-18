package com.wings.helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Purpose: Set Font Awesome Icons to TextView
 *
 * @author NikunjD
 * Created on February 18, 2019
 * Modified on February 18, 2019
 */
public class FontAwesomeIconHelper extends android.support.v7.widget.AppCompatTextView {
    private Context context;
    private static final String FONT_AWESOME_TTF_FILE_PATH = "fonts/font_awesome-font-v5.3.1.1.ttf";

    /**
     * Assign context Font Awesome Helper Class
     *
     * @param context Context
     */
    public FontAwesomeIconHelper(Context context) {
        super(context);
        this.context = context;
    }

    /**
     * Set Font Awesome Icon in your View
     *
     * @param textView Text View
     * @param icon     Icon as a string from string.xml
     */
    public void setFontAwesomeIcon(TextView textView, String icon) {
        if (icon != null && icon.trim().length() > 0) {
            textView.setText(icon);
        }
        AssetManager assetManager = context.getAssets();
        textView.setTypeface(Typeface.createFromAsset(assetManager, FONT_AWESOME_TTF_FILE_PATH));
    }

    /**
     * Set Font Awesome Icon Color
     *
     * @param textView Text View
     * @param color    Color of Text View
     */
    public void setFontAwesomeIconColor(TextView textView, int color) {
        if (textView != null) {
            textView.setTextColor(color);
        }
    }

    /**
     * Set Font Awesome Icon size
     *
     * @param textView Text View instance
     * @param size     Size of Text View must be in sp.
     */
    public void setFontAwesomeIconSize(TextView textView, int size) {
        if (textView != null) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        }
    }
}
