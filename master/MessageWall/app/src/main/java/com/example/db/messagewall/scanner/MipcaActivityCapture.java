package com.example.db.messagewall.scanner;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.example.db.messagewall.activity.AddMessageWallActivity;
import com.example.db.messagewall.activity.SelectActivity;
import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.view.MaterialDialog;
import com.example.db.messagewall.view.materialedittext.MaterialEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.support.android.designlibdemo.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Initial the camera
 * @author Ryan.Tang
 */
public class MipcaActivityCapture extends AppCompatActivity implements Callback {

	public Toolbar toolbar;

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;

    public boolean flag=false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		AppConstant.setScanner(true, this);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture);

		initToolBar();

		//ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		

		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
		
	}

	private void initToolBar() {

		toolbar = (Toolbar) findViewById(R.id.scanner_toolbar);
		toolbar.setTitle("扫码加入留言墙");
		toolbar.setTitleTextColor(getResources().getColor(R.color.actionbar_title_color));
		toolbar.setSubtitleTextColor(getResources().getColor(R.color.actionbar_title_color));

		if (Build.VERSION.SDK_INT >= 21)
			toolbar.setElevation(24);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}
	public Toolbar getToolbar(){
		return toolbar;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}
	
	/**
	 * @param result
	 * @param barcode
	 */
	public void handleDecode(final Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		final String resultString = result.getText();
		if (resultString.equals("")) {
			Toast.makeText(MipcaActivityCapture.this, "Scan failed!", Toast.LENGTH_SHORT).show();
		}else {

			Log.v("scanner",resultString);
			if (AVUser.getCurrentUser()!=null){
				String phone = AVUser.getCurrentUser().getUsername();
				if (phone!=null&&AppConstant.isMobile(phone)){
									/*
									扫描以后处理二维码
									自动加入二维码携带信息中的群组
									 */
					final String[] temp = resultString.split(" ");
									/*
									加入输入的手机用户到留言墙中去
									 */
					final List<String> list = new ArrayList<String>();
					list.add(phone);
									/*
									根据clinetId获得AVIMClient
									 */
					AVIMClient avimClient = AVIMClient.getInstance(temp[1]);
					avimClient.open(new AVIMClientCallback() {
						@Override
						public void done(AVIMClient avimClient, AVException e) {
							if (e==null){
								AVIMConversation avimConversation = avimClient.getConversation(temp[0]);
								avimConversation.addMembers(list, new AVIMConversationCallback() {
									@Override
									public void done(AVException e) {
										if (e==null){
											Intent intent = new Intent(MipcaActivityCapture.this, SelectActivity.class);
											startActivity(intent);
											MipcaActivityCapture.this.finish();
											AppConstant.showSelfToast(MipcaActivityCapture.this,"加入成功！");
										}else {
											AppConstant.showSelfToast(MipcaActivityCapture.this,"加入失败！");
											Log.v("db.error15",e.getMessage());
										}
									}
								});
							}else {
								Log.v("db.error14",e.getMessage());
							}
						}
					});

				}else {
					AppConstant.showSelfToast(MipcaActivityCapture.this,"输入不正确!");
				}
		}else {
				AppConstant.showSelfToast(getApplicationContext(),"请先登陆或注册！");
			}

		}
	}
	
	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_scanner, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id==R.id.menu_light){

		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

}