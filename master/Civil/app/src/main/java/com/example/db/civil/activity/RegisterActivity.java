package com.example.db.civil.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.example.db.civil.MainActivity;
import com.example.db.civil.R;

import it.neokree.materialnavigationdrawer.util.Utils;

public class RegisterActivity extends ActionBarActivity {

    public EditText mUserName,mUserPsd;
    public Button mSignUp,back;

    public String name,psd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setStatus(true,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUserName=(EditText)findViewById(R.id.user_name);
        mUserPsd=(EditText)findViewById(R.id.user_psd);
        mSignUp=(Button)findViewById(R.id.btn_signup);
        back=(Button)findViewById(R.id.back);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=mUserName.getText().toString();
                psd=mUserPsd.getText().toString();
                if (name.length()!=0&&psd.length()!=0){
                    AVUser avUser=new AVUser();
                    avUser.setUsername(name);
                    avUser.setPassword(psd);
                    avUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(AVException e) {
                            Toast.makeText(getApplicationContext(),"注册成功!",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent();
                            intent.setClass(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(), "用户名或密码不能为空!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
                RegisterActivity.this.finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
