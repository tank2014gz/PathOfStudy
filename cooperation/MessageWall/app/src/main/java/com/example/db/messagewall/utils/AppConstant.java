package com.example.db.messagewall.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.db.messagewall.activity.PersonInfoActivity;
import com.example.db.messagewall.view.SystemBarTintManager;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.support.android.designlibdemo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by db on 7/13/15.
 */
public class AppConstant {

    public static int QR_WIDTH=160;
    public static int QR_HEIGHT=160;

    // 两个人之间的单聊
    public static int ConversationType_OneOne = 0;
    //群聊
    public static int ConversationType_Group = 1;

    public static SystemBarTintManager tintManager;

    public static void setStatus(boolean on,Activity context){

        /**
        Window window = context.getWindow();
        WindowManager.LayoutParams layoutParams=window.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on){
            layoutParams.flags |=bits;
        }else {
            layoutParams.flags &= ~bits;
        }
        window.setAttributes(layoutParams);
         */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(context);
            switch (ThemeHelper.getTheme(context)){
                case R.style.nLiveo_Theme_BlueActionBar:
                    tintManager.setStatusBarTintColor(context.getResources().getColor(R.color.actionbar_blue));
                    break;
                case R.style.nLiveo_Theme_DarkActionBar:
                    tintManager.setStatusBarTintColor(context.getResources().getColor(R.color.actionbar));
                    break;
                case R.style.nLiveo_Theme_ChinaredActionBar:
                    tintManager.setStatusBarTintColor(context.getResources().getColor(R.color.actionbar_chinared));
                    break;
                case R.style.nLiveo_Theme_GdblackActionBar:
                    tintManager.setStatusBarTintColor(context.getResources().getColor(R.color.actionbar_gdblack));
                    break;
                case R.style.nLiveo_Theme_DoubangreenActionBar:
                    tintManager.setStatusBarTintColor(context.getResources().getColor(R.color.actionbar_doubangreen));
                    break;
                case R.style.nLiveo_Theme_XiaomigreenActionBar:
                    tintManager.setStatusBarTintColor(context.getResources().getColor(R.color.actionbar_xiaomiorigin));
                    break;
                case R.style.nLiveo_Theme_DansuiyellowActionBar:
                    tintManager.setStatusBarTintColor(context.getResources().getColor(R.color.actionbar_dansuiyellow));
                    break;
                case R.style.nLiveo_Theme_NaocanpinkActionBar:
                    tintManager.setStatusBarTintColor(context.getResources().getColor(R.color.actionbar_naocanpink));
                    break;
                case R.style.nLiveo_Theme_MensaopurpleActionBar:
                    tintManager.setStatusBarTintColor(context.getResources().getColor(R.color.actionbar_mensaopurple));
                    break;
                default:
                    tintManager.setStatusBarTintColor(context.getResources().getColor(R.color.actionbar_wumai));
            }
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    public static void setStatus0(boolean on,Activity context){
        Window window = context.getWindow();
        WindowManager.LayoutParams layoutParams=window.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on){
            layoutParams.flags |=bits;
        }else {
            layoutParams.flags &= ~bits;
        }
        window.setAttributes(layoutParams);
    }

