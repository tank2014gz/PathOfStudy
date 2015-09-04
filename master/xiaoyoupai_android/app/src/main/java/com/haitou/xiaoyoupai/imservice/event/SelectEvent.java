package com.haitou.xiaoyoupai.imservice.event;

import com.haitou.xiaoyoupai.ui.adapter.album.ImageItem;

import java.util.List;

/**
 * @author : yingmu on 15-1-16.
 * @email : yingmu@haitou.com.
 */
public class SelectEvent {
    private List<ImageItem> list;
    public SelectEvent(List<ImageItem> list){
        this.list = list;
    }

    public List<ImageItem> getList() {
        return list;
    }

    public void setList(List<ImageItem> list) {
        this.list = list;
    }
}
