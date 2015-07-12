package com.example.db.alife.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.db.alife.R;
import com.example.db.alife.utils.AppConstant;
import com.example.db.alife.view.ALifeToast;
import com.example.db.alife.view.materialedittext.MaterialEditText;
import com.umeng.fb.ConversationActivity;
import com.umeng.fb.FeedbackAgent;
import com.umeng.message.PushAgent;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedBackActivity extends AppCompatActivity {

    public Toolbar toolbar;

    public MaterialEditText mMaterialEditText;
    public EditText mEditText;
    public CircleImageView mCircleImageView;

    public String contacttype,feedcontent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        initToolBar();

        mMaterialEditText = (MaterialEditText)findViewById(R.id.edit_title);
        mEditText = (EditText)findViewById(R.id.edit_content);
        mCircleImageView = (CircleImageView)findViewById(R.id.send);

        mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                feedcontent = mEditText.getText().toString();

                if (feedcontent!=null){
                    Uri smsToUri = Uri.parse("smsto:" + "18627804616");
                    Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                    intent.putExtra("sms_body", feedcontent);
                    startActivity(intent);
                    FeedBackActivity.this.finish();
                }else {
                    ALifeToast.makeText(FeedBackActivity.this, "意见不能为空！", ALifeToast.ToastType.SUCCESS, ALifeToast.LENGTH_SHORT).show();

                }
            }
        });


    }

    private void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("意见反馈");
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


        return super.onOptionsItemSelected(item);
    }
}
