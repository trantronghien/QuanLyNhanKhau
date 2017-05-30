package com.it.hientran.tracuunhankhau.util;

import android.util.Log;

import com.it.hientran.tracuunhankhau.activity.MyApplication;
import com.it.hientran.tracuunhankhau.object.HistorySeen;
import com.it.hientran.tracuunhankhau.object.HistorySeenDao;
import com.it.hientran.tracuunhankhau.object.NguoiDan;
import com.it.hientran.tracuunhankhau.object.NguoiDanDao;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 5/27/2017.
 */

public class HistorySeenUtil {
    private final String TAG = "HistorySeenUtil";
    public HistorySeenUtil(){}

    public void insertItemSeen(NguoiDan nguoiDan , Date dataSeen){
        try {

            HistorySeen historySeen = new HistorySeen();
                historySeen.setClearName(nguoiDan.getBidanh());
                historySeen.setMaHd(nguoiDan.getMaHd());
                historySeen.setIdChuHo(nguoiDan.getIdChuHo());
                historySeen.setDateSeen(dataSeen);
                if (isExistLogPage(nguoiDan.getMaHd()) == true){
                    Log.i(TAG , "insertItemSeen already exists record --> not insert " + nguoiDan.getMaHd());
                }else {
                    Log.i(TAG , "insertItemSeen doesn't exist record  inserting....");
                    MyApplication.getDaoSession()
                            .getHistorySeenDao()
                            .insert(historySeen);
                }
        }catch (Exception e){
            Log.e(TAG , "insertItemSeen Lỗi insert \t" + e.getMessage());
        }
    }

    public boolean isExistLogPage(long MaHd){
        long count = MyApplication.getDaoSession()
                .getHistorySeenDao().queryBuilder()
                .where(HistorySeenDao.Properties.MaHd.eq(MaHd))
                .count();
        return count != 0;
    }

    /**
     * get ALL History Seen Table
     * @return
     * @throws Exception
     */
    public List<HistorySeen> getDataFromDb() throws Exception {
        List<HistorySeen> list = MyApplication.getDaoSession().getHistorySeenDao()
                    .queryBuilder()
                    .list();
        return list;
    }
    // get list HistorySeen using show on list

    public boolean deleteHistory(){
        try{
            MyApplication.getDaoSession().getHistorySeenDao().deleteAll();
            Log.i(TAG, "deleteHistory: delete complete");
            return true;
        }catch (Exception e){
            Log.e(TAG, "deleteHistory: delete error");
            return false;
        }
    }

}
