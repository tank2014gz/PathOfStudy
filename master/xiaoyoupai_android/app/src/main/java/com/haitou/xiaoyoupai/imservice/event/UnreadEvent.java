package com.haitou.xiaoyoupai.imservice.event;

import com.haitou.xiaoyoupai.imservice.entity.UnreadEntity;

/**
 * @author : yingmu on 15-1-6.
 * @email : yingmu@haitou.com.
 */
public class UnreadEvent {

    public UnreadEntity entity;
    public Event event;

    public UnreadEvent(){}
    public UnreadEvent(Event e){
        this.event = e;
    }

    public enum Event {
        UNREAD_MSG_LIST_OK,
        UNREAD_MSG_RECEIVED,

        SESSION_READED_UNREAD_MSG
    }
}
