package com.github.florent37.xebia.wear;

import com.github.florent37.Emmet;
import com.github.florent37.EmmetWearableListenerService;
import com.github.florent37.davinci.daemon.DaVinciDaemon;
import com.github.florent37.xebia.model.Book;
import com.github.florent37.xebia.task.GetBooksTask;
import com.github.florent37.xebia.wearprotocol.WearBook;
import com.github.florent37.xebia.wearprotocol.WearProtocol;
import com.github.florent37.xebia.wearprotocol.WearProtocolSmartphone;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florentchampigny on 27/04/15.
 */
public class WearService extends EmmetWearableListenerService implements WearProtocolSmartphone, GetBooksTask.GetBooksTaskCallBack {

    WearProtocol sender;

    @Override
    public void onCreate() {
        super.onCreate();

        Emmet emmet = getEmmet();
        emmet.registerReceiver(WearProtocolSmartphone.class,this);
        sender = emmet.createSender(WearProtocol.class);
    }

    @Override
    public void onConnected() {
        new GetBooksTask(this).execute();
    }

    @Override
    public void onBooksReceived(List<Book> bookList) {

        //prepare models for wear
        List<WearBook> wearBooks = new ArrayList<>();
        for(Book book : bookList){
            wearBooks.add(new WearBook(book.getIsbn(),book.getTitle(),book.getPrice(),book.getCover()));
        }

        //send it to wear
        if(sender != null)
            sender.onBooksLoaded(wearBooks);
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        DaVinciDaemon.with(getApplicationContext()).handleMessage(messageEvent);
        super.onMessageReceived(messageEvent);
    }

    @Override
    public void onNetworkError() {

    }
}