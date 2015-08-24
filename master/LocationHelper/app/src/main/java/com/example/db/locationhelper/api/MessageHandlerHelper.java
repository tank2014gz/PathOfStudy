package com.example.db.locationhelper.api;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;

/**
 * Created by db on 8/3/15.
 */
public class MessageHandlerHelper extends AVIMTypedMessageHandler<AVIMTypedMessage> {

    public AVIMMessageHandler avimMessageHandler;
    private static MessageHandlerHelper messageHandlerHelper;
    public Context context;

    public MessageHandlerHelper(Context context){
        super();
        this.context = context;
    }


//    /*
//    获取单例
//     */
//    public static synchronized MessageHandlerHelper getInstance() {
//        if ( messageHandlerHelper== null) {
//            messageHandlerHelper = new MessageHandlerHelper();
//        }
//        return messageHandlerHelper;
//    }

    @Override
    public void onMessage(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {
        if (client.getClientId().equals(AppData.getClientIdFromPre())){

            if (avimMessageHandler!=null){
                avimMessageHandler.onMessage(message,conversation,client);
            }else {

            }

        }else {
            client.close(null);
        }
    }

    @Override
    public void onMessageReceipt(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {

    }

    public void setAvimMessageHandler(AVIMMessageHandler avimMessageHandler){
        this.avimMessageHandler = avimMessageHandler;
    }

    public AVIMMessageHandler getAvimMessageHandler(){
        return this.avimMessageHandler;
    }


}
