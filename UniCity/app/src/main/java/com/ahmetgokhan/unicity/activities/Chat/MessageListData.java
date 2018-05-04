package com.ahmetgokhan.unicity.activities.Chat;

public class MessageListData {

    private String name;
    private String message;
    private String thread_id;
    private String circleImageView;
    private String from_name;

    public MessageListData(String name, String message, String thread_id, String circleImageView,String from_name) {
        this.name = name;
        this.message = message;
        this.thread_id = thread_id;
        this.circleImageView = circleImageView;
        this.from_name = from_name;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getCircleImageView() {
        return circleImageView;
    }

    public void setCircleImageView(String circleImageView) {
        this.circleImageView = circleImageView;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
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

