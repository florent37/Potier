package com.github.florent37.xebia;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;
import android.util.Log;

import com.github.florent37.Emmet;
import com.github.florent37.davinci.BitmapCallBack;
import com.github.florent37.davinci.DaVinci;
import com.github.florent37.xebia.wearprotocol.WearBook;
import com.github.florent37.xebia.wearprotocol.WearProtocol;
import com.github.florent37.xebia.wearprotocol.WearProtocolSmartphone;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

public class MainActivity extends Activity implements WearProtocol {

    private final static String TAG = MainActivity.class.getCanonicalName();

    private GridViewPager pager;
    private DotsPageIndicator dotsPageIndicator;

    private List<WearBook> bookList;

    private Emmet emmet = new Emmet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emmet.onCreate(this);

        pager = (GridViewPager) findViewById(R.id.pager);
        dotsPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        dotsPageIndicator.setPager(pager);

        WearProtocolSmartphone sender = emmet.createSender(WearProtocolSmartphone.class);
        sender.onConnected();

        emmet.registerReceiver(WearProtocol.class, MainActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        emmet.onDestroy();
    }

    public void startMainScreen() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (pager != null && pager.getAdapter() == null)
                    pager.setAdapter(new BookGridPagerAdapter(MainActivity.this, bookList, getFragmentManager()));
            }
        });
    }

    @Override
    public void onBooksLoaded(List<WearBook> wearBookList) {
        this.bookList = wearBookList;
        startMainScreen();
    }

}
