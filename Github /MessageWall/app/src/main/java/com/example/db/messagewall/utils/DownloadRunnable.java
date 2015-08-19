package com.example.db.messagewall.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.db.messagewall.bean.MemberInfo;


/**
 * Created by db on 7/20/15.
 */
public class DownloadRunnable implements Runnable {

    public Handler handler;
    public String uri,filename;

    public DownloadRunnable(Handler handler,String uri,String filename){
        super();
        this.handler = handler;
        this.uri = uri;
        this.filename = filename;
    }

    @Override
    public void run() {
        try {

            Bitmap bitmap = BitmapFactory.decodeStream(Download.getImageStream(uri));
            Download.saveFile(bitmap,filename);

            Message message = Message.obtain();
            message.obj = "下载保存成功！";
            message.what = 0x123;
            handler.sendMessage(message);

        }catch (Exception e){

            Message message = Message.obtain();
            message.obj = "下载保存失败！";
            message.what = 0x122;
            handler.sendMessage(message);
            Log.v("download.error",e.getMessage());

        }
    }
}
