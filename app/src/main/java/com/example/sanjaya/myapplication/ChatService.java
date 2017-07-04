package com.example.sanjaya.myapplication;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanjaya on 7/3/2017.
 */

public class ChatService extends Service implements MainActivity.ChatSettings {
    FirebaseDatabase database;
    String timeStamp = "0";
    Boolean showNotif = false;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    NotificationManager mNotifyMgr;
    NotificationCompat.Builder mBuilder;
    List<String> roomList = new ArrayList<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Utils.getDatabase();
        sharedPreferences = getApplicationContext().getSharedPreferences("chatPref", Context.MODE_PRIVATE);
        mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        database = Utils.getDatabase();
        for (String room:roomList) {
            final DatabaseReference myRefParent = database.getReference("message");
            Log.i("Listening to ", room);
            myRefParent.keepSynced(true);
            final DatabaseReference roomID = myRefParent.child(room);
            Query recent5 = roomID.limitToLast(5);
            Log.d("last 5", recent5.toString());

            recent5.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    timeStamp = sharedPreferences.getString("lastActive", "0");
                    showNotif = sharedPreferences.getBoolean("notifChat", true);
                    String notifChat = "";
                    int notifNo = 100;
                    for (DataSnapshot msgSnapshot : dataSnapshot.getChildren()) {
                        ChatModel value = msgSnapshot.getValue(ChatModel.class);
                        Log.i("Stored Timestamp  ", timeStamp);
                        Log.i("Message Timestamp ", value.getTime());
                        Log.i("Show notif ", showNotif.toString());

                        if (Long.parseLong(timeStamp) < Long.parseLong(value.getTime()) && showNotif) {
                            NotificationCompat.Builder b = new NotificationCompat.Builder(getApplicationContext());
                            b.setContentTitle("My notification")
                                    .setContentText(value.getMessage())
                                    .setSmallIcon(R.drawable.ic_account_box).build();

                            mNotifyMgr.notify(notifNo, b.build());
//                        notifChat=notifChat+value.getMessage()+"\n";
                            notifNo++;
                        }

                    }
                    Log.d("asdasd", "Value is: " + notifChat);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            roomID.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
//                for (DataSnapshot msgSnapshot : dataSnapshot.getChildren()) {
//                    ChatModel value = msgSnapshot.getValue(ChatModel.class);
//                    Toast.makeText(getApplicationContext(),value.getMessage(),Toast.LENGTH_SHORT).show();
//                    Log.d("asdasd", "Value is: " + value.getMessage());
//                }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("asdasd", "Failed to read value.", error.toException());
                }
            });
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Intent intent = new Intent(this, ChatService.class);
        startService(intent);
        super.onDestroy();
    }


    @Override
    public void getRoomList(List<String> roomList) {
        this.roomList=roomList;
    }
}
