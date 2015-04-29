package com.example.db.tline.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.tline.MainActivity;
import com.example.db.tline.R;
import com.example.db.tline.beans.PictureLineInfo;
import com.example.db.tline.database.PLineSQLiDataBaseHelper;
import com.example.db.tline.utils.AppConstant;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by db on 4/19/15.
 */
public class PictureLineAdapter extends BaseAdapter{

    public Context context;
    public ArrayList<PictureLineInfo> pictureLineInfos;
    public DisplayImageOptions options;
    public ImageLoader imageLoader;

    public PictureLineAdapter(Context context,ArrayList<PictureLineInfo> pictureLineInfos){
        super();
        this.context=context;
        this.pictureLineInfos=pictureLineInfos;
        imageLoader=ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.login)
                .showImageForEmptyUri(R.drawable.login)
                .showImageOnFail(R.drawable.login)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public int getCount() {
        return pictureLineInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return pictureLineInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.picture_line_item,null);
            viewHolder=new ViewHolder();
            viewHolder.mTitle=(TextView)view.findViewById(R.id.title);
            viewHolder.mClock=(TextView)view.findViewById(R.id.clock);
            viewHolder.mTimePrint=(TextView)view.findViewById(R.id.timeprint);
            viewHolder.mImageView=(ImageView)view.findViewById(R.id.description);
            viewHolder.mShare=(Button)view.findViewById(R.id.btn_share);
            viewHolder.mEdit=(Button)view.findViewById(R.id.btn_edit);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }

        final PictureLineInfo pictureLineInfo=pictureLineInfos.get(i);

        viewHolder.mTitle.setText(pictureLineInfo.getTitle());
        viewHolder.mTimePrint.setText("在平静的日子里,我安然无恙...");
        if (pictureLineInfo.getUri().length()!=0){
            imageLoader.displayImage(pictureLineInfo.getUri(), viewHolder.mImageView, options);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        viewHolder.mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        viewHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PLineSQLiDataBaseHelper pLineSQLiDataBaseHelper=new PLineSQLiDataBaseHelper(context);
                SQLiteDatabase sqLiteDatabase=pLineSQLiDataBaseHelper.getWritableDatabase();
                sqLiteDatabase.delete("pictureline","title like ?",new String[]{pictureLineInfo.getTitle()});
                sqLiteDatabase.close();
                notifyDataSetChanged();

                Intent intent=new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });

        return view;
    }
    public static class ViewHolder{
        TextView mTitle,mClock,mTimePrint;
        ImageView mImageView;
        Button mShare,mEdit,mMsg;
    }
}
