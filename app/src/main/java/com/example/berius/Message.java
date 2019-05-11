package com.example.berius;

import java.io.Serializable;

public class Message implements Serializable{
    private String text;
    private String remoteDBId;
    private String time;

    public Message(String text, String remoteDBId, String time){
        this.text = text;
        this.remoteDBId = remoteDBId;
        this.time = time;
    }

    public String getText(){
        return this.text;
    }
    public String getId(){ return this.remoteDBId; }
    public String getTime(){ return this.time;}
    }
