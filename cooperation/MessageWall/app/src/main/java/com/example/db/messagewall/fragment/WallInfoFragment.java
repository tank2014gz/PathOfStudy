package com.example.db.messagewall.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationMemberCountCallback;
import com.example.db.messagewall.api.AppData;
import com.example.db.messagewall.view.ALifeToast;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.support.android.designlibdemo.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WallInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WallInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WallInfoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DisplayImageOptions options;
    public ImageLoader imageLoader;

    public Bundle bundle;
    public static String CONVERSATION_ID;

    public TextView mWallName,mWallDate,mWallCount;
    public ImageView mImageView;
    public LinearLayout linearLayout;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WallInfoFragment.
     */

    public static WallInfoFragment newInstance(String param1, String param2) {

        WallInfoFragment fragment = new WallInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    public WallInfoFragment() {

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

        View rootView = inflater.inflate(R.layout.fragment_wall_info, container, false);

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

        mWallName = (TextView)rootView.findViewById(R.id.wall_name);
        mWallDate = (TextView)rootView.findViewById(R.id.wall_date);
        mWallCount = (TextView)rootView.findViewById(R.id.wall_count);
        mImageView = (ImageView)rootView.findViewById(R.id.wall_code);
        linearLayout = (LinearLayout)rootView.findViewById(R.id.btn_refresh);

        final AVIMConversation avimConversation = AppData.getIMClient().getConversation(CONVERSATION_ID);
        mWallName.setText(avimConversation.getAttribute("name").toString());
        mWallDate.setText(avimConversation.getAttribute("date").toString());
        avimConversation.getMemberCount(new AVIMConversationMemberCountCallback() {
            @Override
            public void done(Integer integer, AVException e) {
                if (e==null){
                    mWallCount.setText(String.valueOf(integer)+"人");
                }else {

                }
            }
        });

        avimConversation.fetchInfoInBackground(new AVIMConversationCallback() {
            @Override
            public void done(AVException e) {
                if (e==null){
                    imageLoader.displayImage(AppData.getIMClient()
                            .getConversation(CONVERSATION_ID)
                            .getAttribute("link_url")
                            .toString(),mImageView,options);
                }else {
                    ALifeToast.makeText(getActivity()
                            , "请重新加载！"
                            , ALifeToast.ToastType.SUCCESS
                            , ALifeToast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avimConversation.fetchInfoInBackground(new AVIMConversationCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e==null){
                            imageLoader.displayImage(AppData.getIMClient()
                                    .getConversation(CONVERSATION_ID)
                                    .getAttribute("link_url")
                                    .toString(),mImageView,options);
                        }else {
                            ALifeToast.makeText(getActivity()
                                    , "请重新加载！"
                                    , ALifeToast.ToastType.SUCCESS
                                    , ALifeToast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
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

}
