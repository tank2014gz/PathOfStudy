package com.haitou.xiaoyoupai.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.haitou.xiaoyoupai.R;
import com.haitou.xiaoyoupai.config.IntentConstant;
import com.haitou.xiaoyoupai.ui.base.TTBaseFragmentActivity;
import com.haitou.xiaoyoupai.ui.fragment.WebviewFragment;

public class WebViewFragmentActivity extends TTBaseFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent=getIntent();
		if (intent.hasExtra(IntentConstant.WEBVIEW_URL)) {
			WebviewFragment.setUrl(intent.getStringExtra(IntentConstant.WEBVIEW_URL));
		}
		setContentView(R.layout.tt_fragment_activity_webview);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
