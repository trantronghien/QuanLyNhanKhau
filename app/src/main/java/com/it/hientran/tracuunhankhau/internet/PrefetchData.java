package com.it.hientran.tracuunhankhau.internet;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.it.hientran.tracuunhankhau.R;
import com.it.hientran.tracuunhankhau.util.IntentUtil;
import com.it.hientran.tracuunhankhau.util.NguoiDanUtil;

import java.io.IOException;

/**
 * Created by admin on 5/22/2017.
 */

public class PrefetchData extends AsyncTask<String, Void, Void> {

    private AppCompatActivity activity;
    public ProgressDialog myDialog;

    private final String TAG = "PrefetchData";
    private int pageCurrent = 1;

    public PrefetchData(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        myDialog = new ProgressDialog(activity);
        myDialog.setMessage(activity.getString(R.string.string_mess_loading));
        myDialog.show();
    }

    // download and insert into database
    @Override
    protected Void doInBackground(String... url) {
        try {
            NguoiDanUtil nguoiDanUtil =new NguoiDanUtil();
            if (nguoiDanUtil.isEmptyTableNgDan() == true){
                String json = RequestServer.getJsonFromServer(url[0]);
                nguoiDanUtil.insertDataFromJson(json , getPageCurrent());
            }

            else
                return null;
        } catch (IOException e) {
            Log.e(TAG , "doInBackground: server hoặc internet có thể có chuyện rồi");
            e.printStackTrace();
        }catch (Exception e){
            Log.e(TAG , "doInBackground: Có thể insert dữ liệu lỗi");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        try {
            IntentUtil.WelComeTransitiveMain(activity);
            myDialog.dismiss();
        } catch (Exception e) {
            Log.e("PrefetchData", "onPostExecute: " + e.getCause().getMessage());
        }
    }

    public int getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(int pageCurrent) {
        this.pageCurrent = pageCurrent;
    }


}
