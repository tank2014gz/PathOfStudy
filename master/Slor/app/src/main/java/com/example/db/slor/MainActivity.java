package com.example.db.slor;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.db.slor.activity.NavigatorDrawerActivity;
import com.example.db.slor.activity.SignUpActivity;
import com.example.db.slor.utils.Utils;

import mehdi.sakout.fancybuttons.FancyButton;


public class MainActivity extends ActionBarActivity {

    public FancyButton mSignIn,mSignUp;
    public EditText mUserName,mUserPsd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setStatus(true,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSignIn=(FancyButton)findViewById(R.id.btn_signin);
        mSignUp=(FancyButton)findViewById(R.id.btn_signup);

        mUserName=(EditText)findViewById(R.id.username);
        mUserPsd=(EditText)findViewById(R.id.userpsd);

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=mUserName.getText().toString();
                String psd=mUserPsd.getText().toString();

                if (name!=null&&psd!=null){
                    AVUser avUser=AVUser.getCurrentUser();
                    if (avUser==null){
                        Toast.makeText(getApplicationContext(),"请先注册!",Toast.LENGTH_SHORT).show();
                    }else {
                        avUser.logInInBackground(name,psd,new LogInCallback<AVUser>() {
                            @Override
                            public void done(AVUser avUser, AVException e) {
                                if (e==null){
                                    Intent intent=new Intent();
                                    intent.setClass(MainActivity.this, NavigatorDrawerActivity.class);
                                    intent.putExtra("objected",avUser.getObjectId());
                                    startActivity(intent);
                                    MainActivity.this.finish();
                                }else {
                                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"输入不能为空!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=mUserName.getText().toString();
                String psd=mUserPsd.getText().toString();

                Intent intent=new Intent();
                intent.setClass(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
