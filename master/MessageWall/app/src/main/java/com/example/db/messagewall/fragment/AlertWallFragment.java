package com.example.db.messagewall.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.example.db.messagewall.adapter.AlterWallAdapter;
import com.example.db.messagewall.adapter.WallAdapter;
import com.example.db.messagewall.api.AppData;
import com.support.android.designlibdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlertWallFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AlertWallFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlertWallFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public RecyclerView recyclerView;

    public Bundle bundle;
    public static String CONVERSATION_ID;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlertWallFragment.
     */

    public static AlertWallFragment newInstance(String param1, String param2) {

        AlertWallFragment fragment = new AlertWallFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    public AlertWallFragment() {

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

        View rootView =  inflater.inflate(R.layout.fragment_alert_wall, container, false);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));


        /*
        直接加载所有的留言墙
         */
        List<String> list = new ArrayList<String>();
        list.add(AVUser.getCurrentUser().getUsername());

        AppData.setClientIdToPre(AVUser.getCurrentUser().getUsername());

        final AVIMClient avimClient0 = AppData.getIMClient();
        final List<String> queryClientIds = new ArrayList<String>();
        queryClientIds.addAll(list);

        avimClient0.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVException e) {
                if (e == null) {
                    AVIMConversationQuery query = avimClient.getQuery();
                    query.containsMembers(queryClientIds);
                    query.findInBackground(new AVIMConversationQueryCallback() {
                        @Override
                        public void done(List<AVIMConversation> list, AVException e) {
                            if (null != e) {
                                Log.v("db.error4", e.getMessage());
                            } else {
                                recyclerView.setAdapter(new AlterWallAdapter(getActivity(), list));
                                Log.v("db.cnm2", String.valueOf(list.size()));
                            }
                        }
                    });
                } else {
                    Log.v("db.error11", e.getMessage());
                }
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
