package com.randomappsinc.bro.Utils;

import android.app.Application;
import android.content.Context;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.IoniconsModule;

/**
 * Created by Alex Chiou on 1/28/16.
 */
public class MyApplication extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Iconify.with(new IoniconsModule());
    }

    public static Context getAppContext() {
        return context;
    }
}
