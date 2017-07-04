package com.example.sanjaya.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActivityHandler {

    EditText yourID;
    Button submitID;
    ChatSettings chatSettings;


    List<String> listRoom= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listRoom.add("Room 1");
        listRoom.add("Room 2");
        listRoom.add("Room 3");
        listRoom.add("Room 4");
        listRoom.add("Room 5");

        yourID = (EditText)findViewById(R.id.yourID);
        submitID = (Button)findViewById(R.id.submitID);
//        targetID = (EditText)findViewById(R.id.targetID);

        if (!isMyServiceRunning(ChatService.class)) {
            Intent intent = new Intent(this, ChatService.class);
            startService(intent);
        }

        submitID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getBaseContext(),ChatActivity.class);
                intent.putExtra("yourID",yourID.getText().toString());
//                intent.putExtra("targetID",targetID.getText().toString());
                startActivity(intent);
            }
        });
        chatSettings.getRoomList(listRoom);
    }


    public interface ChatSettings {
        void getRoomList(List<String> roomList);
    }

}
