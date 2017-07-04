package com.example.sanjaya.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sanjaya on 6/29/2017.
 */

public class ChatRoomRecyclerAdapter extends RecyclerView.Adapter<ChatRoomRecyclerAdapter.CustomViewHolder> {

    List<String> chatModelList;
    Context context;
    String yourID;
    public ChatRoomRecyclerAdapter(Context context, List<String> chatModelList,String yourID){
        this.chatModelList=chatModelList;
        this.context=context;
        this.yourID=yourID;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_room_item, null);
        CustomViewHolder viewHolder=new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.chatSender.setText(chatModelList.get(position));
    }


    @Override
    public int getItemCount() {
        return chatModelList.size();
    }
    class CustomViewHolder extends RecyclerView.ViewHolder{
        protected Button chatSender;

        public CustomViewHolder(View view) {
            super(view);
            chatSender = (Button) view.findViewById(R.id.roomLabel);
            chatSender.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(context,ChatActivity.class);
                    intent.putExtra("chatRoom",chatSender.getText().toString());
                    intent.putExtra("yourID",yourID);
                    context.startActivity(intent);
                }
            });

        }
    }
}
