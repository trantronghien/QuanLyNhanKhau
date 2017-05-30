package com.it.hientran.tracuunhankhau.activity;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract;

import com.it.hientran.tracuunhankhau.object.DaoMaster;
import com.it.hientran.tracuunhankhau.object.DaoSession;
import com.it.hientran.tracuunhankhau.object.NguoiDanDao;

/**
 * Created by admin on 5/21/2017.
 */

public class MyApplication extends Application {

    public static final String SQL_DB_NAME = "People-db";
    private static NguoiDanDao peopleDao;
    private static DaoSession daoSession;
    private static SQLiteDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, SQL_DB_NAME , null);
        SQLiteDatabase db = helper.getWritableDatabase();
        setDataBase(db);
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        // do this in your activities/fragments to get hold of a DAO
        peopleDao = daoSession.getNguoiDanDao();
    }

    private static void setDataBase(SQLiteDatabase db){
        database = db;
    }
    public static SQLiteDatabase getDatabase() {
        return database;
    }

    public static NguoiDanDao getPeopleDao() {
        return peopleDao;
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public static void clearDatabase(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(
                context.getApplicationContext(),SQL_DB_NAME , null);
        SQLiteDatabase db = devOpenHelper.getWritableDatabase();
        devOpenHelper.onUpgrade(db,0,0);
    }

}
