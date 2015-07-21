package com.example.db.messagewall.api;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

/**
 * Created by db on 7/15/15.
 */
public class MessageHandler extends AVIMMessageHandler{

    public AVIMMessageHandler avimMessageHandler;
    public Context context;


    public MessageHandler(Context context){
        super();
        this.context = context;
    }



    @Override
    public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
        /*
        处理消息的代码
         */
        String msgContent = message.getContent();
        String msgDate = String.valueOf(message.getTimestamp());
        String msgfrom = message.getFrom();

        if (client.getClientId().equals(AppData.getClientIdFromPre())){

            if (avimMessageHandler!=null){
                avimMessageHandler.onMessage(message,conversation,client);
            }else {

            }

        }else {
            client.close(null);
        }

    }

    public void setAvimMessageHandler(AVIMMessageHandler avimMessageHandler){
        this.avimMessageHandler = avimMessageHandler;
    }
    public AVIMMessageHandler getAvimMessageHandler(){
        return this.avimMessageHandler;
    }
}