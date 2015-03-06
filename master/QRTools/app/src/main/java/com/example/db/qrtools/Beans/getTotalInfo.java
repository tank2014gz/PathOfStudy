package com.example.db.qrtools.Beans;

/**
 * Created by db on 3/3/15.
 */
public class getTotalInfo {
    public String path;
    public String size;
    public String time;
    public boolean writeable;
    public boolean readable;
    public boolean hidden;
    public void setPath(String path){
        this.path=path;
    }
    public String getPath(){
        return path;
    }
    public void setSize(String size){
        this.size=size;
    }
    public String getSize(){
        return size;
    }
    public void setTime(String time){
        this.time=time;
    }
    public String getTime(){
        return time;
    }
    public void setWriteable(boolean writeable){
        this.writeable=writeable;
    }
    public boolean getWriteable(){
        return writeable;
    }
    public void setReadable(boolean readable){
        this.readable=readable;
    }
    public boolean getReadable(){
        return readable;
    }
    public void setHidden(boolean hidden){
        this.hidden=hidden;
    }
    public boolean getHidden(){
        return hidden;
    }
}
