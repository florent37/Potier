package com.github.florent37.xebia;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;

import com.github.florent37.emmet.Emmet;
import com.github.florent37.xebia.wearprotocol.WearBook;
import com.github.florent37.xebia.wearprotocol.WearProtocol;
import com.github.florent37.xebia.wearprotocol.WearProtocolSmartphone;

import java.util.List;

/**
 * Wearable MainActivity
 */
public class MainActivity extends Activity implements WearProtocol {

    private final static String TAG = MainActivity.class.getCanonicalName();

    private GridViewPager pager;
    private DotsPageIndicator dotsPageIndicator;

    private List<WearBook> bookList;

    /**
     * Used to simplify communication betweet wear-smartphone
     */
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

    //sent by smartphone
    @Override
    public void onBooksLoaded(List<WearBook> wearBookList) {
        this.bookList = wearBookList;
        startMainScreen();
    }

}
