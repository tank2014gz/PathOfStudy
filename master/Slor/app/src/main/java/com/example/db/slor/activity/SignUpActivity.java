package com.example.db.slor.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.example.db.slor.MainActivity;
import com.example.db.slor.R;
import com.example.db.slor.utils.Utils;

import mehdi.sakout.fancybuttons.FancyButton;

public class SignUpActivity extends ActionBarActivity {


    public EditText mUserName,mUserPsd;
    public FancyButton mSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setStatus(true,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mSignUp=(FancyButton)findViewById(R.id.btn_signup);

        mUserName=(EditText)findViewById(R.id.username);
        mUserPsd=(EditText)findViewById(R.id.userpsd);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=mUserName.getText().toString();
                String psd=mUserPsd.getText().toString();

                if (name!=null&&psd!=null){
                    AVUser avUser=new AVUser();
                    avUser.setUsername(name);
                    avUser.setPassword(psd);
                    avUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e==null){
                                Toast.makeText(getApplicationContext(),"注册成功!",Toast.LENGTH_SHORT).show();
                                Handler handler=new Handler();
                                Runnable runnable=new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent =new Intent();
                                        intent.setClass(SignUpActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        SignUpActivity.this.finish();
                                    }
                                };
                                handler.postDelayed(runnable,2000);
                            }else {
                                Toast.makeText(getApplicationContext(),"注册失败!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(), "输入不能为空!", Toast.LENGTH_SHORT).show();
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
