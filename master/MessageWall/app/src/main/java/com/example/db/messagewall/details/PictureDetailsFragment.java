package com.example.db.messagewall.details;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.db.messagewall.activity.PictureDetailsActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.support.android.designlibdemo.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PictureDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PictureDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PictureDetailsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Bundle bundle;

    public String content,date,from,url,size,nichen;

    public TextView mContent,mDate,mFrom,mSize,mNiChen;
    public ImageView imageView;

    public DisplayImageOptions options;
    public ImageLoader imageLoader;

    private OnFragmentInteractionListener mListener;


    public static PictureDetailsFragment newInstance(String param1, String param2) {
        PictureDetailsFragment fragment = new PictureDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PictureDetailsFragment() {

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

        View rootView = inflater.inflate(R.layout.fragment_picture_details, container, false);

        mContent = (TextView)rootView.findViewById(R.id.content);
        mDate = (TextView)rootView.findViewById(R.id.date);
        mFrom = (TextView)rootView.findViewById(R.id.from);
        mSize = (TextView)rootView.findViewById(R.id.size);
        mNiChen = (TextView)rootView.findViewById(R.id.nichen);
        imageView = (ImageView)rootView.findViewById(R.id.img);

        /*
        给textview设置下划线
         */
        mContent.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.photo3)
                .showImageForEmptyUri(R.drawable.photo3)
                .showImageOnFail(R.drawable.photo3)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        bundle = this.getArguments();
        if (bundle!=null){
            content = bundle.getString("content");
            date = bundle.getString("date");
            from = bundle.getString("from");
            url = bundle.getString("url");
            size = bundle.getString("size");
            nichen = bundle.getString("nichen");
        }

        if (content!=null&&content.length()!=0){
            mContent.setText(content+".png");
            mDate.setText("时间: "+date);
            mFrom.setText("来自: "+from);
            mSize.setText("大小: "+size);
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

        if (url!=null){
            imageLoader.displayImage(url,imageView,options);
        }



        mContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri link = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW,link);
                startActivity(intent);
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
