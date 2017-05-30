package com.it.hientran.tracuunhankhau.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.it.hientran.tracuunhankhau.activity.MyApplication;
import com.it.hientran.tracuunhankhau.internet.Config;
import com.it.hientran.tracuunhankhau.internet.DownloadJson;
import com.it.hientran.tracuunhankhau.object.*;

import org.greenrobot.greendao.query.Query;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by admin on 5/17/2017.
 */

public class NguoiDanUtil {
    private AppCompatActivity context;
    private final String TAG = "NguoiDanUtil";
    public NguoiDanUtil(){}
    public NguoiDanUtil(AppCompatActivity context){
        this.context = context;
    }

    /**
     * get data form server and limit default is 100
     * @param url    link server
     * @return        List NguoiDan
     */
    public List<NguoiDan> getDataFormServer(String url){
        String json ;
        try {
            json = new DownloadJson(context).execute(url).get();
            Gson gson = new Gson();
            Type type = new TypeToken<List<NguoiDan>>() {}.getType();
            List<NguoiDan> listNguoiDan = gson.fromJson(json, type);
            Log.i("NguoiDanUtil" , "getDataFormServer: " + listNguoiDan.size());
            return listNguoiDan;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<NguoiDan> getDataFormJson(String json){
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<List<NguoiDan>>() {}.getType();
            List<NguoiDan> listNguoiDan = gson.fromJson(json, type);
            Log.i("NguoiDanUtil" , "getDataFormServer: " + listNguoiDan.size());
            return listNguoiDan;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get list data nguoi  dan follow page param from DB
     * @param limit    limit number page
     * @param page     filter follow page condition
     * @return   List NguoiDan
     * @throws Exception
     */
    public List<NguoiDan> getDataFromDbFollowPage(int limit , int page) throws Exception {
        List<NguoiDan> list = new ArrayList<>();
        List<String> mahdList = new LogSystemUtil().getAllMaHdAtPage(String.valueOf(page));
        for (String item: mahdList ) {
            NguoiDan nguoiDan = MyApplication.getPeopleDao()
                    .queryBuilder()
                    .limit(limit)
                    .where(NguoiDanDao.Properties.MaHd.eq(item))
                    .unique();
            list.add(nguoiDan);
        }
        return list;
    }

    /**
     * Before being inserted page record (numberPage) must check numberPage does't exist in record LogSystem
     * insert data in table NGUOI_DAN when was json from server
     * @param json      json data
     * @param numberPage current number
     * @return   total row inserted
     */
    public int insertDataFromJson(String json ,int numberPage){
        int count = 0;
        LogSystemUtil logSystemUtil = new LogSystemUtil();
        boolean checkExist = logSystemUtil.isExistLogPage(numberPage);
        if (checkExist == true){
            Log.i(TAG , "insertDataFromJson already exists record --> not insert "  + numberPage);
            return 0;
        }
        try {
            Log.i(TAG , "insertDataFromJson doesn't exist record  inserting.... "  + numberPage);
            Gson gson = new Gson();
            Type type = new TypeToken<List<NguoiDan>>() {}.getType();
//            List<NguoiDan> listNguoiDan = gson.fromJson(json, type);
            Collection<NguoiDan> listNguoiDan = gson.fromJson(json, type);
            for (NguoiDan item : listNguoiDan) {
                MyApplication.getPeopleDao().insert(item);
                long MaHd = item.getMaHd();
                logSystemUtil.insertRecord(numberPage ,MaHd);
                count++;
            }
        }catch (Exception e){
            Log.e(TAG , "insertDataFromJson Lỗi insert \t" + e.getMessage());
        }
        return count;
    }

    /**
     * Before being insert page record (numberPage) must check numberPage does't exist in record LogSystem
     * insert data form list type NgưoiDan from when numberPage Not in table LogSystem
     * @param listNguoiDan   list to insert
     * @param numberPage      numberPage using is exist in record LogSystem
     * @return
     */
    public int insertDataFromList(List<NguoiDan> listNguoiDan ,int numberPage){
        int count = 0;
        LogSystemUtil logSystemUtil = new LogSystemUtil();
        boolean checkExist = logSystemUtil.isExistLogPage(numberPage);
        if (checkExist == true){
            Log.i(TAG , "insertDataFromList already exists record --> not insert " + numberPage);
            return 0;
        }
        try {
            Log.i(TAG , "insertDataFromList doesn't exist record  inserting...." + numberPage);
            for (NguoiDan item : listNguoiDan) {
                MyApplication.getPeopleDao().insert(item);
                long MaHd = item.getMaHd();
                logSystemUtil.insertRecord(numberPage ,MaHd);
                count++;
            }
        }catch (Exception e){
            Log.e(TAG , "insertDataFromList Lỗi insert \t" + e.getMessage());
        }
        return count;
    }

    /**
     * @return the total lines in the table
     */
    public int countRowInTableNgDan(){
        return (int)MyApplication
                .getPeopleDao().count();
    }

    /**
     * @return true if Table Null
     */
    public boolean isEmptyTableNgDan(){
        int record = new NguoiDanUtil().countRowInTableNgDan();
        if (record != 0){
            return false;
        }
        Log.i(TAG , "isEmptyTableNgDan: total row Table NgDan " + record);
        return true;
    }

    /**
     * get data form db follow String query
     * @param query   String query
     * @return list type NguoiDan
     */
    public List<NguoiDan> getDataFollowQuery(String query , AppCompatActivity activity){
        List<NguoiDan> list;
        try{
            MyApplication.getPeopleDao().queryBuilder().LOG_VALUES = true;
            MyApplication.getPeopleDao().queryBuilder().LOG_SQL = true;
            Query<NguoiDan> queryNguoiDan = MyApplication
                    .getPeopleDao()
                    .queryBuilder()
                    .whereOr(NguoiDanDao.Properties.Bidanh.like("%"+query)
                                    , NguoiDanDao.Properties.Bidanh.like("%"+query +"%")
                                    , NguoiDanDao.Properties.Bidanh.like(query + "%")
                                    ,NguoiDanDao.Properties.Hoten.like("%"+query)
                                    , NguoiDanDao.Properties.Hoten.like("%"+query +"%")
                                    , NguoiDanDao.Properties.Hoten.like(query + "%")
                    )
                    .build();
            list = queryNguoiDan.list();
            // search online
            if (list == null){
                String urlQuery = Config.convertToRequestSearch(Config.JSON_FORMAT ,query ,Config.SEARCH_METHOD_CONTAINS_WITH ,1);
                String json = new DownloadJson(activity).execute(urlQuery).get();
                list = getDataFormJson(json);
                Toast.makeText(activity, "Search online...", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            return null;
        }
        Log.i(TAG, "getDataFollowQuery: list got " + list.size());
        return list;
    }

    /**
     * get list all NguoiDan have MaHd Match with MaHd of list History Seen
     * @param list HistorySeen
     * @return list NguoiDan
     */
    public List<NguoiDan> getDataFollowMaHdHisSeen(List<HistorySeen> list){
        List<NguoiDan> listPeople = new ArrayList<>();
        NguoiDan nguoiDan ;
        for (HistorySeen item: list) {
           nguoiDan =  MyApplication
                   .getPeopleDao()
                   .queryBuilder()
                   .where(NguoiDanDao.Properties.MaHd.eq(item.getMaHd()))
                   .unique();
            listPeople.add(nguoiDan);
        }
        return listPeople;
    }
}
