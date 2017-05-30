package com.it.hientran.tracuunhankhau.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.it.hientran.tracuunhankhau.activity.MyApplication;
import com.it.hientran.tracuunhankhau.object.LogSystem;
import com.it.hientran.tracuunhankhau.object.LogSystemDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 5/29/2017.
 */

public class LogSystemUtil {

    private static final String TAG = "LogSystemUtil";

    /**
     * get data form Table LogSystem
     * @param page
     * @return
     * @throws Exception
     */
    public List<String> getAllMaHdAtPage(String page) throws Exception{
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = MyApplication.getDatabase();
        String[] projection = {
                LogSystemDao.Properties.MaHd.columnName,
//                LogSystemDao.Properties.NumberPage.columnName,
        };
        String selection = LogSystemDao.Properties.NumberPage.columnName + " = ?";
        String[] selectionArgs = { page };
        String sortOrder = LogSystemDao.Properties.MaHd.columnName + " DESC";

        Cursor cursor =  db.query(
                LogSystemDao.TABLENAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        while(cursor.moveToNext()) {
            String itemMaHd = cursor.getString(
                    cursor.getColumnIndexOrThrow(LogSystemDao.Properties.MaHd.columnName));
            list.add(itemMaHd);
        }
        return list;
    }

    /**
     * insert record number Page form MaHd id
     * @param numberPage record page
     * @param MaHd        _id of a row in table NGUOI_DAN
     */
    public void insertRecord(int numberPage , long MaHd){
        MyApplication.getDaoSession()
                .getLogSystemDao()
                .insert(new LogSystem(null ,numberPage , MaHd));  // bản ghi số trang
    }

    /**
     * check numberPage param is Exists in table LOG_SYSTEM
     * @param numberPage   is check
     * @return  false if does not exists
     */
    public boolean isExistLogPage(int numberPage){
        long count = MyApplication.getDaoSession()
                .getLogSystemDao().queryBuilder()
                .where(LogSystemDao.Properties.NumberPage.eq(numberPage))
                .count();
        return count != 0;
    }
}
