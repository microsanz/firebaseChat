package com.example.sanjaya.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sanjaya on 6/27/2017.
 */

public class ChatActivity extends ActivityHandler {
    TextView fromID;
    EditText inputMessage;
    Button sendMessage;
    String yourID;
    List<ChatModel> chatModelList;
    ChatModel chatModel;
    ChatDatabaseController chatDatabaseController;
    private LinearLayoutManager mLinearLayoutManager;
    String chatRoom="";

    FirebaseDatabase database;
    private RecyclerView mMessageRecyclerView;
    private FirebaseRecyclerAdapter<ChatModel, CustomViewHolder>
            mFirebaseAdapter;


    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView chatSender;
        private TextView chatMessage;

        public CustomViewHolder(View view) {
            super(view);
            this.setChatSender((TextView) view.findViewById(R.id.chatSender));
            this.setChatMessage((TextView) view.findViewById(R.id.chatMessage));
        }

        public TextView getChatSender() {
            return chatSender;
        }

        public void setChatSender(TextView chatSender) {
            this.chatSender = chatSender;
        }

        public TextView getChatMessage() {
            return chatMessage;
        }

        public void setChatMessage(TextView chatMessage) {
            this.chatMessage = chatMessage;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);


        sharedPreferences= getSharedPreferences("chatPref",Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();

        editor.putBoolean("notifChat",false);
        editor.apply();
        Log.i("ChatActivity notifChat", Boolean.toString(sharedPreferences.getBoolean("notifChat",false)));
        chatModelList = new ArrayList<>();
        chatModel = new ChatModel();
        fromID = (TextView) findViewById(R.id.fromID);
        inputMessage = (EditText) findViewById(R.id.inputMessage);
        sendMessage = (Button) findViewById(R.id.sendMessage);
        mMessageRecyclerView = (RecyclerView) findViewById(R.id.chatRecycler);
        mLinearLayoutManager=new LinearLayoutManager(this);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        chatDatabaseController = new ChatDatabaseController(this);

        yourID = getIntent().getStringExtra("yourID");
        chatRoom=getIntent().getStringExtra("chatRoom");

        fromID.setText(chatRoom);
        database=Utils.getDatabase();
        final DatabaseReference myRefParent = database.getReference("message");
        myRefParent.keepSynced(true);
        final DatabaseReference roomID = myRefParent.child(chatRoom);



        mFirebaseAdapter = new FirebaseRecyclerAdapter<ChatModel,
                CustomViewHolder>(
                ChatModel.class,
                R.layout.chat_item,
                CustomViewHolder.class,
                roomID) {

            @Override
            protected void populateViewHolder(CustomViewHolder viewHolder, ChatModel model, int position) {
                viewHolder.getChatSender().setText(model.getFromID());
                Log.d("ID > ", model.getFromID());
                viewHolder.getChatMessage().setText(model.getMessage());

                Log.d("message > ", model.getMessage());
            }
        };
        mMessageRecyclerView.setAdapter(mFirebaseAdapter);


        roomID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot msgSnapshot : dataSnapshot.getChildren()) {
                    ChatModel value = msgSnapshot.getValue(ChatModel.class);
                    Log.d("asdasd", "Value is: " + value.getMessage());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("asdasd", "Failed to read value.", error.toException());
            }
        });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatModel.setFromID(yourID);
                chatModel.setMessage(inputMessage.getText().toString());
                chatModel.setTime(Long.toString(System.currentTimeMillis()));
                chatModelList.add(chatModel);
                myRefParent.child("Room ID 1").push().setValue(chatModel);
                chatModel = new ChatModel();
                inputMessage.setText("");
            }
        });

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition =
                        mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    mMessageRecyclerView.scrollToPosition(positionStart);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        editor= sharedPreferences.edit();
        editor.putString("lastActive",Long.toString(System.currentTimeMillis()));
        editor.putBoolean("notifChat",true);
        editor.apply();
        Log.i("lastActive ", sharedPreferences.getString("lastActive","0"));
        super.onPause();
    }
}

