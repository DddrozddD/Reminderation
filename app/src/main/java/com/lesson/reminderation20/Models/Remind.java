package com.lesson.reminderation20.Models;

public class Remind {
    public String Id;
    public String desc;
public Remind(){}

    public Remind(String id, String text, String title, String userId, String date) {
        this.Id = id;
        this.desc = text;
        this.title = title;
        this.userId = userId;
        this.date = date;
    }

    public String title;
    public String userId;
    public String date;


}
