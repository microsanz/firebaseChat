package com.example.sanjaya.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sanjaya on 7/3/2017.
 */

public class CustomActivity extends AppCompatActivity{

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onResume() {
        super.onResume();
        editor= sharedPreferences.edit();
        editor.putString("notifChat","0");
        editor.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        editor= sharedPreferences.edit();
        editor.putString("notifChat","1");
        editor.apply();
    }
}
