package com.example.db.tline.beans;

/**
 * Created by db on 4/22/15.
 */
public class TextLineInfo {
    public String title;
    public String content;
    public String date;
    public void setTitle(String title){
        this.title=title;
    }
    public String getTitle(){
        return title;
    }
    public void setContent(String content){
        this.content=content;
    }
    public String getContent(){
        return content;
    }
    public void setDate(String date){
        this.date=date;
    }
    public String getDate(){
        return date;
    }
}
