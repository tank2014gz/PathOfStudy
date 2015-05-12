package com.example.db.civil.beans;

import com.avos.avoscloud.AVObject;

import org.json.JSONArray;

/**
 * Created by db on 5/10/15.
 */
public class Todo extends AVObject{
    public static final String TODO_CLASS="todo";
    public final String COMMIT_KEY="commit";
    public void setCommits(JSONArray commits){
        this.put(COMMIT_KEY,commits);
    }
    public JSONArray getCommits(){
        return this.getJSONArray(COMMIT_KEY);
    }
}
