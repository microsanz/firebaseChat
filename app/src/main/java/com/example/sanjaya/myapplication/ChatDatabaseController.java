package com.example.sanjaya.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanjaya on 6/28/2017.
 */

public class ChatDatabaseController extends SQLiteOpenHelper{

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "firebaseChat";

    // Contacts table name
    private final String TABLE_CHAT = "chat";

    // Contacts Table Columns names
    private final String KEY_TO_ID = "toID";
    private final String KEY_FROM_ID = "fromID";
    private final String KEY_MESSAGE = "message";
    private final String KEY_TIME = "time";

    public ChatDatabaseController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CHAT_TABLE = "CREATE TABLE " + TABLE_CHAT + "("
                + KEY_TO_ID + " TEXT,"+ KEY_FROM_ID + " TEXT," + KEY_MESSAGE + " TEXT,"
                + KEY_TIME + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CHAT_TABLE);
        Log.e("asd","wqeqweqeqe");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<ChatModel> getChatFromID(String fromID){
        List<ChatModel> chatModelList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_CHAT + " WHERE " + KEY_FROM_ID +" = "+ fromID + "ORDER BY "+ KEY_TIME +" ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ChatModel contact = new ChatModel();
                contact.setFromID(cursor.getString(0));
                contact.setMessage(cursor.getString(1));
                contact.setTime(cursor.getString(2));
                // Adding contact to list
                chatModelList.add(contact);
            } while (cursor.moveToNext());
        }


        cursor.close();
        return chatModelList;
    }

    public void addChat(String toID,String fromID,String message, String time){

        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(KEY_TO_ID,toID);
        values.put(KEY_FROM_ID,fromID);
        values.put(KEY_MESSAGE,message);
        values.put(KEY_TIME,time);

// Insert the new row, returning the primary key value of the new row
        db.insert(TABLE_CHAT, null, values);
    }
}
