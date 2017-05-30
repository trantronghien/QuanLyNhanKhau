package com.it.hientran.tracuunhankhau.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.it.hientran.tracuunhankhau.R;
import com.it.hientran.tracuunhankhau.broadcast.ConnectivityService;
import com.it.hientran.tracuunhankhau.internet.PrefetchData;
import com.it.hientran.tracuunhankhau.internet.Config;
import com.it.hientran.tracuunhankhau.util.IntentUtil;
import com.it.hientran.tracuunhankhau.util.SharedPreferencesUtil;

import static com.it.hientran.tracuunhankhau.internet.Config.JSON_FORMAT;
import static com.it.hientran.tracuunhankhau.internet.Config.LIST_METHOD;


public class WelcomeActivity extends AppCompatActivity implements ConnectivityService.ConectivityListener {

    private final String TAG = "WelcomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = this.getWindow().getDecorView();
        setContentView(R.layout.activity_welcome);
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        // khởi tạo dữ liệu khi load data
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(this);

        int page = sharedPreferencesUtil.getIntPageCurrent();
        boolean inItData = sharedPreferencesUtil.isInitFirstData();
        if (page == 1 && inItData == false){
            sharedPreferencesUtil.savePageCurrent(page);
            sharedPreferencesUtil.saveStatusInitFirstData(true);
            new PrefetchData(this).execute(Config.convertToRequestGetList(JSON_FORMAT , LIST_METHOD , page , TAG));
        }else{
            IntentUtil.WelComeTransitiveMain(this);
        }
//      ConnectivityService connectivityService = new ConnectivityService(this.getPackageName().getClass().getName());
//      connectivityService.setOnConectivityListener(this);
    }

    @Override
    public void onConnectivity(boolean isNetworkConnected, boolean isInternetAvailable) {
        if (!isNetworkConnected){
            Toast.makeText(this, "Chưa kết nối đến internet", Toast.LENGTH_SHORT).show();
        }
//      else if(!isInternetAvailable){
//            Toast.makeText(this, "Internet có vấn đề", Toast.LENGTH_SHORT).show();
//      }
        else{
            Toast.makeText(this, "Internet Khả dụng", Toast.LENGTH_SHORT).show();
            new PrefetchData(this).execute(Config.HOST_NAME_SERVER);
        }
    }
}
