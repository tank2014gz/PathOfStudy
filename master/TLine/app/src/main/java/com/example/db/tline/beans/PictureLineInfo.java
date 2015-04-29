package com.example.db.tline.beans;

/**
 * Created by db on 4/23/15.
 */
public class PictureLineInfo {
    public String title;
    public String content;
    public String date;
    public String uri;
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
    public void setUri(String uri){
        this.uri=uri;
    }
    public String getUri(){
        return uri;
    }
}
