package com.example.db.messagewall.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.view.ALifeToast;
import com.example.db.messagewall.view.dd.CircularProgressButton;
import com.example.db.messagewall.view.swipebacklayout.SwipeBackActivity;
import com.support.android.designlibdemo.R;


public class SignUpActivity extends SwipeBackActivity {

    public Toolbar toolbar;

//    public Button mSignUp;
    public TextView mSignIn,mSendCheckCode;
    public EditText mUserName,mUserPsd,mCheckCode,mNiChen;
    public String username,userpsd,usercheckcode,usernichen;

    public CircularProgressButton circularProgressButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initToolBar();

//        mSignUp = (Button)findViewById(R.id.btn_signup);
        mSignIn = (TextView)findViewById(R.id.tv_signin);
        mSendCheckCode = (TextView)findViewById(R.id.acquire_key);

        circularProgressButton = (CircularProgressButton)findViewById(R.id.circularButton);
        circularProgressButton.setIndeterminateProgressMode(true);

        mUserName = (EditText)findViewById(R.id.user_name);
        mUserPsd = (EditText)findViewById(R.id.user_psd);
        mCheckCode = (EditText)findViewById(R.id.user_check);
        mNiChen = (EditText)findViewById(R.id.user_nichen);

        mSendCheckCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = mUserName.getText().toString();
                userpsd = mUserPsd.getText().toString();
                usernichen = mNiChen.getText().toString();
                if (username.length()!=0&&userpsd.length()!=0&&AppConstant.isMobile(username)&&usernichen!=null&&usernichen.length()!=0){
                    AVUser avUser = new AVUser();
                    avUser.setUsername(username);
                    avUser.setPassword(userpsd);
                    avUser.setMobilePhoneNumber(username);
                    ALifeToast.makeText(SignUpActivity.this
                                        , "发送验证码ing"
                                        , ALifeToast.ToastType.SUCCESS
                                        , ALifeToast.LENGTH_SHORT)
                                        .show();
                    avUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {

                            } else {
                                Log.v("db.error2", e.getMessage());
                            }
                        }
                    });
                }else {
                    ALifeToast.makeText(SignUpActivity.this
                                        , "输入不能为空！"
                                        , ALifeToast.ToastType.SUCCESS
                                        , ALifeToast.LENGTH_SHORT)
                                        .show();

                }
            }
        });

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                SignUpActivity.this.finish();
            }
        });

        circularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (circularProgressButton.getProgress() == 0) {
                    circularProgressButton.setProgress(50);

                    username = mUserName.getText().toString();
                    userpsd = mUserPsd.getText().toString();
                    usernichen = mNiChen.getText().toString();
                    if (username.length() != 0 && userpsd.length() != 0 && AppConstant.isMobile(username) && usernichen != null && usernichen.length() != 0) {
                        AVUser avUser = new AVUser();
                        avUser.setUsername(username);
                        avUser.setPassword(userpsd);
                        avUser.setMobilePhoneNumber(username);
                    /*
                    保存用户的昵称
                     */
                        AVObject avObject = new AVObject("NiChen");
                        avObject.put("username", username);
                        avObject.put("nichen", usernichen);
                        avObject.saveInBackground();

                        usercheckcode = mCheckCode.getText().toString();
                        if (usercheckcode.length() != 0) {
                            avUser.verifyMobilePhoneInBackground(usercheckcode, new AVMobilePhoneVerifyCallback() {
                                @Override
                                public void done(AVException e) {
                                    // TODO Auto-generated method stub
                                    if (e == null) {

                                        circularProgressButton.setProgress(100);

//                                    startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
//                                    SignUpActivity.this.finish();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
                                                SignUpActivity.this.finish();
                                            }
                                        },1000);

                                    } else {
                                        circularProgressButton.setProgress(0);
                                        ALifeToast.makeText(SignUpActivity.this
                                                , "注册失败惹！"
                                                , ALifeToast.ToastType.SUCCESS
                                                , ALifeToast.LENGTH_SHORT)
                                                .show();
                                    }
                                }
                            });
                        } else {
                            ALifeToast.makeText(SignUpActivity.this
                                    , "请输入验证码！"
                                    , ALifeToast.ToastType.SUCCESS
                                    , ALifeToast.LENGTH_SHORT)
                                    .show();

                        }

                    } else {
                        ALifeToast.makeText(SignUpActivity.this
                                , "输入不正确！"
                                , ALifeToast.ToastType.SUCCESS
                                , ALifeToast.LENGTH_SHORT)
                                .show();
                    }
                } else if (circularProgressButton.getProgress() == 100) {
                    startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                    SignUpActivity.this.finish();
                }

            }
        });
    }

    private void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("注册");
        toolbar.setTitleTextColor(getResources().getColor(R.color.actionbar_title_color));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.actionbar_title_color));

        if (Build.VERSION.SDK_INT >= 21)
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

    }
    public Toolbar getToolbar(){
        return toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}