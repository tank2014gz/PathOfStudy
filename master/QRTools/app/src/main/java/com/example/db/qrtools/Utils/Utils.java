package com.example.db.qrtools.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.db.qrtools.R;
import com.example.db.qrtools.Beans.getTotalInfo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import static android.text.format.DateFormat.format;
import static android.text.format.Formatter.formatFileSize;

/**
 * Created by db on 3/1/15.
 */
public class Utils {
    public static int QR_WIDTH=250;
    public static int QR_HEIGHT=250;
    public static String SELF_DEFINE="01";
    public static String PICTURE="02";
    public static String ID_CARD="03";
    public static String MAIL="04";
    public static String MESSAGE="05";
    public static String URL_ADDRESS="06";
    //产生二维码图片
    public static Bitmap createQRImage(String content){
        Bitmap bitmap=null;
        try{
            QRCodeWriter qrCodeWriter=new QRCodeWriter();
            if (content==null||"".equals(content)||content.length()<1){
                return null;
            }
            //将输入的文本转化成文本
            BitMatrix bitMatrix=qrCodeWriter.encode(content, BarcodeFormat.QR_CODE,Utils.QR_WIDTH,Utils.QR_HEIGHT);
            Hashtable<EncodeHintType,String> hashtable=new Hashtable<EncodeHintType,String>();
            hashtable.put(EncodeHintType.CHARACTER_SET,"utf-8");
            BitMatrix bitMatrix1=new QRCodeWriter().encode(content,BarcodeFormat.QR_CODE,Utils.QR_WIDTH,Utils.QR_HEIGHT,hashtable);
            int[] pixels=new int[Utils.QR_WIDTH*Utils.QR_HEIGHT];
            for (int y=0;y<Utils.QR_HEIGHT;y++){
                for (int x=0;x<Utils.QR_WIDTH;x++){
                    if (bitMatrix1.get(x,y)){
                        pixels[y * Utils.QR_WIDTH + x] = 0xff000000;
                    }else {
                        pixels[y * Utils.QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            bitmap=Bitmap.createBitmap(Utils.QR_WIDTH,Utils.QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, Utils.QR_WIDTH, 0, 0, Utils.QR_WIDTH, Utils.QR_HEIGHT);

        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
    //保存二维码图片
    public static void saveQRImage(String content,Bitmap bitmap,String category){
        File directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        directory.mkdir();
        File QR=new File(directory.getAbsolutePath()+"/QRTools");
        if (!QR.exists()){
            QR.mkdir();
        }
        File file=new File(QR.getAbsolutePath()+"/"+category+"_"+content.toString()+".png");
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
    }
    //删除二维码图片
    public static void deleteQRImage(String content,String category){
        File directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        directory.mkdir();
        File QR=new File(directory.getAbsolutePath()+"/QRTools");
        if (!QR.exists()){
            QR.mkdir();
        }
        File file=new File(QR.getAbsolutePath()+"/"+category+"_"+content.toString()+".png");
        if (file.exists()){
            file.delete();
        }
    }
    //分享二维码图片
    public static void shareQRImage(Context context,String content,String category){
        File directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        directory.mkdir();
        File QR=new File(directory.getAbsolutePath()+"/QRTools");
        if (!QR.exists()){
            QR.mkdir();
        }
        File temp=new File(QR.getAbsolutePath()+"/"+category+"_"+content.toString()+".png");
        String path=null;
        path=temp.getAbsolutePath();
        Intent intent=new Intent(Intent.ACTION_SEND);
        if (path==null||path.equals("")){
            intent.setType("text/plain");//纯文本
        }else {
            File file=new File(path);
            if(file.exists()&&file.isFile()&&file!=null){
                intent.setType("image/png");
                Uri uri=Uri.fromFile(file);
                intent.putExtra(Intent.EXTRA_STREAM,uri);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, "share");
        intent.putExtra(Intent.EXTRA_TEXT, "share");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent,"share"));
    }
    //产生沉浸式状态栏
    public static void setStatus(boolean on,Activity context){
        Window window=context.getWindow();
        WindowManager.LayoutParams layoutParams=window.getAttributes();
        final int bits=WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on){
            layoutParams.flags |=bits;
        }else {
            layoutParams.flags &= ~bits;
        }
        window.setAttributes(layoutParams);
    }
    //返回自定义Toast的view
    public static View selfDefineToast(Context context){
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View RootView=layoutInflater.inflate(R.layout.toast_item,null);
        return RootView;
    }
    //获取目录下每张照片的详细信息
    public static ArrayList<getTotalInfo> getTotalInfos(Context context){
        ArrayList<getTotalInfo> getTotalInfos=new ArrayList<getTotalInfo>();
        String directory="/storage/sdcard0/QRTools";
        File QRTools=new File(directory);
        File[] files=QRTools.listFiles();
        for (int i=0;i<files.length;i++){
            try{
                getTotalInfo totalInfo=new getTotalInfo();
                totalInfo.setPath(files[i].getAbsolutePath());
                totalInfo.setSize(formatFileSize(context, new FileInputStream(files[i]).available()));
                totalInfo.setTime(String.valueOf(format("MM/dd/yy h:mmaa", files[i].lastModified())));
                totalInfo.setHidden(files[i].isHidden());
                totalInfo.setReadable(files[i].canRead());
                totalInfo.setWriteable(files[i].canWrite());
                getTotalInfos.add(totalInfo);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return getTotalInfos;
    }
    public static Bitmap[] getBitmap(){
        String directory="/storage/sdcard0/QRTools";
        File file=new File(directory);
        File[] files=file.listFiles();
        Bitmap[] bitmaps=new Bitmap[files.length];
        InputStream inputStream=null;
        for (int i=0;i<files.length;i++){
        try {
            inputStream=new FileInputStream(files[i]);
            bitmaps[i]=BitmapFactory.decodeStream(inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
        }
        return bitmaps;
    }
    public static boolean isSaved(String content,String category){
        boolean flag=false;
        File directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        directory.mkdir();
        File QR=new File(directory.getAbsolutePath()+"/QRTools");
        if (!QR.exists()){
            QR.mkdir();
        }
        File[] files=QR.listFiles();
        for (int i=0;i<files.length;i++){
            if (files[i].getAbsolutePath().equals(QR.getAbsolutePath()+"/"+category+"_"+content.toString()+".png")){
                flag=true;
            }
        }
        return flag;
    }
}
