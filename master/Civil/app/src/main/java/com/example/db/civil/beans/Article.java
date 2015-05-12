package com.example.db.civil.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by db on 4/9/15.
 */
public class Article {
    public List<String> url;
    public ArrayList<String> content;
    public void setUrl(List<String> url){
        this.url=url;
    }
    public List<String> getUrl(){
        return url;
    }
    public void setContent(ArrayList<String> content){
        this.content=content;
    }
    public ArrayList<String> getContent(){
        return content;
    }

}
