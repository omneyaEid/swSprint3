package com.example.demo.dao;

public class PlaceHolder {
    private final int id;
    private final String name;
    private final String book;
    private final String type;
    private final String mailOrSMS;

    public PlaceHolder(int id, String name, String book, String type, String mailOrSMS) {
        this.id = id;
        this.name = name;
        this.book = book;
        this.type=type;
        this.mailOrSMS = mailOrSMS;
    }

    public String getMailOrSMS() {
        return mailOrSMS;
    }

    public String getType() {
        return type;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBook() {
        return book;
    }
}
