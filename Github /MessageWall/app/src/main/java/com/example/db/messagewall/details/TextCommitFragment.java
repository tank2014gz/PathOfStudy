package com.example.db.messagewall.details;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.db.messagewall.adapter.CommitAdapter;
import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.view.MaterialDialog;
import com.example.db.messagewall.view.fab.FloatingActionButton;
import com.example.db.messagewall.view.materialedittext.MaterialEditText;
import com.support.android.designlibdemo.R;

import java.util.List;


public class TextCommitFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Bundle bundle;

    public String from,msgId,comment;

    public ListView listView;

    public FloatingActionButton floatingActionButton;
    public MaterialEditText materialEditText;

    public SwipeRefreshLayout mSwipeRefreshLayout;

    public CommitAdapter commitAdapter;

    private OnFragmentInteractionListener mListener;


    public static TextCommitFragment newInstance(String param1, String param2) {
        TextCommitFragment fragment = new TextCommitFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TextCommitFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_text_commit, container, false);

        bundle = this.getArguments();
        if (bundle!=null){
            from = bundle.getString("from");
            msgId = bundle.getString("msgId");
        }

        listView = (ListView)rootView.findViewById(R.id.listview);
        floatingActionButton = (FloatingActionButton)rootView.findViewById(R.id.add);
        mSwipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.refreshlayout);

        AVQuery<AVObject> query = new AVQuery<AVObject>("Commit");
        query.whereEqualTo("from", AVUser.getCurrentUser().getUsername());
        query.whereEqualTo("msgId",msgId);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e==null){
                    if (list.size()!=0){
                        commitAdapter = new CommitAdapter(getActivity(),list);
                        listView.setAdapter(commitAdapter);
                    }else {

                    }
                }else {
                    e.printStackTrace();
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AVQuery<AVObject> query = new AVQuery<AVObject>("Commit");
                query.whereEqualTo("to", from);
                query.whereEqualTo("msgId",msgId);
                query.orderByDescending("createdAt");
                query.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {
                        if (e==null){
                            if (list.size()!=0){
                                commitAdapter = new CommitAdapter(getActivity(),list);
                                listView.setAdapter(commitAdapter);
                            }else {

                            }
                        }else {
                            e.printStackTrace();
                        }
                    }
                });
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialDialog materialDialog = new MaterialDialog(getActivity());
                View view = LayoutInflater.from(getActivity())
                        .inflate(R.layout.comment_input,null);
                materialEditText = (MaterialEditText)view.findViewById(R.id.edit_ask_phone);
                materialDialog.setView(view);
                materialDialog.setCanceledOnTouchOutside(true)
                        .setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                comment = materialEditText.getText().toString();
                                if (comment!=null&&comment.length()!=0){
                                    AVObject avObject = new AVObject("Commit");
                                    avObject.put("to",from);
                                    avObject.put("msgId",msgId);
                                    avObject.put("comment",comment);
                                    avObject.put("from",AVUser.getCurrentUser().getUsername());
                                    avObject.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(AVException e) {
                                            if (e==null){
                                                AppConstant.showSelfToast(getActivity(), "评论成功!");
                                                materialDialog.dismiss();
                                            }else {
                                                e.printStackTrace();
                                                AppConstant.showSelfToast(getActivity(),"评论失败!");
                                                materialDialog.dismiss();
                                            }
                                        }
                                    });
                                }else {
                                    materialDialog.dismiss();
                                }

                            }
                        })
                        .setNegativeButton("Cancel", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                materialDialog.dismiss();
                            }
                        });
                materialDialog.show();
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
