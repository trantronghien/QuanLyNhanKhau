package com.it.hientran.tracuunhankhau.util;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.it.hientran.tracuunhankhau.activity.MainActivity;

/**
 * Created by admin on 5/28/2017.
 */

public class IntentUtil {

    public static final String FLAG_KEY = "flag";
    public static void WelComeTransitiveMain(AppCompatActivity activity){
        Intent i = new Intent(activity, MainActivity.class);
        i.putExtra(FLAG_KEY, true);
        activity.startActivity(i);
        activity.finish();
    }

}
