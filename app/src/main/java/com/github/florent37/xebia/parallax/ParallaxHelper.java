package com.github.florent37.xebia.parallax;

import android.support.v4.view.ViewCompat;
import android.view.View;

import com.github.florent37.xebia.R;

import static com.github.florent37.xebia.utils.Utils.colorWithAlpha;
import static com.github.florent37.xebia.utils.Utils.dpToPx;

/**
 * Created by florentchampigny on 27/04/15.
 */
public class ParallaxHelper {

    final float finalTitleY;
    final float finalTitleX;
    final float originalTitleY;
    final float originalTitleX;

    final float maxScroll;
    final float finalScale = 0.6f;

    final int color;
    final int toolbarElevation;

    private View header;
    private View logo;
    private View toolbar;

    public ParallaxHelper(View toolbar, View header, View logo){
        this.logo = logo;
        this.header = header;
        this.toolbar = toolbar;

        color = logo.getContext().getResources().getColor(R.color.colorPrimary);

        maxScroll = logo.getContext().getResources().getDimension(R.dimen.parallaxHeaderHeight);

        toolbarElevation = (int) dpToPx(4,logo.getContext());

        this.finalTitleY = dpToPx(0f, logo.getContext());
        this.finalTitleX = dpToPx(15f, logo.getContext());
        this.originalTitleX = logo.getX();
        this.originalTitleY = logo.getY();
    }

    public void onScroll(int yOffset){
        this.header.setTranslationY(-yOffset/1.5f);

        float percent = yOffset/maxScroll;
        if(percent <= 1) {
            logo.setTranslationY((finalTitleY - originalTitleY) * percent);
            logo.setTranslationX((finalTitleX - originalTitleX) * percent);

            float scale = (1-percent)*(1-finalScale) + finalScale;

            logo.setScaleX(scale);
            logo.setScaleY(scale);

            toolbar.setBackgroundColor(colorWithAlpha(color,percent));
            if(percent == 1) {
                ViewCompat.setElevation(toolbar, toolbarElevation);
            }else{
                ViewCompat.setElevation(toolbar, 0);
            }
        }
    }

}
