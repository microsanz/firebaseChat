package com.example.sanjaya.myapplication;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sanjaya on 7/1/2017.
 */

public class Utils {
    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }

}