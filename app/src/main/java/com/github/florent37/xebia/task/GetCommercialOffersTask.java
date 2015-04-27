package com.github.florent37.xebia.task;

import com.github.florent37.xebia.model.Book;
import com.github.florent37.xebia.model.Offer;
import com.github.florent37.xebia.model.OfferEnvelope;
import com.github.florent37.xebia.webservice.HenriPotierWebService;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by florentchampigny on 27/04/15.
 */
public class GetCommercialOffersTask {

    public interface GetCommercialOfferTaskCallBack{
        public void onCommercialOfferReceived(List<Offer> offers);
        public void onNetworkError();
    }

    private GetCommercialOfferTaskCallBack callBack;

    public GetCommercialOffersTask(GetCommercialOfferTaskCallBack callBack){
        this.callBack = callBack;
    }

    private String getIsbnsFromBooks(List<Book> books){
        StringBuilder stringBuilder = new StringBuilder();
        int size = books.size();
        for(int i=0;i<size;++i){
            stringBuilder.append(books.get(i).getIsbn());
            if(i<size-1)
                stringBuilder.append(",");
        }
        return stringBuilder.toString();
    }

    public void execute(List<Book> books){
        if(books != null && !books.isEmpty()) {

            String isbns = getIsbnsFromBooks(books);

            HenriPotierWebService.getService().getCommercialOffers(isbns,new Callback<OfferEnvelope>() {
                @Override
                public void success(OfferEnvelope offerEnvelope, Response response) {
                    if (callBack != null && offerEnvelope != null && offerEnvelope.getOffers() != null)
                        callBack.onCommercialOfferReceived(offerEnvelope.getOffers());
                }

                @Override
                public void failure(RetrofitError error) {
                    if (callBack != null)
                        callBack.onNetworkError();
                }
            });
        }
    }

}
