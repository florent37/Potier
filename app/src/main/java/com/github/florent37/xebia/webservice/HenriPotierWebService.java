package com.github.florent37.xebia.webservice;

import android.util.Log;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by florentchampigny on 27/04/15.
 */
public class HenriPotierWebService {

    public static final String TAG = HenriPotierWebService.class.getSimpleName();

    /**
     * Return a HenriPotierService implementation from Retrofit
     * @return
     */
    public static HenriPotierService getService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(HenriPotierService.ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        Log.d(TAG, message);
                    }
                })
                .build();

        return restAdapter.create(HenriPotierService.class);
    }

}
