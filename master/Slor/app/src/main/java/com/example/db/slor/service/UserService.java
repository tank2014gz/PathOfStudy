package com.example.db.slor.service;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;

/**
 * Created by db on 3/25/15.
 */
public class UserService {


    public static AVUser findUser(String id) throws AVException {
        AVQuery<AVUser> q = AVUser.getQuery(AVUser.class);
        q.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        return q.get(id);
    }
    public static AVUser signUp(String name, String password) throws AVException {
        AVUser user = new AVUser();
        user.setUsername(name);
        user.setPassword(password);
        user.signUp();
        return user;
    }

}
