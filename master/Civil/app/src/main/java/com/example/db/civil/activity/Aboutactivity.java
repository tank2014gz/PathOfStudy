package com.example.db.civil.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.db.civil.R;

import it.neokree.materialnavigationdrawer.util.Utils;

public class Aboutactivity extends ActionBarActivity {

    public TextView textView;
    public Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setStatus(true,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        textView=(TextView)findViewById(R.id.about);
        back=(Button)findViewById(R.id.back);

        String about="'每日建筑'是一款为建筑学学生打造的App,它提供丰富的世界著名建筑美文,还提供建筑土木相关的各种规范和软件下载.本App所有资源来自与'环球教育网'和'土木工程网',转载或者引用请标明出处,谢谢!";

        textView.setText(about);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Aboutactivity.this,SubscribeActivity.class);
                startActivity(intent);
                Aboutactivity.this.finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_aboutactivity, menu);
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
