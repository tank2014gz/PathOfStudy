package com.haitou.xiaoyoupai.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haitou.xiaoyoupai.R;

/**
 * Created by db on 8/27/15.
 */
public class ActivityHeadAdapter extends RecyclerView.Adapter<ActivityHeadAdapter.ViewHolder> {

    public Context context;


    public ActivityHeadAdapter(Context context){
        super();
        this.context = context;
    }

    @Override
    public ActivityHeadAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommand_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ActivityHeadAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.img);
            textView = (TextView)itemView.findViewById(R.id.content);
        }
    }
}
