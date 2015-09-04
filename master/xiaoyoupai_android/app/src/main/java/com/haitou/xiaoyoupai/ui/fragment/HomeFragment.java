package com.haitou.xiaoyoupai.ui.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.haitou.xiaoyoupai.R;
import com.haitou.xiaoyoupai.ui.activity.PushActivity;
import com.haitou.xiaoyoupai.ui.adapter.ActivityHeadAdapter;
import com.haitou.xiaoyoupai.ui.adapter.ActivityListAdapter;
import com.haitou.xiaoyoupai.ui.widget.QRCodePopWindow;
import com.haitou.xiaoyoupai.ui.widget.SelfPopWindow;


public class HomeFragment extends MainFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View curView = null;

    public ListView listView;

    public RecyclerView recyclerView;

    public SelfPopWindow selfPopWindow = null;

    public QRCodePopWindow qrCodePopWindow = null;

    private OnFragmentInteractionListener mListener;


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
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

        curView = inflater.inflate(R.layout.fragment_home, topContentView);
        super.init(curView);
        initTitleView();

        listView = (ListView)curView.findViewById(R.id.ActivityListView);

        //head部分
        View mHeadView = inflater.inflate(R.layout.activity_head,null);
        recyclerView = (RecyclerView)mHeadView.findViewById(R.id.listview);
        recyclerView.setHasFixedSize(true);
        int orientation = LinearLayoutManager.HORIZONTAL;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),orientation,false));
        ActivityHeadAdapter activityHeadAdapter = new ActivityHeadAdapter(getActivity());
        recyclerView.setAdapter(activityHeadAdapter);

        listView.addHeaderView(mHeadView);

        ActivityListAdapter activityListAdapter = new ActivityListAdapter(getActivity());
        listView.setAdapter(activityListAdapter);

        topRightTwoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchView();
            }
        });

        topRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopWindow();
            }
        });

        return curView;
    }

    /*
    弹出菜单选项
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void showPopWindow(){
        if (selfPopWindow==null){
            OnClickListener onClickListener = new OnClickListener();
            selfPopWindow = new SelfPopWindow(getActivity(),onClickListener,dp2px(160),dp2px(192));
            selfPopWindow.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b){
                        selfPopWindow.dismiss();
                    }
                }
            });

        }

        selfPopWindow.setFocusable(true);
        selfPopWindow.setOutsideTouchable(true);

        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.8f;
        getActivity().getWindow().setAttributes(params);
        selfPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                params.alpha = 1.0f;//设置为不透明，即恢复原来的界面
                getActivity().getWindow().setAttributes(params);
            }
        });
        selfPopWindow.showAtLocation(topRightBtn,Gravity.TOP|Gravity.RIGHT,16,160);
        selfPopWindow.update();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void showQrCodePopWindow(){
        if (qrCodePopWindow==null){
            OnClickListener onClickListener = new OnClickListener();
            qrCodePopWindow = new QRCodePopWindow(getActivity(),onClickListener, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            qrCodePopWindow.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b){
                        qrCodePopWindow.dismiss();
                    }
                }
            });
        }
        qrCodePopWindow.setFocusable(true);
        qrCodePopWindow.setOutsideTouchable(true);

        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.8f;
        getActivity().getWindow().setAttributes(params);
        qrCodePopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                params.alpha = 1.0f;//设置为不透明，即恢复原来的界面
                getActivity().getWindow().setAttributes(params);
            }
        });
        qrCodePopWindow.showAtLocation(topRightBtn,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL,0,0);
        qrCodePopWindow.update();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    public class OnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.activity_menu:

                    startActivity(new Intent(getActivity(), PushActivity.class));
                    selfPopWindow.dismiss();
                    break;
                case R.id.people_menu:

                    selfPopWindow.dismiss();
                    break;
                case R.id.sign_in_menu:

                    selfPopWindow.dismiss();

                    showQrCodePopWindow();

                    break;
                case R.id.help_menu:

                    selfPopWindow.dismiss();
                    break;
                case R.id.qrcode:

                    qrCodePopWindow.dismiss();
                    break;
                default:selfPopWindow.dismiss();
            }
        }
    }

    /**
     * @Description 设置顶部按钮
     */
    private void initTitleView() {
        // 设置标题
        setTopLeftText("活动");
//        setTopRightButton(R.drawable.abc_ic_search_api_mtrl_alpha);
        setTopRightButton(R.drawable.ic_add_white_24dp);
        setTopRightTwoButton(R.drawable.abc_ic_search_api_mtrl_alpha);
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
