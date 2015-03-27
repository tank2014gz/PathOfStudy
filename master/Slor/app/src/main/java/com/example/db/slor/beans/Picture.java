package com.example.db.slor.beans;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

/**
 * Created by db on 3/26/15.
 */
@AVClassName(Picture.PHOTO_CLASS)
public class Picture extends AVObject{
    public static final String PHOTO_CLASS="photo";
    public static String URL_KEY="url";
    public static String DESCRIPTION_KEY="description";
    public static String TITLE_KEY="title";
    public static String USER_KEY="user";
    public void setUrl(String url){
        this.put(URL_KEY,url);
    }
    public String getUrl(){
        return this.getString(URL_KEY);
    }
    public void setDescription(String description){
        this.put(DESCRIPTION_KEY,description);
    }
    public String getDescription(){
        return this.getString(DESCRIPTION_KEY);
    }
    public void setTitle(String title){
        this.put(TITLE_KEY,title);
    }
    public String getTitle(){
        return this.getString(TITLE_KEY);
    }
    public void setUser(AVUser avUser){
        this.put(USER_KEY,avUser);
    }
    public AVUser getUser(){
        return this.getAVUser(USER_KEY);
    }
}
