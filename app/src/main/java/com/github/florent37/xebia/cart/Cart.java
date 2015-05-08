package com.github.florent37.xebia.cart;

import android.content.Context;

import com.github.florent37.xebia.model.Book;
import com.github.florent37.xebia.model.CommercialOffer;
import com.github.florent37.xebia.model.Offer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by florentchampigny on 27/04/15.
 */
public class Cart {

    private Context context;
    private CartBookFacade cartBookFacade;

    public Cart(Context context) {
        this.context = context;
    }

    public CartBookFacade getCartBook() {
        if(cartBookFacade == null)
            cartBookFacade = new CartBookFacadeSharedPreferences(context);
        return cartBookFacade;
    }

    public float getPrice() {
        List<Book> bookList = getCartBook().getCommandedBooks();

        return Cart.getPrice(bookList);
    }

    public static float getPrice(List<Book> bookList) {
        float price = 0;
        if (bookList != null) {
            int size = bookList.size();
            for (int i = 0; i < size; ++i)
                price += bookList.get(i).getPrice();
        }

        return price;
    }


    public CommercialOffer getBestOffer(List<Offer> offers) {
        float price = getPrice();

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

        //return the min price
        CommercialOffer min = Collections.min(bestOffers);

        return min;
    }

}
