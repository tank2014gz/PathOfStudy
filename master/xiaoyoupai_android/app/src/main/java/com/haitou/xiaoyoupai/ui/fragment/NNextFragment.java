package com.haitou.xiaoyoupai.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haitou.xiaoyoupai.DB.entity.UserEntity;
import com.haitou.xiaoyoupai.R;
import com.haitou.xiaoyoupai.config.IntentConstant;
import com.haitou.xiaoyoupai.imservice.event.UserInfoEvent;
import com.haitou.xiaoyoupai.imservice.service.IMService;
import com.haitou.xiaoyoupai.imservice.support.IMServiceConnector;
import com.haitou.xiaoyoupai.ui.adapter.TagAdapter;
import com.haitou.xiaoyoupai.ui.helper.HttpUtils;
import com.haitou.xiaoyoupai.ui.widget.CircleImageView;
import com.haitou.xiaoyoupai.ui.widget.materialedittext.MaterialEditText;
import com.haitou.xiaoyoupai.utils.IMUIHelper;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;


public class NNextFragment extends MainFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private View curView = null;

    public GridView gridView;
    public MaterialEditText editText_endtime,editText_price,editText_amount;

    public CheckBox isCheck,isRestrain;

    public String endtime,price,amount;

    public String check = "否";
    public String restrain = "是";

    public Button btn_preview,btn_put;

    public Bundle bundle;

    public int currentUserId;

    private OnFragmentInteractionListener mListener;


    public static NNextFragment newInstance(String param1, String param2) {
        NNextFragment fragment = new NNextFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NNextFragment() {
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

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("peer_id", Context.MODE_PRIVATE);
        currentUserId = Integer.valueOf(sharedPreferences.getString("peer_id","1"));

        bundle = this.getArguments();

        curView = inflater.inflate(R.layout.fragment_nnext, topContentView);
        super.init(curView);
        initTitleView();

        gridView = (GridView)curView.findViewById(R.id.tag);
        editText_endtime = (MaterialEditText)curView.findViewById(R.id.end_time);
        editText_amount = (MaterialEditText)curView.findViewById(R.id.amount);
        editText_price = (MaterialEditText)curView.findViewById(R.id.price);

        btn_put = (Button)curView.findViewById(R.id.put);
        btn_preview = (Button)curView.findViewById(R.id.preview);

        isCheck = (CheckBox)curView.findViewById(R.id.is_check);
        isRestrain = (CheckBox)curView.findViewById(R.id.is_restrain);

        isCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    check = "是";
                }else {
                    check = "否";
                }
            }
        });
        isRestrain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    restrain = "是";
                }else {
                    restrain = "否";
                }
            }
        });

        TagAdapter tagAdapter = new TagAdapter(getActivity());
        gridView.setAdapter(tagAdapter);

        topLeftContainerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        topRightTitleTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_put.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                price = editText_price.getText().toString();
                amount = editText_amount.getText().toString();
                endtime = editText_endtime.getText().toString();

                if (price!=null&&amount!=null&&endtime!=null
                        &&price.length()!=0&&amount.length()!=0&&endtime.length()!=0){

                    bundle.putString("end_time", endtime);
                    bundle.putString("max_num",amount);
                    bundle.putString("charge",price);
                    bundle.putString("check",check);
                    bundle.putString("limit_v",restrain);



                }else {
                    Toast.makeText(getActivity(),"输入不能为空!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return curView;
    }

    private void initTitleView() {
        // 设置标题
        setTopRightText("发布");
        setTopLeftText("限制信息");
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


    public ArrayList<String> bundleToString(Bundle bundle){

        ArrayList<String> temp = new ArrayList<String>();

        temp.add(String.valueOf(currentUserId));
        temp.add(bundle.getString("subject"));
        temp.add(bundle.getString("start_time"));
        temp.add(bundle.getString("end_time"));
        temp.add(bundle.getString("position"));
        temp.add("0.000000");
        temp.add("0.000000");
        temp.add(bundle.getString("type"));
        temp.add(bundle.getString("cover_img"));
        temp.add(bundle.getString("content"));
        temp.add(bundle.getString("end_time"));
        temp.add(bundle.getString("charge"));
        temp.add(bundle.getString("max_num"));
        temp.add(bundle.getString("check"));
        temp.add(bundle.getString("limit_v"));
        temp.add("detail");

        return temp;

    }
    public ArrayList<String> listToString(){

        ArrayList<String> temp = new ArrayList<String>();

        temp.add("uid");
        temp.add("subject");
        temp.add("start_time");
        temp.add("end_time");
        temp.add("position");
        temp.add("coord_x");
        temp.add("coord_y");
        temp.add("type");
        temp.add("cover_img");
        temp.add("content");
        temp.add("deadline");
        temp.add("charge");
        temp.add("max_num");
        temp.add("check");
        temp.add("limit_v");
        temp.add("detail");

        return temp;
    }

    //                    try {
//                        JSONObject jsonObject = new JSONObject();
//                        for (int j=0;j<16;j++){
//                           jsonObject.put(listToString().get(j),bundleToString(bundle).get(j));
//                        }
//
//
//
//                        Request request = new JsonRequest<JSONArray>(Request.Method.POST,
//                                "http://202.114.20.55/schoolmate/api/activity/add", jsonObject.toString(),
//                                new Response.Listener<JSONArray>() {
//
//                                    @Override
//                                    public void onResponse(JSONArray response) {
//                                        Log.v("TAG", response.toString());
//                                    }
//                                }, new Response.ErrorListener() {
//
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Log.v("TAG","Error: " + error.getMessage());
//                            }
//                        }) {
//
//
//                            @Override
//                            protected Map<String, String> getParams() {
//                                Map<String, String> params = new HashMap<String, String>();
//
//                                return params;
//                            }
//
//                            @Override
//                            protected Response parseNetworkResponse(NetworkResponse networkResponse) {
//                                String je = null;
//                                try {
//                                    je = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
//                                    return Response.success(new JSONArray(je), HttpHeaderParser.parseCacheHeaders(networkResponse));
//                                } catch (UnsupportedEncodingException e) {
//                                    e.printStackTrace();
//                                    return Response.error(new ParseError(e));
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                    return Response.error(new ParseError(e));
//                                }
//                            }
//
//                        };

//                       JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
//                                "http://202.114.20.55/schoolmate/api/activity/add", jsonObject,
//                                new Response.Listener<JSONObject>() {
//
//                                    @Override
//                                    public void onResponse(JSONObject response) {
//                                        Log.v("TAG", response.toString());
//                                    }
//                                }, new Response.ErrorListener() {
//
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Log.v("TAG","Error: " + error);
//                            }
//                        }) {
//
//                       };
//
//                        request.setTag("activity");
//                        Volley.newRequestQueue(getActivity()).add(request);

//                    }catch (JSONException e){
//                        e.printStackTrace();
//                    }
}
