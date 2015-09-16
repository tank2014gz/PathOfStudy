package com.haitou.xiaoyoupai.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.haitou.xiaoyoupai.DB.entity.UserEntity;
import com.haitou.xiaoyoupai.R;
import com.haitou.xiaoyoupai.ui.activity.EducationActivity;
import com.haitou.xiaoyoupai.ui.widget.materialedittext.MaterialEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.URI;

public class AddEducationFragment extends MainFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View curView = null;

    private MaterialEditText tv_school,tv_xueyuan,tv_certificate,tv_start,tv_end;

    private Button btn_save;

    public String uid,cid,major,start_time,end_time,level,teacher;

    private OnFragmentInteractionListener mListener;


    public static AddEducationFragment newInstance(String param1, String param2) {
        AddEducationFragment fragment = new AddEducationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AddEducationFragment() {
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

        curView = inflater.inflate(R.layout.fragment_add_education, topContentView);
        super.init(curView);
        initTitleView();

        tv_school = (MaterialEditText)curView.findViewById(R.id.school);
        tv_xueyuan = (MaterialEditText)curView.findViewById(R.id.xueyuan);
        tv_certificate = (MaterialEditText)curView.findViewById(R.id.certificate);
        tv_start = (MaterialEditText)curView.findViewById(R.id.start_time);
        tv_end = (MaterialEditText)curView.findViewById(R.id.end_time);

        btn_save = (Button)curView.findViewById(R.id.save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("peer_id", Context.MODE_PRIVATE);
                uid = sharedPreferences.getString("peer_id", "1");

                cid = tv_school.getText().toString();
                major = tv_xueyuan.getText().toString();
                start_time = tv_start.getText().toString();
                end_time = tv_end.getText().toString();
                level = tv_certificate.getText().toString();
                teacher = "暂无";

                if (cid.length()!=0&&major.length()!=0&&start_time.length()!=0&&end_time.length()!=0
                        &&level.length()!=0){

                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams requestParams = new RequestParams();
                    requestParams.put("uid",uid);
                    requestParams.put("cid",cid);
                    requestParams.put("major",major);
                    requestParams.put("start_time",start_time);
                    requestParams.put("end_time",end_time);
                    requestParams.put("level",level);
                    requestParams.put("teacher",teacher);

                    client.setTimeout(5000);
                    client.post(getActivity(), "http://202.114.20.55/schoolmate/api/user/addEducation"
                            , requestParams, new AsyncHttpResponseHandler() {

                        @Override
                        public void onStart() {
                            // called before request is started
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                            // called when response HTTP status is "200 OK"

                            startActivity(new Intent(getActivity(), EducationActivity.class));
                            getActivity().finish();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                            // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                            Toast.makeText(getActivity(),"添加失败！",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onRetry(int retryNo) {
                            // called when request is retried
                        }
                    });


                }else {
                    Toast.makeText(getActivity(),"输入不能为空！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        topLeftContainerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        return curView;
    }

    private void initTitleView() {
        // 设置标题
        setTopTitle("增加教育经历");
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
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
