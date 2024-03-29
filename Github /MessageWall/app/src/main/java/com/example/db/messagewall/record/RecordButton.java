package com.example.db.messagewall.record;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.view.MaterialDialog;
import com.support.android.designlibdemo.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RecordButton extends Button {
  public static final int BACK_RECORDING = R.drawable.circle_ganlan;
  public static final int BACK_IDLE = R.drawable.circle_anshihui;
  public static final int SLIDE_UP_TO_CANCEL = 0;
  public static final int RELEASE_TO_CANCEL = 1;
  private static final int MIN_INTERVAL_TIME = 1000;// 2s
  private static int[] recordImageIds = {R.drawable.chat_icon_voice0,
      R.drawable.chat_icon_voice1, R.drawable.chat_icon_voice2,
      R.drawable.chat_icon_voice3, R.drawable.chat_icon_voice4,
      R.drawable.chat_icon_voice5};
  private TextView textView;
  private String outputPath = null;
  private RecordEventListener recordEventListener;
  private long startTime;
  private Dialog recordIndicator;
  private View view;
  private MediaRecorder recorder;
  private ObtainDecibelThread thread;
  private Handler volumeHandler;
  private ImageView imageView;
  private int status;
  private OnDismissListener onDismiss = new OnDismissListener() {

    @Override
    public void onDismiss(DialogInterface dialog) {
      stopRecording();
    }
  };

  public RecordButton(Context context) {
    super(context);
    init();
  }

  public RecordButton(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  public RecordButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public void setSavePath(String path) {
    outputPath = path;
  }

  public void setRecordEventListener(RecordEventListener listener) {
    recordEventListener = listener;
  }

  private void init() {
    volumeHandler = new ShowVolumeHandler();
    setBackgroundResource(BACK_IDLE);
    initRecordDialog();
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (outputPath == null)
      return false;
    int action = event.getAction();
    switch (action) {
      case MotionEvent.ACTION_DOWN:
        startRecord();
        break;
      case MotionEvent.ACTION_UP:
        if (status == RELEASE_TO_CANCEL) {
          cancelRecord();
        } else {
          try {
            finishRecord();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
        break;
      case MotionEvent.ACTION_MOVE:
        if (event.getY() < 0) {
          status = RELEASE_TO_CANCEL;
        } else {
          status = SLIDE_UP_TO_CANCEL;
        }
        setTextViewByStatus();
        break;
      case MotionEvent.ACTION_CANCEL:
        cancelRecord();
        break;
    }
    return true;
  }

  public int getColor(int id) {
    return getContext().getResources().getColor(id);
  }

  private void setTextViewByStatus() {
    if (status == RELEASE_TO_CANCEL) {
      textView.setTextColor(getColor(R.color.chat_record_btn_red));
      textView.setText(R.string.chat_record_button_releaseToCancel);
    } else if (status == SLIDE_UP_TO_CANCEL) {
      textView.setTextColor(getColor(R.color.chat_common_white));
      textView.setText(R.string.chat_record_button_slideUpToCancel);
    }
  }

  private void startRecord() {
    startTime = System.currentTimeMillis();
    setBackgroundResource(BACK_RECORDING);
    startRecording();
    recordIndicator.show();
  }

  private void initRecordDialog() {
    recordIndicator = new Dialog(getContext(),
            R.style.chat_record_button_toast_dialog_style);

    view = inflate(getContext(), R.layout.chat_record_layout, null);
    imageView = (ImageView) view.findViewById(R.id.imageView);
    textView = (TextView) view.findViewById(R.id.textView);
    recordIndicator.setContentView(view, new LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
    recordIndicator.setOnDismissListener(onDismiss);

    LayoutParams lp = recordIndicator.getWindow().getAttributes();
    lp.gravity = Gravity.CENTER;

  }

  private void removeFile() {
    File file = new File(outputPath);
    if (file.exists()) {
      file.delete();
    }
  }

  private void finishRecord() throws IOException {
    stopRecording();
    recordIndicator.dismiss();
    setBackgroundResource(BACK_IDLE);

    long intervalTime = System.currentTimeMillis() - startTime;
    if (intervalTime < MIN_INTERVAL_TIME) {
      AppConstant.showSelfToast(getContext(),getContext().getString(R.string.chat_record_button_pleaseSayMore));
      removeFile();
      return;
    }

    int sec = Math.round(intervalTime * 1.0f / 1000);
    if (recordEventListener != null) {
      recordEventListener.onFinishedRecord(outputPath, sec);
    }
  }

  private void cancelRecord() {
    stopRecording();
    setBackgroundResource(BACK_IDLE);
    recordIndicator.dismiss();
    AppConstant.showSelfToast(getContext(),getContext().getString(R.string.chat_cancelRecord));
    removeFile();
  }

  private void startRecording() {
    try {
      if (recorder == null) {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setOutputFile(outputPath);
        recorder.prepare();
      } else {
        recorder.reset();
        recorder.setOutputFile(outputPath);
      }
      recorder.start();
      thread = new ObtainDecibelThread();
      thread.start();
      recordEventListener.onStartRecord();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void stopRecording() {
    if (thread != null) {
      thread.exit();
      thread = null;
    }
    if (recorder != null) {
      try {
        recorder.stop();
      } catch (Exception e) {
      } finally {
        recorder.release();
        recorder = null;
      }
    }
  }

  public interface RecordEventListener {
    public void onFinishedRecord(String audioPath, int secs) throws IOException;

    void onStartRecord();
  }

  private class ObtainDecibelThread extends Thread {
    private volatile boolean running = true;

    public void exit() {
      running = false;
    }

    @Override
    public void run() {
      while (running) {
        try {
          Thread.sleep(200);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        if (recorder == null || !running) {
          break;
        }
        int x = recorder.getMaxAmplitude();
        if (x != 0) {
          int f = (int) (10 * Math.log(x) / Math.log(10));
          int index = (f - 18) / 5;
          if (index < 0) index = 0;
          if (index > 5) index = 5;
          volumeHandler.sendEmptyMessage(index);
        }
      }
    }

  }

  class ShowVolumeHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
      imageView.setImageResource(recordImageIds[msg.what]);
      //imageView.setImageResource(recordImageIds[5]);
    }
  }
}
