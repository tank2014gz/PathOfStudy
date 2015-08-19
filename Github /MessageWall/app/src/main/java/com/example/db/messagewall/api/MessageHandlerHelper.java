package com.example.db.messagewall.api;

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
import com.example.db.messagewall.activity.SelectActivity;
import com.support.android.designlibdemo.R;

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

                /*
                发送通知栏的通知
                 */
                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
                mBuilder.setContentTitle("留言墙")
                        .setContentText("...发布了新消息惹")
                        .setTicker("测试通知来啦")
                        .setWhen(System.currentTimeMillis())
                        .setPriority(Notification.DEFAULT_ALL)
                                  .setAutoCancel(true)
                        .setOngoing(false)
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setSmallIcon(R.drawable.logo);
                Notification notification = mBuilder.build();
                notification.flags = Notification.FLAG_ONGOING_EVENT  ;
                notification.flags = Notification.FLAG_NO_CLEAR;
                Intent intent = new Intent(context,SelectActivity.class);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                mBuilder.setContentIntent(pendingIntent);
                mNotificationManager.notify(0,notification);
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
