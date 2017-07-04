package com.example.sanjaya.myapplication;

/**
 * Created by sanjaya on 6/28/2017.
 */

public class ChatModel {
    private String fromID;
    private String message;
    private String time;

    public ChatModel(){
        this.fromID="";
        this.message="";
        this.time="";
    }

    public ChatModel(String fromID,String message,String time){
        this.fromID=fromID;
        this.message=message;
        this.time=time;
    }

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
