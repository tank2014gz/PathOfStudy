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
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.example.db.tline.R;
import com.example.db.tline.utils.AppConstant;
import com.example.db.tline.view.RevealLayout;

public class SignUpActivity extends ActionBarActivity {

    public EditText mUserName,mUserPsd;
    public Button mSignUp,back;

    public RevealLayout mRevealLayout;
    public boolean mIsAnimationSlowDown = false;
    public boolean mIsBaseOnTouchLocation = false;

    public String USER_NAME;
    public String USER_PASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppConstant.setStatus(true,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mUserName=(EditText)findViewById(R.id.user_name);
        mUserPsd=(EditText)findViewById(R.id.user_psd);

        mSignUp=(Button)findViewById(R.id.btn_signin);
        back=(Button)findViewById(R.id.back);

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

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                USER_NAME=mUserName.getText().toString();
                USER_PASSWORD=mUserPsd.getText().toString();
                if (USER_NAME.length()!=0&&USER_PASSWORD.length()!=0){
                    AVUser avUser=new AVUser();
                    avUser.setUsername(USER_NAME);
                    avUser.setPassword(USER_PASSWORD);
                    avUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(AVException e) {
                            Toast.makeText(getApplicationContext(), "注册成功!", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent();
                            intent.setClass(SignUpActivity.this,LoginActivity.class);
                            startActivity(intent);
                            SignUpActivity.this.finish();
                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(), "用户名或密码不能为空!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
