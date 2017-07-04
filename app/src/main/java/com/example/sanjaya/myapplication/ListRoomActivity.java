package com.example.sanjaya.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanjaya on 7/4/2017.
 */

public class ListRoomActivity extends AppCompatActivity implements MainActivity.ChatSettings{
    RecyclerView listChatRoomRecycler;
    ChatRoomRecyclerAdapter chatRoomRecyclerAdapter;
    List<String> roomList= new ArrayList<>();
    String yourID="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        yourID=getIntent().getStringExtra("yourID");
        listChatRoomRecycler= (RecyclerView) findViewById(R.id.list_chat_room_recycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        chatRoomRecyclerAdapter = new ChatRoomRecyclerAdapter(this,roomList,yourID);
        listChatRoomRecycler.setLayoutManager(linearLayoutManager);
        listChatRoomRecycler.setAdapter(chatRoomRecyclerAdapter);

    }

    @Override
    public void getRoomList(List<String> roomList) {
        this.roomList=roomList;
    }
}
