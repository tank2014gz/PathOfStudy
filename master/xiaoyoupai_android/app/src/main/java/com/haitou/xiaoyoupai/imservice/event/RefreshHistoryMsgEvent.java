package com.haitou.xiaoyoupai.imservice.event;

import com.haitou.xiaoyoupai.DB.entity.MessageEntity;

import java.util.List;

/**
 * @author : yingmu on 15-3-26.
 * @email : yingmu@haitou.com.
 *
 * 异步刷新历史消息
 */
public class RefreshHistoryMsgEvent {
   public int pullTimes;
   public int lastMsgId;
   public int count;
   public List<MessageEntity> listMsg;
   public int peerId;
   public int peerType;
   public String sessionKey;

   public RefreshHistoryMsgEvent(){}

}
