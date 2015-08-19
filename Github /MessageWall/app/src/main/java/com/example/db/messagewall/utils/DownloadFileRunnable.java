package com.example.db.messagewall.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.InputStream;

/**
 * Created by db on 8/4/15.
 */
public class DownloadFileRunnable implements Runnable {

    public Handler handler;
    public String uri, filename;

    public String path;

    public DownloadFileRunnable(Handler handler, String uri, String filename) {
        super();
        this.handler = handler;
        this.uri = uri;
        this.filename = filename;
    }

    @Override
    public void run() {
        try {

            InputStream inputStream = Download.getImageStream(uri);
            path = Download.writeToSDFromInputStream(filename, inputStream);

            Message message = Message.obtain();
            message.obj = path;
            message.what = 0x123;
            handler.sendMessage(message);

        } catch (Exception e) {

            Message message = Message.obtain();
            message.obj = "";
            message.what = 0x122;
            handler.sendMessage(message);
            Log.v("download.error", e.getMessage());

        }
    }
}
