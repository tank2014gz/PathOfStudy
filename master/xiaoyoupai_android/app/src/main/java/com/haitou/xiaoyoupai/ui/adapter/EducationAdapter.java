package com.haitou.xiaoyoupai.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haitou.xiaoyoupai.R;
import com.haitou.xiaoyoupai.imservice.manager.IMLoginManager;
import com.haitou.xiaoyoupai.ui.activity.EducationActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by db on 9/4/15.
 */
public class EducationAdapter extends BaseAdapter{

    public Context context;

    public List<JSONObject> jsonObjects;

    public EducationAdapter(Context context,List<JSONObject> jsonObjects){
        super();
        this.context = context;
        this.jsonObjects = jsonObjects;
    }

    @Override
    public int getCount() {
        return jsonObjects.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return jsonObjects.size()-i-1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final ViewHolder viewHolder;
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.education_item,null);
            viewHolder = new ViewHolder();
            viewHolder.content = (TextView)view.findViewById(R.id.content);
            viewHolder.status = (Button)view.findViewById(R.id.status);
            viewHolder.checkBox = (CheckBox)view.findViewById(R.id.Notice_signup_Checkbox);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }

        final JSONObject jsonObject = jsonObjects.get(jsonObjects.size()-i-1);

        try {

            if (jsonObject.getString("level").equals("0")){
                viewHolder.content.setText(jsonObject
                        .getString("cid")+"     "+jsonObject.getString("major") + "     " +"本科");
            } else {
                viewHolder.content.setText(jsonObject
                        .getString("cid")+"     "+jsonObject.getString("major")+"     "+"研究生");
            }

        }catch (JSONException e){
            e.printStackTrace();
        }

        if (i==0){
            viewHolder.status.setBackgroundColor(Color.parseColor("#f5f5f5"));
            viewHolder.status.setTextColor(context.getResources().getColor(R.color.default_black_color));
            viewHolder.status.setText("默认第一学历");
        }else {
            viewHolder.status.setBackgroundColor(context.getResources().getColor(R.color.default_blue_color));
            viewHolder.status.setTextColor(context.getResources().getColor(R.color.default_bk));
            viewHolder.status.setText("显示为第一学历");
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewHolder.checkBox.setChecked(false);
                viewHolder.checkBox.setVisibility(View.GONE);
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                viewHolder.checkBox.setVisibility(View.VISIBLE);

                return false;
            }
        });
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                if (isChecked){

                    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, android.R.style.Theme_Holo_Light_Dialog));
                    LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View dialog_view = inflater.inflate(R.layout.tt_custom_dialog, null);
                    final EditText editText = (EditText)dialog_view.findViewById(R.id.dialog_edit_content);
                    editText.setVisibility(View.GONE);
                    TextView textText = (TextView)dialog_view.findViewById(R.id.dialog_title);
                    textText.setText("你确定删除这条经历?");
                    builder.setView(dialog_view);
                    builder.setPositiveButton(context.getResources().getString(R.string.tt_ok), new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                            try {
                                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                                RequestParams requestParams = new RequestParams();
                                requestParams.put("eid",jsonObject.getString("id"));

                                asyncHttpClient.setTimeout(5000);
                                asyncHttpClient.post(context, "http://202.114.20.55/schoolmate/api/user/removeEducation"
                                        , requestParams, new AsyncHttpResponseHandler() {

                                    @Override
                                    public void onStart() {
                                        // called before request is started
                                    }

                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                                        // called when response HTTP status is "200 OK"


                                        Intent intent = new Intent(context, EducationActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent);

                                        Toast.makeText(context, "删除成功！", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                                        Toast.makeText(context, "删除失败！", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onRetry(int retryNo) {
                                        // called when request is retried
                                    }
                                });

                            }catch (JSONException e){
                                e.printStackTrace();
                            }

                        }
                    });

                    builder.setNegativeButton(context.getResources().getString(R.string.tt_cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                            viewHolder.checkBox.setChecked(false);
                            viewHolder.checkBox.setVisibility(View.GONE);
                        }
                    });
                    builder.show();

                }else {

                }
            }
        });

        return view;
    }

    public static class ViewHolder{
        TextView content;
        Button status;
        CheckBox checkBox;
    }

}
