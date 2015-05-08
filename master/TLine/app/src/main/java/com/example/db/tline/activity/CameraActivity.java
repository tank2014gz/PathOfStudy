package com.example.db.tline.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.db.tline.MainActivity;
import com.example.db.tline.R;
import com.example.db.tline.utils.AppConstant;
import com.example.db.tline.view.RevealLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class CameraActivity extends Activity {

    SurfaceView sView;
    SurfaceHolder surfaceHolder;
    int screenWidth,screenHeight;
    Camera camera;
    boolean isPreview=false;
    boolean isClicked=false;

    public RevealLayout mRevealLayout;
    public boolean mIsAnimationSlowDown = false;
    public boolean mIsBaseOnTouchLocation = false;

    public Button mCatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //设置全屏显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);
        //获取窗口管理器
        WindowManager wm=getWindowManager();
        Display display=wm.getDefaultDisplay();
        DisplayMetrics metrics=new DisplayMetrics();
        //获取屏幕的宽和高
        display.getMetrics(metrics);
        screenWidth=metrics.widthPixels;
        screenHeight=metrics.heightPixels;
        sView=(SurfaceView)findViewById(R.id.surface);
        //设置Surface不需要自己维护缓冲区
        sView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder=sView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                initCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                if (camera!=null){
                    if (isPreview){
                        camera.startPreview();
                        camera.release();
                        camera=null;
                    }
                }
            }
        });
        sView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capture();
            }
        });

        mRevealLayout = (RevealLayout) findViewById(R.id.reveal_layout);

    }
    //初始化照相机对象
    private void initCamera(){
        if (!isPreview){
            //此处默认打开后置摄像头
            //此处通过传参数，可以打开后置摄像头
            camera=Camera.open(0);
            camera.setDisplayOrientation(90);
        }
        if (camera!=null&&!isPreview){
            try {
                Camera.Parameters parameters=camera.getParameters();
                //设置预览图片的大小
                parameters.setPreviewSize(screenWidth,screenHeight);
                //设置预览时显示多少帧的最大值和最小值
                parameters.setPreviewFpsRange(4,10);
                //设置图片的格式
                parameters.setPreviewFormat(ImageFormat.JPEG);
                //设置JPG图片的质量
                parameters.set("jpeg-quality",85);
                //设置照片的大小
                parameters.setPictureSize(screenWidth,screenHeight);
                //通过surface显示取景画面
                camera.setPreviewDisplay(surfaceHolder);
                //开始预览
                camera.startPreview();

            }catch (Exception e){
                e.printStackTrace();
            }
            isPreview=true;
        }
    }
    public void capture(){
        if (camera!=null){
            camera.autoFocus(autoFocusCallback);
        }
    }
    Camera.AutoFocusCallback autoFocusCallback=new Camera.AutoFocusCallback() {
        //自动调焦时触发这个方法
        @Override
        public void onAutoFocus(boolean b, Camera camera) {
            if (b){
                camera.takePicture(new Camera.ShutterCallback() {
                    @Override
                    public void onShutter() {
                        //按下快门时执行此处的代码
                    }
                },new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] bytes, Camera camera) {
                        //此处代码可以决定是否要保存原始图片信息
                    }
                },myJpegCallback);
            }
        }
    };
    Camera.PictureCallback myJpegCallback=new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            //根据拍照所得的数据创建位图
            final Bitmap bm= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                            //创件一个文件SD卡上的文件
                            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/TLine");

                            directory.mkdir();
                            File file = new File(directory, "tline.jpg");
                            String path=directory+"/"+ "tline.jpg";
                            FileOutputStream outputStream = null;
                            //获取评论的内容
                            try {
                                //打开置顶文件所对应的输出流
                                outputStream = new FileOutputStream(file);
                                bm.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                                outputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
            Uri uri=Uri.fromFile(file);
            Intent intent=new Intent(CameraActivity.this, MainActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("camera",uri.toString());
            intent.putExtra("camerabundle",bundle);
            CameraActivity.this.setResult(6,intent);
            CameraActivity.this.finish();

            //重新浏览
            camera.stopPreview();
            camera.startPreview();
            isPreview=true;
        }
    };

    public void voerTack()
    {
        camera.startPreview();
    }

}
