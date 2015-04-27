package com.github.florent37.xebia.model;

/**
 * Created by florentchampigny on 27/04/15.
 */
public class CommercialOffer implements Comparable<CommercialOffer>{

    private float price;
    private Offer offer;

    public CommercialOffer(float price, Offer offer) {
        this.price = price;
        this.offer = offer;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    @Override
    public int compareTo(CommercialOffer another) {
        if(getPrice() < another.getPrice())
            return -1;
        else if(getPrice() == another.getPrice())
            return 0;
        else
            return 1;
    }
}