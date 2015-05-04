package com.example.db.tline.beans;

/**
 * Created by db on 5/1/15.
 */
public class PLineListInfo {
    public String relationtitle;
    public String relationcontent;
    public String relationdate;
    public String title;
    public String content;
    public String date;
    public String url;
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
    public void setRelationtitle(String relationtitle){
        this.relationtitle=relationtitle;
    }
    public String getRelationtitle(){
        return relationtitle;
    }
    public void setRelationcontent(String relationcontent){
        this.relationcontent=relationcontent;
    }
    public String getRelationcontent(){
        return relationcontent;
    }
    public void setRelationdate(String relationdate){
        this.relationdate=relationdate;
    }
    public String getRelationdate(){
        return relationdate;
    }
    public void setUrl(String url){
        this.url=url;
    }
    public String getUrl(){
        return url;
    }
}
