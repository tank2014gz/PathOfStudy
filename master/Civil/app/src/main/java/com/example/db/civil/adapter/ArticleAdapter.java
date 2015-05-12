package com.example.db.civil.adapter;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.db.civil.R;
import com.example.db.civil.activity.ArticleDetailActivity;
import com.example.db.civil.activity.CommentActivity;
import com.example.db.civil.beans.Article;
import com.example.db.civil.beans.ArticleInfo;
import com.example.db.civil.utlis.AppConstant;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by db on 4/7/15.
 */
public class ArticleAdapter extends BaseAdapter {

    public Context context;

    public List<Article> articles;

    public List<ArticleInfo> articleInfos;

    public DisplayImageOptions options;

    public ImageLoader imageLoader;

    public ArticleAdapter(Context context,List<Article> articles,List<ArticleInfo> articleInfos){
        super();
        this.context=context;
        this.articles=articles;
        this.articleInfos=articleInfos;

        imageLoader=ImageLoader.getInstance();

        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.empty)
                .showImageForEmptyUri(R.drawable.empty)
                .showImageOnFail(R.drawable.error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();


    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Object getItem(int i) {
        return articles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.article_item,null);
            viewHolder=new ViewHolder();
            viewHolder.preview=(ImageView)view.findViewById(R.id.preview);
            viewHolder.from=(TextView)view.findViewById(R.id.description);
            viewHolder.description=(TextView)view.findViewById(R.id.from);
//            viewHolder.collection=(Button)view.findViewById(R.id.collection);
            viewHolder.comment=(Button)view.findViewById(R.id.comment);
            viewHolder.share=(Button)view.findViewById(R.id.share);
            viewHolder.more=(Button)view.findViewById(R.id.more);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }
        if (articles.get(i).getUrl().get(0)!=null){
            imageLoader.displayImage("http://www.edu24ol.com"+articles.get(i).getUrl().get(0), viewHolder.preview, options);
        }else {
            imageLoader.displayImage("http://upload.jianshu.io/daily_images/images/BKx3sA9CumMFpUzQJTFx.jpg",viewHolder.preview,options);
        }

        viewHolder.description.setText(articleInfos.get(i).getDescription().substring(1,articleInfos.get(i).getDescription().length()));
        viewHolder.from.setText("来源于:环球网校");

        final int temp=i;

        viewHolder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Are you sure?");
                pDialog.setCancelText("No");
                pDialog.setConfirmText("Yes");
                pDialog.showCancelButton(true);
                pDialog.setCancelable(true);
                pDialog.setCanceledOnTouchOutside(true);
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        ArticleInfo articleInfo=articleInfos.get(temp);
                        articleInfos.remove(articleInfo);
                        notifyDataSetChanged();
                        sweetAlertDialog.setTitleText("Deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        pDialog.dismiss();
                                    }
                                })
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                    }
                });
                pDialog.show();

            }
        });
        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppConstant.LOGIN_STATUS){
                    Intent intent=new Intent(context, CommentActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else {
                    Toast.makeText(context,"请先登陆!",Toast.LENGTH_SHORT).show();
                }
            }
        });
//        viewHolder.collection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        viewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_SUBJECT, "share");
                intent.putExtra(Intent.EXTRA_TEXT, "share");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(Intent.createChooser(intent,"share"));
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, ArticleDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putStringArrayList("content",articles.get(temp).getContent());
                bundle.putString("description",articleInfos.get(temp).getDescription());
                bundle.putString("articleurl",articleInfos.get(temp).getUrl());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        return view;
    }


    public static class ViewHolder{
        TextView from,description;
        ImageView preview;
        Button collection,comment,share,more;

    }


}
