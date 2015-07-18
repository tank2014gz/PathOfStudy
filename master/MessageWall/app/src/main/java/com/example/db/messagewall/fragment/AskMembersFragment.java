package com.example.db.messagewall.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.example.db.messagewall.api.AppData;
import com.example.db.messagewall.utils.ShareData;
import com.example.db.messagewall.view.ALifeToast;
import com.example.db.messagewall.view.materialedittext.MaterialEditText;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.support.android.designlibdemo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AskMembersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AskMembersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AskMembersFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DisplayImageOptions options;
    public ImageLoader imageLoader;

    public MaterialEditText mAskName;
    public ImageView mAskCode;
    public LinearLayout mShare;
    public FloatingActionButton floatingActionButton;

    public String askphone;

    public Bundle bundle;
    public static String CONVERSATION_ID;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AskMembersFragment.
     */

    public static AskMembersFragment newInstance(String param1, String param2) {

        AskMembersFragment fragment = new AskMembersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    public AskMembersFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_ask_members, container, false);

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

        mAskName = (MaterialEditText)rootView.findViewById(R.id.edit_ask_phone);
        mAskCode = (ImageView)rootView.findViewById(R.id.edit_ask_code);
        mShare = (LinearLayout)rootView.findViewById(R.id.btn_share);
        floatingActionButton = (FloatingActionButton)rootView.findViewById(R.id.fab);

        imageLoader.displayImage(AppData.getIMClient()
                .getConversation(CONVERSATION_ID)
                .getAttribute("link_url")
                .toString(),mAskCode,options);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askphone = mAskName.getText().toString();
                if (askphone!=null){
                    List<String> list = new ArrayList<String>();
                    list.add(askphone);
                    AVIMConversation avimConversation = AppData.getIMClient().getConversation(CONVERSATION_ID);
                    avimConversation.addMembers(list, new AVIMConversationCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e==null){

                                FragmentTransaction fragmentTransaction = getActivity()
                                                                            .getSupportFragmentManager()
                                                                            .beginTransaction();
                                MessageWallFeagment messageWallFeagment = new MessageWallFeagment();
                                messageWallFeagment.setArguments(bundle);
                                fragmentTransaction.replace(R.id.container,messageWallFeagment).commit();

                                ALifeToast.makeText(getActivity()
                                        , "添加成功！"
                                        , ALifeToast.ToastType.SUCCESS
                                        , ALifeToast.LENGTH_SHORT)
                                        .show();
                            }else {

                                FragmentTransaction fragmentTransaction = getActivity()
                                                                            .getSupportFragmentManager()
                                                                            .beginTransaction();
                                MessageWallFeagment messageWallFeagment = new MessageWallFeagment();
                                messageWallFeagment.setArguments(bundle);
                                fragmentTransaction.replace(R.id.container,messageWallFeagment).commit();

                                ALifeToast.makeText(getActivity()
                                        , "添加失败！"
                                        , ALifeToast.ToastType.SUCCESS
                                        , ALifeToast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    });
                }else {
                    ALifeToast.makeText(getActivity()
                            , "输入不能为空！"
                            , ALifeToast.ToastType.SUCCESS
                            , ALifeToast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                寻找对应的文件的路径
                 */
                AVIMConversation avimConversation = AppData.getIMClient().getConversation(CONVERSATION_ID);
                File directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                String rootPath = directory.getAbsolutePath()+"/Messagewall";
                File file = new File(rootPath);
                String path = "";
                String[] list = file.list();
                for (int i=0;i<list.length;i++){
                    if (list[i].equals(avimConversation.getAttribute("name").toString()+".png")){
                        path = rootPath+"/"+list[i];
                    }
                }
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_TITLE,"加入群组");
                intent.putExtra(Intent.EXTRA_SUBJECT,"扫描二维码加入群组！");
                intent.putExtra(Intent.EXTRA_STREAM,Uri.parse(path));
                startActivity(Intent.createChooser(intent,"选择应用"));
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
