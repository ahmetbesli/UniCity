package com.ahmetgokhan.unicity.activities.Chat;

public class ChatMessage {

    private String name;
    private String message;
    private String date;
    private int other;

    public ChatMessage(String name, String message,String date) {
        this.name = name;
        this.message = message;
        this.date = date;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
