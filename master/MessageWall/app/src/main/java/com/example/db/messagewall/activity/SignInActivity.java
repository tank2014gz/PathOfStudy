package com.example.db.messagewall.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.sns.SNS;
import com.avos.sns.SNSBase;
import com.avos.sns.SNSCallback;
import com.avos.sns.SNSException;
import com.avos.sns.SNSType;
import com.example.db.messagewall.api.AppData;
import com.example.db.messagewall.utils.AppConstant;
import com.example.db.messagewall.view.ALifeToast;
import com.support.android.designlibdemo.R;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.UMSsoHandler;

import java.util.Map;
import java.util.Set;

public class SignInActivity extends BaseActivity {

    public Toolbar toolbar;

    public TextView mSignUp;
    public Button mSignIn;
    public EditText mUserName,mUserPsd;
    public String username,userpsd;
    public Button mLoginQQ,mLoginWeiBo;

    public SNSType type = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initToolBar();

        /*
        初始化第三方登陆的东西
         */


        mSignUp = (TextView)findViewById(R.id.tv_signup);
        mSignIn = (Button)findViewById(R.id.btn_signin);
        mLoginQQ = (Button)findViewById(R.id.login_qq);
        mLoginWeiBo = (Button)findViewById(R.id.login_weibo);

        mUserName = (EditText)findViewById(R.id.user_name);
        mUserPsd = (EditText)findViewById(R.id.user_psd);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
                SignInActivity.this.finish();
            }
        });

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = mUserName.getText().toString();
                userpsd = mUserPsd.getText().toString();
                if (username.length()!=0&&userpsd.length()!=0&&AppConstant.isMobile(username)){
                    AVUser.logInInBackground(username, userpsd, new LogInCallback<AVUser>() {
                        @Override
                        public void done(AVUser avUser, AVException e) {
                            if (e == null) {
                                /*
                                存储本地账户的相关信息
                                包括姓名、登陆状态
                                在初始的时候显示
                                 */
                                SharedPreferences sharedPreferences = getApplicationContext()
                                                                        .getSharedPreferences("com.example.db.alife_account"
                                                                        , Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("NAME", avUser.getUsername());
                                editor.putBoolean("STATUS", true);
                                editor.commit();

                                if (!TextUtils.isEmpty(AVUser.getCurrentUser().getUsername())) {
                                    AppData.setClientIdToPre(AVUser.getCurrentUser().getUsername());
                                }



                                } else {
                                    Log.v("db.error1", e.getMessage());
                                }
                            }
                        }

                        );
                    }else {
                        ALifeToast.makeText(SignInActivity.this
                                            , "输入不正确！"
                                            , ALifeToast.ToastType.SUCCESS
                                            , ALifeToast.LENGTH_SHORT)
                                            .show();

                }
            }
        });

        mLoginQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    type = SNSType.AVOSCloudSNSQQ;
                    SNS.setupPlatform(SignInActivity.this, SNSType.AVOSCloudSNSQQ,
                            "1104717039",
                            "ltUfw4djOvhyrJHg",
                            "https://leancloud.cn/1.1/sns/callback/tux1pn5f04378ayy");
                    SNS.loginWithCallback(SignInActivity.this, SNSType.AVOSCloudSNSQQ, new SNSCallback() {
                        @Override
                        public void done(SNSBase base, SNSException e) {
                            if (e==null) {
                                SNS.loginWithAuthData(base.userInfo(), new LogInCallback<AVUser>() {
                                    @Override
                                    public void done(AVUser user, AVException error) {
                                        if (error==null){
                                            /*
                                            存储本地账户的相关信息
                                            包括姓名、登陆状态
                                            在初始的时候显示
                                             */
                                            SharedPreferences sharedPreferences = getApplicationContext()
                                                    .getSharedPreferences("com.example.db.alife_account"
                                                            , Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("NAME", user.getUsername());
                                            editor.putBoolean("STATUS", true);
                                            editor.commit();

                                            if (!TextUtils.isEmpty(AVUser.getCurrentUser().getUsername())) {
                                                AppData.setClientIdToPre(AVUser.getCurrentUser().getUsername());
                                            }


                                        }else {
                                            AppConstant.showSelfToast(SignInActivity.this,"授权失败!");

                                        }
                                    }
                                });
                            }else {
                                AppConstant.showSelfToast(SignInActivity.this, "授权失败!");
                            }
                        }
                    });
                } catch (AVException e) {
                    e.printStackTrace();
                }

            }
        });

        mLoginWeiBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    type = SNSType.AVOSCloudSNSSinaWeibo;
                    SNS.setupPlatform(SignInActivity.this, SNSType.AVOSCloudSNSSinaWeibo, "574579688",
                            "69b7025c912bc15192ab2ce3b8e89c9a",
                            "https://leancloud.cn/1.1/sns/callback/4h9h5f5bunrwe42f");
                    SNS.loginWithCallback(SignInActivity.this, SNSType.AVOSCloudSNSSinaWeibo, new SNSCallback() {
                        @Override
                        public void done(SNSBase base, SNSException e) {
                            if (e==null) {
                                SNS.loginWithAuthData(base.userInfo(), new LogInCallback<AVUser>() {
                                    @Override
                                    public void done(AVUser user, AVException error) {
                                        if (error==null){
                                            /*
                                            存储本地账户的相关信息
                                            包括姓名、登陆状态
                                            在初始的时候显示
                                             */
                                            SharedPreferences sharedPreferences = getApplicationContext()
                                                    .getSharedPreferences("com.example.db.alife_account"
                                                            , Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("NAME", user.getUsername());
                                            editor.putBoolean("STATUS", true);
                                            editor.commit();

                                            if (!TextUtils.isEmpty(AVUser.getCurrentUser().getUsername())) {
                                                AppData.setClientIdToPre(AVUser.getCurrentUser().getUsername());
                                            }


                                        }else {
                                            AppConstant.showSelfToast(SignInActivity.this,"授权失败!");
                                        }
                                    }
                                });
                            }else {
                                AppConstant.showSelfToast(SignInActivity.this, "授权失败!");
                            }
                        }
                    });
                } catch (AVException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("登陆");
        toolbar.setTitleTextColor(getResources().getColor(R.color.actionbar_title_color));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.actionbar_title_color));

        if (Build.VERSION.SDK_INT >= 21)
            toolbar.setElevation(24);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        SNS.onActivityResult(requestCode, resultCode, data, type);

        startActivity(new Intent(SignInActivity.this, SelectActivity.class));
        SignInActivity.this.finish();
    }
}
