package com.example.android_razrab.greendaomanytomany;

import android.app.Application;

import com.facebook.stetho.Stetho;

import org.greenrobot.greendao.database.Database;


/**
 * Created by android_razrab on 12/10/2017.
 */

public class App extends Application {

    DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();


        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "mygreendaodatab.db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        Stetho.initializeWithDefaults(this);
    }

    public DaoSession getDaoSession(){
        return daoSession;

    }
}
