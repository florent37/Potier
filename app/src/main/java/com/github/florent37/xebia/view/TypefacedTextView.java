package com.github.florent37.xebia.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.util.LruCache;
import android.util.AttributeSet;
import android.widget.TextView;

import com.github.florent37.xebia.R;


public class TypefacedTextView extends TextView {

    /**
     * An <code>LruCache</code> for previously loaded typefaces.
     */
    private static LruCache<String, Typeface> sTypefaceCache =
            new LruCache<String, Typeface>(12);

    public TypefacedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Typeface.createFromAsset doesn't work in the layout editor. Skipping...
        if (isInEditMode()) {
            return;
        }

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TypefacedTextView);
        try {

            boolean allCaps = styledAttrs.getBoolean(R.styleable.TypefacedTextView_allCaps, false);
            allCaps(allCaps);

            String fontName = styledAttrs.getString(R.styleable.TypefacedTextView_typeface);

            Typeface typeface = sTypefaceCache.get(fontName);

            if (typeface == null) {
                typeface = Typeface.createFromAsset(getContext().getAssets(), fontName);

                // Cache the Typeface object
                sTypefaceCache.put(fontName, typeface);
            }
            setTypeface(typeface);

            // Note: This flag is required for proper typeface rendering
            setPaintFlags(getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }finally {
            styledAttrs.recycle();
        }

    }

    public void allCaps(boolean upperCase) {
        if (upperCase)
            this.setText(this.getText().toString().toUpperCase());
    }
}