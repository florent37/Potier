package com.github.florent37.xebia.webservice;

import android.util.Log;

import com.github.florent37.xebia.BuildConfig;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by florentchampigny on 27/04/15.
 */
public class HenriPotierWebService {

    public static final String TAG = HenriPotierWebService.class.getSimpleName();

    private static HenriPotierService INSTANCE;

    private HenriPotierWebService(){}

    /**
     * Return a HenriPotierService implementation from Retrofit
     * @return
     */
    public static HenriPotierService getInstance() {

        if(INSTANCE == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(BuildConfig.URL_WEBSERVICES)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setLog(new RestAdapter.Log() {
                        @Override
                        public void log(String message) {
                            Log.d(TAG, message);
                        }
                    })
                    .build();

            INSTANCE =  restAdapter.create(HenriPotierService.class);
        }

        return INSTANCE;
    }

}
