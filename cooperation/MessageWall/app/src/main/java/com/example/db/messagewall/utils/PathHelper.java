package com.example.db.messagewall.utils;

import android.os.Environment;

import com.avos.avoscloud.im.v2.AVIMTypedMessage;

import java.io.File;

/**
 * Created by db on 8/4/15.
 */
public class PathHelper {

    /**
     * 录音保存的地址
     *
     * @return
     */
    public static String getRecordPathByCurrentTime() {

        File directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        directory.mkdir();
        File QR=new File(directory.getAbsolutePath()+"/MessageWall/Record");
        if (!QR.exists()){
            QR.mkdir();
        }

        return new File( QR.getAbsolutePath()+"/record_" + System.currentTimeMillis()).getAbsolutePath();
    }

    public static File getFilePath(String msdId) {
        File directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        directory.mkdir();
        File QR=new File(directory.getAbsolutePath()+"/MessageWall/Audio");
        if (!QR.exists()){
            QR.mkdir();
        }

        return new File( QR.getAbsolutePath()+"/audio_" + msdId);
    }
}
