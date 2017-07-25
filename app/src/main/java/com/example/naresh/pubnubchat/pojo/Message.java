package com.example.naresh.pubnubchat.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.TimeZone;

public class Message {

    public static final int TEXT = 0, IMAGE = 1, FILE = 2, GMAPS = 3;
    public static final int SENDING = 0, SENT = 1, RECEIVED_RECIPIENT = 2, READ_RECIPIENT = 3, RECEIVED_ME = 4, READ_ME = 5;
    private int type = 0;
    private String text = "", sender = "Unknown", url, latLon = "0,0";
    private long timetoken;
    private long id;
    private int status;
    private String channelName;

    @SerializedName("username")
    private String username;
    @SerializedName("message")
    private String message;


    public Message(String username, String message) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        timetoken = calendar.getTimeInMillis() * 10000;
        text = "";
        sender = "";
        type = TEXT;
        status = SENDING;
        id = -1;
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String name) {
        this.channelName = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setTimetoken(long timetoken) {
        this.timetoken = timetoken;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTimetoken() {
        return timetoken;
    }

    public String getLatLon() {
        return latLon;
    }

    public void setLatLon(String latLon) {
        this.latLon = latLon;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getText() {
        return text;
    }

    public String getSender() {
        return sender;
    }

}
