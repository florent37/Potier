package com.github.florent37.xebia.pay.recyclerview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.florent37.xebia.R;
import com.github.florent37.xebia.model.CommercialOffer;
import com.github.florent37.xebia.model.Offer;

/**
 * Created by florentchampigny on 27/04/15.
 */
public class PayHeaderViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewPrice;
    private View topFrame;
    private View bottomFrame;
    private TextView textViewReduction;

    private float initialPrice = 0;
    private float finalPrice = 0;

    private int fullHeight = 0;

    public PayHeaderViewHolder(View view) {
        super(view);
        textViewPrice = (TextView) view.findViewById(R.id.textViewPrice);
        topFrame = view.findViewById(R.id.topFrame);
        bottomFrame = view.findViewById(R.id.bottomFrame);
        textViewReduction = (TextView) view.findViewById(R.id.textViewReduction);
    }

    public void setInitialPrice(float price) {
        if(initialPrice == 0) //only first time
            textViewPrice.setText(price + " €");
        if (price != initialPrice) {
            this.initialPrice = price;
        }
    }

    public void setFinalPrice(final CommercialOffer offer) {
        if (offer != null && offer.getOffer() != null && offer.getPrice() != finalPrice && initialPrice != 0) {
            this.finalPrice = offer.getPrice();

            final float percent = finalPrice / initialPrice;

            itemView.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if(fullHeight == 0)
                        fullHeight = topFrame.getHeight();

                    ValueAnimator va = ValueAnimator.ofFloat(fullHeight, fullHeight * percent);
                    va.setDuration(1500);
                    va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            topFrame.getLayoutParams().height = Math.round((float) animation.getAnimatedValue());
                            topFrame.requestLayout();
                        }
                    });
                    va.start();
                    textViewPrice.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            textViewPrice.setText(finalPrice + " €");
                            textViewPrice.animate().alpha(1);
                        }
                    }).start();

                    textViewReduction.setTranslationY(textViewReduction.getHeight() * 1.5f);
                    textViewReduction.setAlpha(0);
                    textViewReduction.setText(getTextReduction(offer.getOffer()));
                    textViewReduction.animate().alpha(1).translationY(0).setDuration(1000).start();
                }
            }, 1000);
        }
    }

    private String getTextReduction(Offer offer) {
        switch (offer.getType()) {
            case Offer.TYPE_SLICE:
                return itemView.getContext().getString(R.string.par_tranche_de,offer.getValue(),offer.getSliceValue());
            case Offer.TYPE_PERCENTAGE:
                return itemView.getContext().getString(R.string.pourcent_de_reduction_immediate,offer.getValue());
            case Offer.TYPE_MINUS:
                return itemView.getContext().getString(R.string.euros_reduction_immediate,(initialPrice - finalPrice));
        }

        return "";
    }

}
