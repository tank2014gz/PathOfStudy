package com.example.db.tline.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.db.tline.MainActivity;
import com.example.db.tline.R;
import com.example.db.tline.utils.AppConstant;
import com.example.db.tline.view.RevealLayout;
import com.manuelpeinado.fadingactionbar.Utils;

public class LoginActivity extends ActionBarActivity {

    public Button back;

    public EditText mUserName,mUserPsd;
    public Button mSignIn;
    public TextView mForgetPsd,mSignUp;

    public RevealLayout mRevealLayout;
    public boolean mIsAnimationSlowDown = false;
    public boolean mIsBaseOnTouchLocation = false;

    public String USER_NAME;
    public String USER_PASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppConstant.setStatus(true,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mRevealLayout = (RevealLayout) findViewById(R.id.reveal_layout);
        mRevealLayout.setContentShown(false);
        mRevealLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRevealLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mRevealLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRevealLayout.show();
                    }
                }, 50);
            }
        });

        mUserName=(EditText)findViewById(R.id.user_name);
        mUserPsd=(EditText)findViewById(R.id.user_psd);

        mSignIn=(Button)findViewById(R.id.btn_signin);

        mForgetPsd=(TextView)findViewById(R.id.forget_psd);
        mSignUp=(TextView)findViewById(R.id.signup);

        back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                USER_NAME=mUserName.getText().toString();
                USER_PASSWORD=mUserPsd.getText().toString();

                if (USER_NAME.length()!=0&&USER_PASSWORD.length()!=0){

                    AVUser avUser=AVUser.getCurrentUser();
                    if (avUser==null){
                        Toast.makeText(getApplicationContext(), "请先注册!", Toast.LENGTH_SHORT).show();
                    }else {
                        avUser.logInInBackground(USER_NAME,USER_PASSWORD,new LogInCallback<AVUser>() {
                            @Override
                            public void done(AVUser avUser, AVException e) {
                                if (e==null){
                                    AppConstant.USER_NAME=avUser.getUsername().toString();
                                    AppConstant.LOGIN_STATUS=true;
                                    Intent intent=new Intent();
                                    intent.setClass(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    LoginActivity.this.finish();
                                }else {
                                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"用户名或密码不能为空!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
