package com.github.florent37.xebia.webservice;

import com.github.florent37.xebia.model.Book;
import com.github.florent37.xebia.model.OfferContainer;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by florentchampigny on 27/04/15.
 * Henri Potier Xebia Webservice
 */
public interface HenriPotierService {

    @GET("/books")
    void getBooks(Callback<List<Book>> callback);

    @GET("/books/{isbns}/commercialOffers")
    void getCommercialOffers(@Path("isbns") String isbns, Callback<OfferContainer> callback);

}
