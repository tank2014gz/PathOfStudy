package com.example.db.slor.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.etsy.android.grid.util.DynamicHeightTextView;
import com.example.db.slor.MainActivity;
import com.example.db.slor.R;
import com.example.db.slor.beans.Article;
import com.example.db.slor.fragment.BeautifulArticleFragment;
import com.example.db.slor.service.MyService;

import java.util.List;
import java.util.Random;

/**
 * Created by db on 3/23/15.
 */
public class ArticleListViewAdapter extends BaseAdapter {

    public List<Article> articles;

    public Context context;

    public ArticleListViewAdapter(Context context,List<Article> articles){
        super();
        this.context=context;
        this.articles=articles;
    }

    @Override
    public int getCount() {
        return articles != null ? articles.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        if (articles!=null){
            return articles.get(i);
        }else {
            return null;
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        final ViewHolder viewHolder;
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.article_adapter_item,null);
            viewHolder=new ViewHolder();
            viewHolder.mButtonDelete=(Button)view.findViewById(R.id.article_delete);
            viewHolder.mButtonCollection=(Button)view.findViewById(R.id.article_collection);
            viewHolder.mButtonShare=(Button)view.findViewById(R.id.article_share);
            viewHolder.mImageView=(ImageView)view.findViewById(R.id.user_photo);
            viewHolder.mUserTextView=(TextView)view.findViewById(R.id.user_name);
            viewHolder.mDateTextView=(TextView)view.findViewById(R.id.user_date);
            viewHolder.mArticleTitleTV=(TextView)view.findViewById(R.id.article_title);
            viewHolder.mArticleContentTV=(TextView)view.findViewById(R.id.article_content);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }


        final Article article=articles.get(i);

        if (article!=null) {
            if (article.getOwnSee()) {
                AVUser avUser = article.getAVUser("user");


                AVQuery<AVObject> query = new AVQuery<AVObject>("_User");
                query.getInBackground(avUser.getObjectId(), new GetCallback<AVObject>() {
                    @Override
                    public void done(AVObject avObject, AVException e) {
                        if (e == null) {
                            AVUser temp = (AVUser) avObject;
                            String user_name = null;
                            user_name = temp.getUsername();
                            if (user_name != null) {
                                viewHolder.mUserTextView.setText(user_name);
                            }

                        } else {
                            Log.v("reason", e.getMessage());
                        }
                    }
                });

                final GetCallback<AVObject> getCallback=new GetCallback<AVObject>() {
                    @Override
                    public void done(AVObject avObject, AVException e) {
                        if (avObject!=null&&avObject.getBoolean("own_see")){
                            avObject.put("own_see",false);
                            avObject.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e==null){
                                        Intent intent=new Intent();
                                        intent.setAction("com.db.slor");
                                        intent.putExtra("command","refresh");
                                        context.sendBroadcast(intent);
                                        Toast.makeText(context,"删除成功!",Toast.LENGTH_SHORT).show();
                                    }else {
                                        Log.v("result1",e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                };
                viewHolder.mDateTextView.setText(article.getDate().toString().trim());
                viewHolder.mArticleTitleTV.setText(article.getTitle().toString().trim());
                viewHolder.mArticleContentTV.setText(article.getContent().toString().trim());
                viewHolder.mButtonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MyService.fetchTodoById(article.getObjectId(),getCallback);
                    }
                });

            }else {
                view.setVisibility(View.GONE);
            }
        }
        return view;

    }
    public static class ViewHolder
    {
        ImageView mImageView;
        TextView mUserTextView;
        TextView mDateTextView;
        TextView mArticleTitleTV;
        TextView mArticleContentTV;
        Button mButtonShare,mButtonDelete,mButtonCollection;
    }


}
