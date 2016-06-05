package com.baizhong.activity;

import android.app.Application;
import android.os.Environment;

import com.activeandroid.ActiveAndroid;
import com.facebook.drawee.backends.pipeline.Fresco;


import java.io.File;

/**
 * Created by Administrator on 2016/5/30.
 */
public class CoustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        Fresco.initialize(getApplicationContext());
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
