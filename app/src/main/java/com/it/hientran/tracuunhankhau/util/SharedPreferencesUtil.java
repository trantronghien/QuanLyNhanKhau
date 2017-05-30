package com.it.hientran.tracuunhankhau.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by admin on 5/27/2017.
 */

public class SharedPreferencesUtil {
    public final String NAME_SHAREDPRE = "page_current";
    public final String KEY_PAGE_SHAREDPRE = "value_page";
    public final String KEY_INIT_SHAREDPRE = "init";
    private Context context;
    public static final int FIRSTINITPAGE = 1;
    public static final int NUMBER_PAGE = 175;   // với limit 100
    private int pageCurrent;
    private boolean isInIt = false;    // chưa khởi tạo

    public SharedPreferencesUtil(Context context){
        this.context = context;
    }

    public void savePageCurrent(int pageValue) {
        SharedPreferences pre = context.getSharedPreferences(NAME_SHAREDPRE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        if (pageValue < FIRSTINITPAGE)
            pageValue =  FIRSTINITPAGE;
        else if (  pageValue > NUMBER_PAGE)
            pageValue = NUMBER_PAGE;
        editor.putInt(KEY_PAGE_SHAREDPRE, pageValue);
        editor.commit();
    }

    public void saveStatusInitFirstData(boolean inIt) {
        SharedPreferences pre = context.getSharedPreferences(NAME_SHAREDPRE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putBoolean(KEY_INIT_SHAREDPRE, inIt);
        editor.commit();
    }

    public boolean isInitFirstData(){
        SharedPreferences pre = context.getSharedPreferences(NAME_SHAREDPRE, MODE_PRIVATE);
        return pre.getBoolean(KEY_INIT_SHAREDPRE , isInIt);
    }

    public int getIntPageCurrent(){
        SharedPreferences pre = context.getSharedPreferences(NAME_SHAREDPRE, MODE_PRIVATE);
        return pre.getInt(KEY_PAGE_SHAREDPRE , FIRSTINITPAGE);
    }

    public boolean deleteSaved(){
        try {
            SharedPreferences preferences = context.getSharedPreferences(NAME_SHAREDPRE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public int getFirstInitPage() {
        return FIRSTINITPAGE;
    }

    public void setPageCurrent(int pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public int getPageCurrent() {
        return pageCurrent;
    }
}