    public static void setScanner(boolean on,Activity context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(context);
            tintManager.setStatusBarTintColor(context.getResources().getColor(R.color.scanner));
            tintManager.setStatusBarTintEnabled(true);
        }
    }
    /**
     * 方法描述：把dp转换为px<br>
     * 创建时间：2013-4-28  下午2:17:38   创建人：李小冰
     * @return
     */
    private int calculateDpToPx(int padding_in_dp,Context context){
        final float scale = context.getResources().getDisplayMetrics().density;
        return  (int) (padding_in_dp * scale + 0.5f);
    }

    /*
    获取当前的时间
     */
    public static String getCurrentTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        String year = str.substring(0,4);
        String month = str.substring(5,7);
        String day = str.substring(8,10);
        return year+"."+month+"."+day;
    }

    /*
    将long型的时间转化成年、月、日的形式
     */
    public static String convertTime(long date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        /*
        将传入的时间转化成标准格式
         */
        Date curDate = new Date(date);
        String str = formatter.format(curDate);
        String year = str.substring(0,4);
        String month = str.substring(5,7);
        String day = str.substring(8,10);
        return year+"."+month+"."+day;
    }
    /*
    生成二位码并保存到本地文件中
     */
    public static Bitmap createQRImage(String content){
        Bitmap bitmap=null;
        try{
            QRCodeWriter qrCodeWriter=new QRCodeWriter();
            if (content==null||"".equals(content)||content.length()<1){
                return null;
            }
            //将输入的文本转化成文本
            BitMatrix bitMatrix=qrCodeWriter.encode(content, BarcodeFormat.QR_CODE,AppConstant.QR_WIDTH,AppConstant.QR_HEIGHT);
            Hashtable<EncodeHintType,String> hashtable=new Hashtable<EncodeHintType,String>();
            hashtable.put(EncodeHintType.CHARACTER_SET,"utf-8");
            BitMatrix bitMatrix1=new QRCodeWriter().encode(content,BarcodeFormat.QR_CODE,AppConstant.QR_WIDTH,AppConstant.QR_HEIGHT,hashtable);
            int[] pixels=new int[AppConstant.QR_WIDTH*AppConstant.QR_HEIGHT];
            for (int y=0;y<AppConstant.QR_HEIGHT;y++){
                for (int x=0;x<AppConstant.QR_WIDTH;x++){
                    if (bitMatrix1.get(x,y)){
                        pixels[y * AppConstant.QR_WIDTH + x] = 0xff000000;
                    }else {
                        pixels[y * AppConstant.QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            bitmap=Bitmap.createBitmap(AppConstant.QR_WIDTH,AppConstant.QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, AppConstant.QR_WIDTH, 0, 0, AppConstant.QR_WIDTH, AppConstant.QR_HEIGHT);

        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    /*
    保存到本地并返回文件的路径
     */
    public static String saveQRImage(Bitmap bitmap,String title){

        File directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        directory.mkdir();
        File QR=new File(directory.getAbsolutePath()+"/MessageWall");
        if (!QR.exists()){
            QR.mkdir();
        }
        File file=new File(QR.getAbsolutePath()+"/"+title+".png");
        try {
            file.createNewFile();
            OutputStream outputStream=new FileOutputStream(file);
            if (bitmap!=null){
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                outputStream.flush();
                outputStream.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return file.getPath();
    }
    /*
    自定义Toast的布局内容
     */
    public static void showSelfToast(Context context,String title){
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.self_toast,null);
        TextView textView = (TextView)view.findViewById(R.id.text);
        textView.setText(title);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
    /*
    利用正则表达式来验证输入的手机号码是否为合法的格式
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
    /*
    判断文件是否已经下载了
     */
    public static boolean isExist(int position){
        File directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        directory.mkdir();
        File QR=new File(directory.getAbsolutePath()+"/MessageWall/Paper");
        if (!QR.exists()){
            QR.mkdir();
        }
        File myCaptureFile = new File(QR.getAbsolutePath() +"/"+"paper_bkg"+String.valueOf(position)+".png");
        if (myCaptureFile.exists()){
            return true;
        }else {
            return false;
        }
    }

    /*
    判断文件是否已经下载,这是itempaper
     */
    public static boolean isExist0(int position){
        File directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        directory.mkdir();
        File QR=new File(directory.getAbsolutePath()+"/MessageWall/ItemPaper");
        if (!QR.exists()){
            QR.mkdir();
        }
        File myCaptureFile = new File(QR.getAbsolutePath() +"/"+"paper_bkg"+String.valueOf(position)+".png");
        if (myCaptureFile.exists()){
            return true;
        }else {
            return false;
        }
    }

    public static String getPath(Context context,Uri uri)
    {
        String[] projection = {MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /*
    判断二维码是否存在
     */
    public static boolean isCodeExist(String name){
        boolean flag = true;
        File directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        directory.mkdir();
        File QR=new File(directory.getAbsolutePath()+"/MessageWall");
        if (!QR.exists()){
            QR.mkdir();
        }
        File file=new File(QR.getAbsolutePath()+"/"+name+".png");
        if (file.exists()){
            flag = true;
        }else {
            flag = false;
        }

        return flag;
    }
    /*
     获取path
     */
    public static Uri getCodePath(String name){

        File directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        directory.mkdir();
        File QR=new File(directory.getAbsolutePath()+"/MessageWall");
        if (!QR.exists()){
            QR.mkdir();
        }
        File file=new File(QR.getAbsolutePath()+"/"+name+".png");

        return Uri.fromFile(file);
    }
    /**
     * byte(字节)根据长度转成kb(千字节)和mb(兆字节)
     *
     * @param bytes
     * @return
     */
    public static String bytesToKB(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        if (returnValue > 1)
            return (returnValue + "MB");
        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        return (returnValue + "KB");
    }
    /*
    将秒格式化
     */
    public static String miaoToFormat(int duration){

        int hour = duration/3600;
        int minute = (duration-hour*3600)/60;
        int second = (duration-hour*3600)%60;

        Log.v("mlgb",format(hour)+":"+format(minute)+":"+format(second));

        return format(hour)+":"+format(minute)+":"+format(second);
    }
    public static String format(int temp){

        if (String.valueOf(temp).length()==1){
            return "0"+String.valueOf(temp);
        }else {
            return String.valueOf(temp);

        }
    }
}
