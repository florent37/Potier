package com.github.florent37.xebia.webservice;

import com.github.florent37.xebia.model.Book;
import com.github.florent37.xebia.model.OfferEnvelope;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by florentchampigny on 27/04/15.
 */
public interface HenriPotierService {

    public static final String ENDPOINT = "http://henri-potier.xebia.fr";

    @GET("/books")
    public void getBooks(Callback<List<Book>> callback);

    @GET("/books/{isbns}/commercialOffers")
    public void getCommercialOffers(@Path("isbns") String isbns, Callback<OfferEnvelope> callback);

}
