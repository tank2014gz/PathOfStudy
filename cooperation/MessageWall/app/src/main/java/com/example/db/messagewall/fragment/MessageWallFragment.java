package com.example.db.messagewall.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.example.db.messagewall.activity.AddFileItemActivity;
import com.example.db.messagewall.activity.AddMessageItemActivity;
import com.example.db.messagewall.activity.AddPictureItemActivity;
import com.example.db.messagewall.activity.AddVoiceItemActivity;
import com.example.db.messagewall.adapter.MessageGridAdapter;
import com.example.db.messagewall.api.AppData;
import com.example.db.messagewall.api.MessageHandlerHelper;
import com.example.db.messagewall.view.fab.FloatingActionButton;
import com.example.db.messagewall.view.fab.FloatingActionMenu;
import com.support.android.designlibdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MessageWallFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MessageWallFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageWallFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Bundle bundle;
    public static String CONVERSATION_ID;

    public SwipeRefreshLayout mSwipeRefreshLayout;
    public GridView mGridView;
    public FrameLayout frameLayout;

    com.example.db.messagewall.view.fab.FloatingActionButton floatingActionButton_Text
                                                            ,floatingActionButton_Picture
                                                            ,floatingActionButton_Voice
                                                            ,floatingActionButton_File;
    FloatingActionMenu floatingActionMenu;

    public AVIMConversation avimConversation;
    public NoteHandler noteHandler;
    public MessageGridAdapter messageGridAdapter;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageWallFeagment.
     */
    public static MessageWallFragment newInstance(String param1, String param2) {

        MessageWallFragment fragment = new MessageWallFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    public MessageWallFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            bundle = this.getArguments();
            CONVERSATION_ID = bundle.getString("_ID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_message_wall, container, false);

        /*
        设置背景图片,并且进行裁剪，以适配手机的屏幕
         */
        frameLayout = (FrameLayout)rootView.findViewById(R.id.set_bkg);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.example.db.alife_wallpaper", Context.MODE_PRIVATE);
        String paper = sharedPreferences.getString("paper_path","");
        if (paper.equals("")){

        }else {

            /*
            获取屏幕的参数
             */
            int dw = getActivity().getWindowManager().getDefaultDisplay().getWidth();
            int dh = getActivity().getWindowManager().getDefaultDisplay().getHeight() / 2;
            BitmapFactory.Options factory = new BitmapFactory.Options();
            factory.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(paper,factory);
            /*
            对图片的高度和宽度对应手机屏幕进行匹配
             */
            int wRatio = (int) Math.ceil(factory.outWidth / (float) dw);
            int hRatio = (int) Math.ceil(factory.outHeight / (float) dh);
            if (wRatio > 1 || hRatio > 1) {
                /*
                inSampleSize>1则返回比原图更小的图片
                 */
                if (hRatio > wRatio) {
                    factory.inSampleSize = hRatio;
                } else {
                    factory.inSampleSize = wRatio;
                }
            }
            factory.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(paper,factory);
            if (bitmap==null){
            }else {
            }
            Drawable drawable = new BitmapDrawable(bitmap);
            frameLayout.setBackgroundDrawable(drawable);
        }

        mGridView = (GridView)rootView.findViewById(R.id.gridview);
        mSwipeRefreshLayout=(SwipeRefreshLayout)rootView.findViewById(R.id.refreshlayout);

        floatingActionMenu = (FloatingActionMenu)rootView.findViewById(R.id.fab_menu);
        floatingActionMenu.setClosedOnTouchOutside(true);

        floatingActionButton_Text = (com.example.db.messagewall.view.fab.FloatingActionButton)
                rootView.findViewById(R.id.fab_text);
        floatingActionButton_Picture = (com.example.db.messagewall.view.fab.FloatingActionButton)
                rootView.findViewById(R.id.fab_picture);
        floatingActionButton_Voice = (com.example.db.messagewall.view.fab.FloatingActionButton)
                rootView.findViewById(R.id.fab_voice);
        floatingActionButton_File = (com.example.db.messagewall.view.fab.FloatingActionButton)
                rootView.findViewById(R.id.fab_file);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                convertMsgToList();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        convertMsgToList();

        floatingActionButton_Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                Intent intent = new Intent(getActivity(), AddMessageItemActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        floatingActionButton_Picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                Intent intent = new Intent(getActivity(), AddPictureItemActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        floatingActionButton_Voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                Intent intent = new Intent(getActivity(), AddVoiceItemActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        floatingActionButton_File.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionMenu.close(true);
                Intent intent = new Intent(getActivity(), AddFileItemActivity.class);
                intent.putExtras(bundle);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        public void onFragmentInteraction(Uri uri);
    }

    /*
    处理消息的
     */
    public void convertMsgToList(){

        /*
        获取群组里面的所有人的clientId
         */
        avimConversation = AppData.getIMClient().getConversation(CONVERSATION_ID);
        List<String> clientId = new ArrayList<String>();
        clientId = avimConversation.getMembers();

        /*
        处理消息
         */
        messageGridAdapter = new MessageGridAdapter(getActivity());
        avimConversation.queryMessages(new AVIMMessagesQueryCallback() {
            @Override
            public void done(List<AVIMMessage> list, AVException e) {
                if (e==null){
                    if (list!=null){

                        List<AVIMTypedMessage> avimTypedMessages = new ArrayList<AVIMTypedMessage>(list.size());
                        for (AVIMMessage avimMessage:list){
                            if (avimMessage instanceof AVIMTypedMessage){
                                avimTypedMessages.add((AVIMTypedMessage)avimMessage);
                            }else {
                                Log.v("exception","unexpected message");
                            }
                        }
                        messageGridAdapter.setAvimMessages(avimTypedMessages);
                        messageGridAdapter.notifyDataSetChanged();
                        mGridView.setAdapter(messageGridAdapter);
                    }else {

                    }
                }else {
                    Log.v("db.error6",e.getMessage());
                }
            }
        });



    }

    /*
    外部消息处理类
     */
    public static class NoteHandler{

    }
}
