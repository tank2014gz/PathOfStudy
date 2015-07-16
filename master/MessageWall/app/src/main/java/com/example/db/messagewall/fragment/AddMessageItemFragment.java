package com.example.db.messagewall.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.Conversation;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.example.db.messagewall.api.AppData;
import com.example.db.messagewall.view.ALifeToast;
import com.example.db.messagewall.view.materialedittext.MaterialEditText;
import com.support.android.designlibdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddMessageItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddMessageItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMessageItemFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public LinearLayout mSelect;
    public MaterialEditText mWallContent;
    public FloatingActionButton floatingActionButton;
    public ImageView mImg;

    public String wallcontent;

    public Bundle bundle;
    public static String CONVERSATION_ID;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddMessageItemFragment.
     */

    public static AddMessageItemFragment newInstance(String param1, String param2) {

        AddMessageItemFragment fragment = new AddMessageItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    public AddMessageItemFragment() {
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
        View rootview =  inflater.inflate(R.layout.fragment_add_message_item, container, false);

        mSelect = (LinearLayout)rootview.findViewById(R.id.btn_select);
        mWallContent = (MaterialEditText)rootview.findViewById(R.id.edit_wall_content);
        mImg = (ImageView)rootview.findViewById(R.id.select_img);
        floatingActionButton = (FloatingActionButton)rootview.findViewById(R.id.add);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wallcontent = mWallContent.getText().toString();

                if (wallcontent!=null||wallcontent.length()!=0){

                    /*
                    发送消息
                     */
                    AVIMMessage avimMessage = new AVIMMessage();
                    avimMessage.setContent(wallcontent);
                    /*
                    通过CONVERSATION_ID找到群组,来通过群组发布消息
                     */
                    AVIMConversation avimConversation = AppData.getIMClient().getConversation(CONVERSATION_ID);
                    avimConversation.sendMessage(avimMessage, AVIMConversation.NONTRANSIENT_MESSAGE_FLAG, new AVIMConversationCallback() {
                        @Override
                        public void done(AVException e) {

                            if (null == e) {
                                ALifeToast.makeText(getActivity()
                                        , "添加成功！"
                                        , ALifeToast.ToastType.SUCCESS
                                        , ALifeToast.LENGTH_SHORT)
                                        .show();

                                FragmentTransaction fragmentTransaction = getActivity()
                                        .getSupportFragmentManager()
                                        .beginTransaction();
                                MessageWallFeagment messageWallFeagment = new MessageWallFeagment();
                                fragmentTransaction.replace(R.id.container, messageWallFeagment).commit();

                            } else {
                                ALifeToast.makeText(getActivity()
                                        , "添加失败！"
                                        , ALifeToast.ToastType.SUCCESS
                                        , ALifeToast.LENGTH_SHORT)
                                        .show();

                                FragmentTransaction fragmentTransaction = getActivity()
                                        .getSupportFragmentManager()
                                        .beginTransaction();
                                MessageWallFeagment messageWallFeagment = new MessageWallFeagment();
                                fragmentTransaction.replace(R.id.container, messageWallFeagment).commit();

                                Log.v("db.error5", e.getMessage());
                            }
                        }
                    });

                }
            }
        });

        return rootview;
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

}
