package com.example.db.messagewall.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.db.messagewall.activity.MainActivity;
import com.example.db.messagewall.api.AppData;
import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.utils.DownloadRunnable;
import com.example.db.messagewall.view.ALifeToast;
import com.example.db.messagewall.view.CircleButton;
import com.example.db.messagewall.view.MaterialDialog;
import com.example.db.messagewall.view.materialloadingprogressbar.CircleProgressBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.support.android.designlibdemo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

/**
 * Created by db on 7/18/15.
 */
public class WallPaperGridAdapter extends BaseAdapter{

    public Context context;
    public List<String> list = new ArrayList<String>();
    public String path;

    /*
    设置flag实现只能单选
    设置isDownloaded来控制是否下载了文件
     */
    public boolean flag = false;

    public DisplayImageOptions options;
    public ImageLoader imageLoader;

    public MaterialDialog materialDialog;
    public TextView textView;

    public Handler handler;

    public WallPaperGridAdapter(final Context context){
        super();
        this.context = context;
        initList();

        materialDialog = new MaterialDialog((MainActivity)context);
        View view = LayoutInflater.from(context).inflate(R.layout.material_progress,null);
        CircleProgressBar circleProgressBar = (CircleProgressBar)view.findViewById(R.id.dialog);
        textView = (TextView)view.findViewById(R.id.text);
        circleProgressBar.setColorSchemeResources(android.R.color.holo_orange_light);
        materialDialog.setView(view);
        materialDialog.setCanceledOnTouchOutside(true);


        imageLoader = ImageLoader.getInstance();

        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.phone)
                .showImageForEmptyUri(R.drawable.phone)
                .showImageOnFail(R.drawable.phone)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.paper_item,null);
            viewHolder = new ViewHolder();
            viewHolder.linearLayout = (CircleButton)convertView.findViewById(R.id.btn_share);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.img_item);
            viewHolder.download = (CircleButton)convertView.findViewById(R.id.btn_download);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==0x123){
                    textView.setText((String)msg.obj);
                    materialDialog.dismiss();
                    viewHolder.download.setVisibility(ViewGroup.GONE);
                }else if (msg.what==0x122){
                    textView.setText((String)msg.obj);
                    materialDialog.dismiss();
                    viewHolder.download.setVisibility(ViewGroup.VISIBLE);
                }
            }
        };

        imageLoader.displayImage(list.get(position), viewHolder.imageView, options);

        if (AppConstant.isExist(position)){
            viewHolder.download.setVisibility(ViewGroup.GONE);
        }else {
            viewHolder.download.setVisibility(ViewGroup.VISIBLE);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppConstant.isExist(position)){
                    if (viewHolder.linearLayout.getVisibility()==ViewGroup.VISIBLE){

                        viewHolder.linearLayout.setVisibility(ViewGroup.GONE);
                        setPath("");
                        flag = false;

                    }else if (viewHolder.linearLayout.getVisibility()==ViewGroup.GONE&&flag==false){

                        viewHolder.linearLayout.setVisibility(ViewGroup.VISIBLE);
                        /*
                        得到本地文件的路径
                        */
                        File directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                        directory.mkdir();
                        File QR=new File(directory.getAbsolutePath()+"/MessageWall/Paper");
                        setPath(QR.getAbsolutePath()+"/"+"paper_bkg"+String.valueOf(position)+".png");
                        flag = true;
                    }
                }else {
                    ALifeToast.makeText((MainActivity)context
                            , "请先下载！"
                            , ALifeToast.ToastType.SUCCESS
                            , ALifeToast.LENGTH_SHORT)
                            .show();
                }

            }
        });

        viewHolder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                下载文件，隐藏输入法
                */
                if (AppConstant.isExist(position)){

                }else {
                    materialDialog.show();
                    new Thread(new DownloadRunnable(handler,list.get(position),"paper_bkg"+String.valueOf(position))).start();
                }
            }
        });

        return convertView;
    }

    public static class ViewHolder{
        CircleButton linearLayout,download;
        ImageView imageView;
    }

    public void initList(){
        list.add("http://p5.image.hiapk.com/uploads/allimg/150421/7730-150421105135.jpg");
        list.add("http://p4.image.hiapk.com/uploads/allimg/150119/7730-150119154322.jpg");
        list.add("http://p1.image.hiapk.com/uploads/allimg/141015/7730-141015164107.jpg");
        list.add("http://p1.image.hiapk.com/uploads/allimg/131216/23-131216113G6.jpg");
        list.add("http://p1.image.hiapk.com/uploads/allimg/131101/23-1311011H102-52.jpg");
        list.add("http://p4.image.hiapk.com/uploads/allimg/130904/9-130Z41T519-51.jpg");
        list.add("http://p5.image.hiapk.com/uploads/allimg/150724/7730-150H41I033.jpg");
        list.add("http://p1.image.hiapk.com/uploads/allimg/150625/7730-1506251G327.jpg");
        list.add("http://p3.image.hiapk.com/uploads/allimg/150727/7730-150HG60135.jpg");
        list.add("http://p5.image.hiapk.com/uploads/allimg/150306/7730-150306140551.jpg");
        list.add("http://p3.image.hiapk.com/uploads/allimg/150211/7730-150211142A8.png");
        list.add("http://p4.image.hiapk.com/uploads/allimg/150625/7730-150625160F7.jpg");
        list.add("http://p2.image.hiapk.com/uploads/allimg/141205/7730-141205163037-51.jpg");


    }
    public void setPath(String path){
        this.path = path;
    }
    public String getPath(){
        return this.path;
    }
}
