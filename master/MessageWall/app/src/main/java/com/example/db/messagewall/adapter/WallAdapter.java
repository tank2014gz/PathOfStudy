package com.example.db.messagewall.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.db.messagewall.WallInfo;
import com.example.db.messagewall.activity.MainActivity;
import com.example.db.messagewall.view.CircleImageView;
import com.support.android.designlibdemo.R;

import java.util.List;

/**
 * Created by db on 7/13/15.
 */
public class WallAdapter extends RecyclerView.Adapter<WallAdapter.ViewHolder> {

    private List<WallInfo> items;
    public Context context;

    public WallAdapter(Context context){
        super();
//        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wall_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView circleImageView;
        TextView name,description,count;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            circleImageView = (CircleImageView)itemView.findViewById(R.id.img);
            name = (TextView)itemView.findViewById(R.id.wall_name);
            description = (TextView)itemView.findViewById(R.id.wall_description);
            count = (TextView)itemView.findViewById(R.id.wall_count);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.circle);
        }
    }

}
