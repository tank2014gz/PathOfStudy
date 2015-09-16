package com.haitou.xiaoyoupai.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.haitou.xiaoyoupai.R;
import com.haitou.xiaoyoupai.ui.activity.NextActivity;
import com.haitou.xiaoyoupai.ui.widget.materialedittext.MaterialEditText;


public class PushFragment extends MainFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    private View curView = null;
    public Button btn_next;

    private MaterialEditText editText_title,editText_date,editText_position;

    /*
    暂时存储
     */
    public String title,date,position;

    private OnFragmentInteractionListener mListener;


    public static PushFragment newInstance(String param1, String param2) {
        PushFragment fragment = new PushFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PushFragment() {
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

        curView = inflater.inflate(R.layout.fragment_push, topContentView);
        super.init(curView);
        initTitleView();

        btn_next = (Button)curView.findViewById(R.id.next);

        editText_title = (MaterialEditText)curView.findViewById(R.id.title);
        editText_date = (MaterialEditText)curView.findViewById(R.id.date);
        editText_position = (MaterialEditText)curView.findViewById(R.id.position);


        topLeftContainerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = editText_title.getText().toString();
                date = editText_date.getText().toString();
                position = editText_position.getText().toString();

                if (title!=null&&date!=null&&position!=null
                        &&title.length()!=0&&date.length()!=0&&position.length()!=0){

                    Bundle bundle = new Bundle();
                    bundle.putString("subject",title);
                    bundle.putString("start_time",date);
                    bundle.putString("position",position);

                    Intent intent = new Intent(getActivity(), NextActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);


                }else {
                    Toast.makeText(getActivity(),"输入不能为空!",Toast.LENGTH_SHORT).show();
                }

            }
        });

        return curView;
    }

    private void initTitleView() {
        // 设置标题
        setTopRightText("去网页发布");
        setTopLeftText("填写基本信息1/2");
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
