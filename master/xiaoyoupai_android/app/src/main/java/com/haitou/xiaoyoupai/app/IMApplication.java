package com.haitou.xiaoyoupai.app;

import android.app.Application;
import android.content.Intent;
import com.haitou.xiaoyoupai.imservice.service.IMService;
import com.haitou.xiaoyoupai.utils.ImageLoaderUtil;
import com.haitou.xiaoyoupai.utils.Logger;


public class IMApplication extends Application {

	private Logger logger = Logger.getLogger(IMApplication.class);

	@Override
	public void onCreate() {
		super.onCreate();
		logger.i("Application starts");
		startIMService();
		ImageLoaderUtil.initImageLoaderConfig(getApplicationContext());
	}

	private void startIMService() {
		logger.i("start IMService");
		Intent intent = new Intent();
		intent.setClass(this, IMService.class);
		startService(intent);
	}

    public static boolean gifRunning = true;//gif是否运行
}
