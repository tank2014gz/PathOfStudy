package com.example.db.tline.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.example.db.tline.MainActivity;
import com.example.db.tline.R;
import com.example.db.tline.utils.AppConstant;
import com.example.db.tline.view.RevealLayout;
import com.manuelpeinado.fadingactionbar.Utils;

public class LoginActivity extends ActionBarActivity {

    public Button back;

    public RevealLayout mRevealLayout;
    public boolean mIsAnimationSlowDown = false;
    public boolean mIsBaseOnTouchLocation = false;

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
