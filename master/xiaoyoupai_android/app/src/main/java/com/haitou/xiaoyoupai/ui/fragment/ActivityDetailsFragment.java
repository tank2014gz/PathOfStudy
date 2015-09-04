package com.haitou.xiaoyoupai.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.haitou.xiaoyoupai.R;
import com.haitou.xiaoyoupai.ui.adapter.CommentAdapter;
import com.haitou.xiaoyoupai.ui.widget.CircleImageView;
import com.haitou.xiaoyoupai.ui.widget.OverScrollView;


public class ActivityDetailsFragment extends MainFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View curView = null;

    public ListView listView;



    private OnFragmentInteractionListener mListener;

    public static ActivityDetailsFragment newInstance(String param1, String param2) {
        ActivityDetailsFragment fragment = new ActivityDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ActivityDetailsFragment() {
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
        if (null != curView) {
            logger.d("curView is not null, remove it");
            ((ViewGroup) curView.getParent()).removeView(curView);
        }

        curView = inflater.inflate(R.layout.fragment_activity_details, topContentView);
        super.init(curView);
        initTitleView();

        listView = (ListView)curView.findViewById(R.id.CommentListView);

        View mHeadView = inflater.inflate(R.layout.comment_head,null);
        listView.addHeaderView(mHeadView);

        CommentAdapter commentAdapter = new CommentAdapter(getActivity());
        listView.setAdapter(commentAdapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if (i+i1==i2){
                    curView.findViewById(R.id.btn_layout).setVisibility(View.INVISIBLE);
                }else {
                    curView.findViewById(R.id.btn_layout).setVisibility(View.VISIBLE);
                }
            }
        });

        topLeftContainerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        topRightTitleTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //分享的内容

            }
        });

        return curView;
    }

    private void initTitleView() {
        // 设置标题
        setTopRightText("分享");
        setTopTitle("详情");
        setTopLeftText("返回");
        setTopLeftButton(R.drawable.back_pop);
    }

    @Override
    protected void initHandler() {

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
