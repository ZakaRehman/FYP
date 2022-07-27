package com.example.finalyearproject;

public class MessageModel {
    private String message;
    private String sender;
    private String receiver;

    public MessageModel(String message, String sender, String receiver) {
        setMessage(message);
        setSender(sender);
        setReceiver(receiver);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
