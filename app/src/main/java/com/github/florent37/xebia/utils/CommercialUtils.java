package com.github.florent37.xebia.utils;

import com.github.florent37.xebia.model.Book;
import com.github.florent37.xebia.model.CommercialOffer;
import com.github.florent37.xebia.model.Offer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by florentchampigny on 27/04/15.
 */
public class CommercialUtils {
    public static float getPrice(List<Book> bookList) {
        float price = 0;
        if (bookList != null) {
            int size = bookList.size();
            for (int i = 0; i < size; ++i)
                price += bookList.get(i).getPrice();
        }

        return price;
    }

    public static CommercialOffer getBestOffer(List<Book> bookList, List<Offer> offers) {
        float price = getPrice(bookList);

        List<CommercialOffer> bestOffers = new ArrayList<>();

        for (Offer offer : offers) {
            float thisPrice = 0;
            switch (offer.getType()) {
                case Offer.TYPE_MINUS:
                    thisPrice = price - offer.getValue();
                    break;
                case Offer.TYPE_PERCENTAGE:
                    thisPrice = price * (1.0f-offer.getValue()/100f);
                    break;
                case Offer.TYPE_SLICE:
                    if(offer.getSliceValue() == 0)
                        thisPrice = price;
                    else
                        thisPrice = price - ((int)price/offer.getSliceValue()) * offer.getValue();
                    break;
            }
            bestOffers.add(new CommercialOffer(thisPrice,offer));
        }

        CommercialOffer min = Collections.min(bestOffers);

        return min;
    }

}
