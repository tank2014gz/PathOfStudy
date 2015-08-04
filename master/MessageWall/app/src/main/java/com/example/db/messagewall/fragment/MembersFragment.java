package com.example.db.messagewall.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.Conversation;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationMemberCountCallback;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.db.messagewall.adapter.MembersAdapter;
import com.example.db.messagewall.api.AppData;
import com.example.db.messagewall.bean.MemberInfo;
import com.example.db.messagewall.view.CircleImageView;
import com.support.android.designlibdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MembersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MembersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MembersFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SwipeMenuListView mlistView;
    public TextView textView;
    public List<MemberInfo> memberInfos;
    public MembersAdapter membersAdapter;

    public Bundle bundle;
    public static String CONVERSATION_ID;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MembersFragment.
     */

    public static MembersFragment newInstance(String param1, String param2) {

        MembersFragment fragment = new MembersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    public MembersFragment() {

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

        View rootView = inflater.inflate(R.layout.fragment_members, container, false);

        mlistView = (SwipeMenuListView)rootView.findViewById(R.id.listview);

        View mHeadView = LayoutInflater.from(getActivity())
                .inflate(R.layout.members_head,null);
        /*
        成员个数
         */
        textView = (TextView)mHeadView.findViewById(R.id.count);
        final AVIMConversation avimConversation0 = AppData.getIMClient().getConversation(CONVERSATION_ID);
        avimConversation0.fetchInfoInBackground(new AVIMConversationCallback() {
            @Override
            public void done(AVException e) {
                if (e==null){
                 avimConversation0.getMemberCount(new AVIMConversationMemberCountCallback() {
                     @Override
                     public void done(Integer integer, AVException e) {
                         if (e==null){
                             textView.setText("一共有"+String.valueOf(integer)+"个成员");
                         }
                     }
                 });
                }
            }
        });
        mlistView.addHeaderView(mHeadView);

        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem item1 = new SwipeMenuItem(
                        getActivity());
                item1.setBackground(R.drawable.rect_bkg);
                item1.setWidth(dp2px(90));
                item1.setHeight(dp2px(65));
                item1.setIcon(R.drawable.ic_vertical_align_top_white_24dp);
                menu.addMenuItem(item1);

                SwipeMenuItem item2 = new SwipeMenuItem(
                        getActivity());
                item2.setBackground(R.drawable.rect_bkg0);
                item2.setWidth(dp2px(90));
                item2.setIcon(R.drawable.ic_delete_white_24dp);
                menu.addMenuItem(item2);
            }
        };

        mlistView.setMenuCreator(swipeMenuCreator);
        mlistView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index){
                    case 0:
                        /*
                        置顶
                         */

                        break;
                    case 1:
                        /*
                        删除
                         */
                        AVIMConversation avimConversation = AppData.getIMClient().getConversation(CONVERSATION_ID);
                        List<String> list = new ArrayList<String>();
                        final MemberInfo memberInfo = (MemberInfo)membersAdapter.getItem(position);
                        list.add(memberInfo.getName());
                        avimConversation.kickMembers(list, new AVIMConversationCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e==null){
                                    membersAdapter.memberInfos.remove(memberInfo);
                                    membersAdapter.notifyDataSetChanged();
                                }else {
                                    Log.v("db.error7",e.getMessage());
                                }
                            }
                        });
                        break;
                }
                return false;
            }
        });

        memberInfos = new ArrayList<MemberInfo>();

        AVIMConversation avimConversation = AppData.getIMClient().getConversation(CONVERSATION_ID);
        List<String> list = new ArrayList<String>();
        list = avimConversation.getMembers();
        for (int i=0;i<list.size();i++){
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setName(list.get(i));
            memberInfo.setDate(avimConversation.getAttribute("name").toString());
            memberInfos.add(memberInfo);
        }
        membersAdapter = new MembersAdapter(getActivity(),memberInfos);



        mlistView.setAdapter(membersAdapter);


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
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
