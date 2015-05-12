package com.example.db.civil.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
import com.example.db.civil.MainActivity;
import com.example.db.civil.R;
import com.example.db.civil.utlis.AppConstant;

import it.neokree.materialnavigationdrawer.util.Utils;

public class LoginActivity extends ActionBarActivity {

    public EditText mUserName,mUserPsd;
    public Button mSignIn,back;
    public TextView mSignUp;

    public String name,psd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setStatus(true,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserName=(EditText)findViewById(R.id.user_name);
        mUserPsd=(EditText)findViewById(R.id.user_psd);
        mSignIn=(Button)findViewById(R.id.btn_signin);
        mSignUp=(TextView)findViewById(R.id.signup);
        back=(Button)findViewById(R.id.back);

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name=mUserName.getText().toString();
                psd=mUserPsd.getText().toString();

                if (name.length()!=0&&psd.length()!=0){

                    AVUser avUser=AVUser.getCurrentUser();
                    if (avUser==null){
                        Toast.makeText(getApplicationContext(),"请先注册!",Toast.LENGTH_SHORT).show();
                    }else {
                        avUser.logInInBackground(name,psd,new LogInCallback<AVUser>() {
                            @Override
                            public void done(AVUser avUser, AVException e) {
                                if (e==null){
                                    AppConstant.USER_NAME=avUser.getObjectId().substring(0,8);
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
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
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
