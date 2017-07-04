package com.example.sanjaya.myapplication;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sanjaya on 7/3/2017.
 */

public abstract class ActivityHandler extends Activity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences= this.getSharedPreferences("chatPref", Context.MODE_PRIVATE);
    }

    @Override
    protected void onPause() {
        editor= sharedPreferences.edit();
        editor.putBoolean("notifChat",true);
        editor.apply();
        super.onPause();
    }
    protected boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
