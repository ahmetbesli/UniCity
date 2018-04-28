package com.ahmetgokhan.unicity.activities.Chat;



public class MessageListData {

    private String name;
    private String message;
    private String chat_room;

    public MessageListData(String name, String message,String chat_room) {
        this.name = name;
        this.message = message;
        this.chat_room = chat_room;
    }


    public String getChat_room() {
        return chat_room;
    }

    public void setChat_room(String chat_room) {
        this.chat_room = chat_room;
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

