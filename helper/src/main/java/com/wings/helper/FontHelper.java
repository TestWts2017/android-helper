package com.wings.helper;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Purpose: Replace Font with selected fonts based on parent view and all child view
 * Usage:
 * For Activity:
 * -   FontHelper fontChanger = new FontHelper(getAssets(), "font.otf");
 * -   fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));
 * For Fragment:
 * Inside OnActivity Created
 * -    FontHelper fontChanger = new FontHelper(getAssets(), "font.otf");
 * -    fontChanger.replaceFonts((ViewGroup) this.getView());
 * For Adapter:
 * -    if(convertView == null){
 * -    convertView = inflater.inflate(R.layout.list_item, null);
 * -    fontChanger.replaceFonts((ViewGroup)convertView);
 * }
 *
 * @author NikunjD
 * Created on February 13, 2019
 * Modified on February 14, 2019
 */

public class FontHelper {
    private Typeface typeface;

    /**
     * Set typeface
     *
     * @param typeface Font Typeface
     */
    public FontHelper(Typeface typeface) {
        this.typeface = typeface;
    }

    /**
     * Get typeface from asset and file name
     *
     * @param assets             Assets
     * @param assetsFontFileName Asset font file name (Fonts link of assets)
     */
    public FontHelper(AssetManager assets, String assetsFontFileName) {
        typeface = Typeface.createFromAsset(assets, assetsFontFileName);
    }

    /**
     * Replace font in every child as provided typeface
     *
     * @param viewTree ViewGroup
     */
    public void replaceFonts(ViewGroup viewTree) {
        View child;
        for (int i = 0; i < viewTree.getChildCount(); ++i) {
            child = viewTree.getChildAt(i);
            if (child instanceof ViewGroup) {
                // recursive call
                replaceFonts((ViewGroup) child);
            } else if (child instanceof TextView) {
                // base case
                int style = Typeface.NORMAL;
                if (((TextView) child).getTypeface() != null) {
                    if (((TextView) child).getTypeface().getStyle() == Typeface.BOLD) {
                        //do your stuff
                        style = Typeface.BOLD;
                    }
                }
                ((TextView) child).setTypeface(typeface, style);
            }
        }
    }
}
