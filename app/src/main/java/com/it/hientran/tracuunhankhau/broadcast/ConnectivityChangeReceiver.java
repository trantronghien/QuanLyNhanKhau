package com.it.hientran.tracuunhankhau.broadcast;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by admin on 5/23/2017.
 */

public class ConnectivityChangeReceiver extends BroadcastReceiver {
    private final String TAG = "ConnectivityChangeReceiver";
    private Context context;
    public static final String NETWORK_AVAILABLE = "isNetworkAvailable";
    public static final String INTERNET_AVAILABLE = "isInternetAvailable";

    @SuppressLint("LongLogTag")
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        ComponentName comp = new ComponentName(context.getPackageName(),
                ConnectivityService.class.getName());
        intent.putExtra(NETWORK_AVAILABLE , isNetworkAvailable());
        intent.putExtra(INTERNET_AVAILABLE , isInternetAvailable());
        context.startService((intent.setComponent(comp)));
        Log.i(TAG , "ConnectivityChangeReceiver start");
    }

    /**
     * đảm bảo rằng thiết bị đã đc kết nối
     * @return
     */
    public boolean isNetworkAvailable() {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    /**
     * đảm bảo rằng kết nối đc với internet
     * @return
     */

    @SuppressLint("LongLogTag")
    public boolean isInternetAvailable() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            final InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            Log.e(TAG , e.getMessage());
        }catch (NetworkOnMainThreadException e){
            Log.e(TAG , e.getMessage());
        }
        return false;
    }
}
