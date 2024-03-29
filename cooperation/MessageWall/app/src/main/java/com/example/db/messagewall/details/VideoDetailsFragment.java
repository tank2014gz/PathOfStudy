package com.example.db.messagewall.details;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.support.android.designlibdemo.R;

import java.util.List;


public class VideoDetailsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Bundle bundle;

    public String content,date,from,url,msgId,size,duration,nichen;

    public TextView mContent,mDate,mFrom,mSize,mDuration,mNiChen;
    public Button playButton;

    private OnFragmentInteractionListener mListener;


    public static VideoDetailsFragment newInstance(String param1, String param2) {
        VideoDetailsFragment fragment = new VideoDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public VideoDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_video_details, container, false);

        mContent = (TextView)rootView.findViewById(R.id.content);
        mDate = (TextView)rootView.findViewById(R.id.date);
        mFrom = (TextView)rootView.findViewById(R.id.from);
        mSize = (TextView)rootView.findViewById(R.id.size);
        mNiChen = (TextView)rootView.findViewById(R.id.nichen);
        mDuration = (TextView)rootView.findViewById(R.id.duration);
        playButton = (Button)rootView.findViewById(R.id.playBtn);

        /*
        给textview设置下划线
         */
        mContent.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        bundle = this.getArguments();
        if (bundle!=null){
            content = bundle.getString("content");
            date = bundle.getString("date");
            from = bundle.getString("from");
            url = bundle.getString("url");
            msgId = bundle.getString("msgId");
            size = bundle.getString("size");
            duration = bundle.getString("long");
            nichen = bundle.getString("nichen");
            Log.v("mlgeb", url);
        }

        if (content!=null&&content.length()!=0){
            mContent.setText(content+".mp4");
            mDate.setText("时间: "+date);
            mFrom.setText("来自: "+from);
            mSize.setText("大小: "+size);
            mDuration.setText("长度: "+duration);
        }

        /*
        显示昵称
         */
        AVQuery<AVObject> query = new AVQuery<AVObject>("NiChen");
        query.whereEqualTo("username", from);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e==null){
                    AVObject avObject = (AVObject)list.get(0);
                    if (avObject.get("nichen").toString()!=null){
                        mNiChen.setText("昵称: "+avObject.get("nichen").toString());
                    }else {
                        mNiChen.setText("昵称: "+from);
                    }
                }else {
                    e.printStackTrace();
                }
            }
        });

        mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri link = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW,link);
                startActivity(intent);
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Intent.ACTION_VIEW);
                intent3.setDataAndType(Uri.parse(url),"video/mp4");
                startActivity(intent3);
            }
        });

        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
