package com.example.db.civil.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.db.civil.R;

import it.neokree.materialnavigationdrawer.util.Utils;

public class FeedBackActivity extends ActionBarActivity {

    public EditText editText;
    public Button back,send;

    public String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setStatus(true,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        editText=(EditText)findViewById(R.id.content);
        back=(Button)findViewById(R.id.back);
        send=(Button)findViewById(R.id.send);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FeedBackActivity.this,SubscribeActivity.class);
                startActivity(intent);
                FeedBackActivity.this.finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = editText.getText().toString();
                if (content.length() != 0) {
                    Uri smsToUri = Uri.parse("smsto:" + "18627804616");
                    Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                    intent.putExtra("sms_body", content);
                    startActivity(intent);
                    FeedBackActivity.this.finish();
                } else {
                    Toast.makeText(getApplicationContext(),"您还没有输入任何的内容!",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feed_back, menu);
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
