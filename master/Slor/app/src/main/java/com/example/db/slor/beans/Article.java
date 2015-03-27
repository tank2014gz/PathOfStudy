package com.example.db.slor.beans;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

/**
 * Created by db on 3/23/15.
 */
@AVClassName(Article.ARTICLE_CLASS)
public class Article extends AVObject{
    public static final String ARTICLE_CLASS="article";
    public final String USER_KEY="user";
    public final String DATE_KEY="date";
    public final String TITLE_KEY="title";
    public final String CONTENT_KEY="content";
    public final String OTHERS_SEE_KEY="others_see";
    public final String OWN_SEE_KEY="own_see";
    public void setUser(AVUser avUser){
        this.put(USER_KEY,avUser);
    }
    public AVUser getUser(){
        return this.getAVUser(USER_KEY);
    }
    public void setDate(String date){
        this.put(DATE_KEY,date);
    }
    public String getDate(){
        return this.getString(DATE_KEY);
    }
    public void setTitle(String title){
        this.put(TITLE_KEY,title);
    }
    public String getTitle(){
        return this.getString(TITLE_KEY);
    }
    public void setContent(String content){
        this.put(CONTENT_KEY,content);
    }
    public String getContent(){
        return this.getString(CONTENT_KEY);
    }
    public void setOthersSee(boolean isOthersSee){
        this.put(OTHERS_SEE_KEY,isOthersSee);
    }
    public boolean getOthersSee(){
        return this.getBoolean(OTHERS_SEE_KEY);
    }
    public void setOwnSee(boolean isOwnSee){
        this.put(OWN_SEE_KEY,isOwnSee);
    }
    public boolean getOwnSee(){
        return this.getBoolean(OWN_SEE_KEY);
    }
}
