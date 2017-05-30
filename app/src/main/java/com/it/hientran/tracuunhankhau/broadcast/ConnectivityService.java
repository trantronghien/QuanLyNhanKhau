package com.it.hientran.tracuunhankhau.broadcast;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * Created by admin on 5/23/2017.
 */

public class ConnectivityService extends IntentService {
    ConectivityListener conectivityListener;
    public static final String TAG = "ConnectivityService";
    public ConnectivityService(String name) {
        super(name);

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Bundle extras = intent.getExtras();
        boolean isNetworkConnected = extras.getBoolean(ConnectivityChangeReceiver.NETWORK_AVAILABLE);
        boolean isInternetAvailable = extras.getBoolean(ConnectivityChangeReceiver.INTERNET_AVAILABLE);
        if (conectivityListener != null){
            conectivityListener.onConnectivity(isNetworkConnected ,isInternetAvailable );
        }
        Log.i(TAG , "onHandleIntent Service " );
    }

    public void setOnConectivityListener(ConectivityListener conectivityListener){
        this.conectivityListener = conectivityListener;
    }

    public interface ConectivityListener{
        void onConnectivity(boolean isNetworkConnected , boolean isInternetAvailable);
    }
}
