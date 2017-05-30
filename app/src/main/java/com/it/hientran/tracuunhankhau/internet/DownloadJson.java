package com.it.hientran.tracuunhankhau.internet;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.it.hientran.tracuunhankhau.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by admin on 5/17/2017.
 */

public class DownloadJson extends AsyncTask<String , Void , String> {

    AppCompatActivity activity;
    public ProgressDialog myDialog;
    private final String TAG = "DownloadJson";
    public DownloadJson(AppCompatActivity activity){
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (activity != null){
            myDialog = new ProgressDialog(activity);
            myDialog.setMessage(activity.getString(R.string.string_mess_loading));
            myDialog.show();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String json = RequestServer.getJsonFromServer(params[0]);
            Log.i("DownloadJson" , json);
            return json;
        } catch (IOException e) {
            Log.e(TAG , "server gặp chuyện rồi");
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (activity != null){
            myDialog.dismiss();
        }
    }

}
