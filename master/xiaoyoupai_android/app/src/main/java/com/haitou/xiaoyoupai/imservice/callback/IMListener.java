package com.haitou.xiaoyoupai.imservice.callback;

/**
 * @author : yingmu on 15-1-7.
 * @email : yingmu@haitou.com.
 */
public interface IMListener<T> {
    public abstract void onSuccess(T response);

    public abstract void onFaild();

    public abstract void onTimeout();
}
