package com.github.florent37.xebia.task;

import com.github.florent37.xebia.model.Book;
import com.github.florent37.xebia.webservice.HenriPotierService;
import com.github.florent37.xebia.webservice.HenriPotierWebService;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by florentchampigny on 27/04/15.
 * A task to simplify webservice call
 */
public class GetBooksTask {

    //callback to implement from caller
    public interface GetBooksTaskCallBack{
        public void onBooksReceived(List<Book> bookList);
        public void onNetworkError();
    }

    private GetBooksTaskCallBack callBack;

    public GetBooksTask(GetBooksTaskCallBack callBack){
        this.callBack = callBack;
    }

    public void execute(){
        //use retrofit service
        HenriPotierWebService.getService().getBooks(new Callback<List<Book>>() {
            @Override
            public void success(List<Book> books, Response response) {
                if(callBack != null && books != null)
                    callBack.onBooksReceived(books);
            }

            @Override
            public void failure(RetrofitError error) {
                if(callBack != null)
                    callBack.onNetworkError();
            }
        });
    }

}
