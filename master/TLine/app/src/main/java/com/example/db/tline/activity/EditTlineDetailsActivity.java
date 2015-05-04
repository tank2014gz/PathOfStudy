package com.example.db.tline.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.db.tline.R;
import com.example.db.tline.database.TLineListSQLiDateBaseHelper;
import com.example.db.tline.floatingactionbutton.FloatingActionButton;
import com.example.db.tline.utils.AppConstant;
import com.example.db.tline.view.RevealLayout;

public class EditTlineDetailsActivity extends ActionBarActivity {

    public EditText mTitle,mContent;
    public Button back;
    public FloatingActionButton save;

    public String title=null;
    public String content=null;

    public String relation_title=null;
    public String relation_content=null;
    public String relation_date=null;
    public Bundle bundle;

    public RevealLayout mRevealLayout;
    public boolean mIsAnimationSlowDown = false;
    public boolean mIsBaseOnTouchLocation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppConstant.setStatus(true,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tline_details);

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

        bundle=this.getIntent().getExtras();

        if (bundle!=null){
                relation_title=bundle.getString("title");
                Log.v("hhhh",relation_title);
                relation_date=bundle.getString("date");
                relation_content=bundle.getString("content");
            }


        mTitle=(EditText)findViewById(R.id.title);
        mContent=(EditText)findViewById(R.id.content);
        back=(Button)findViewById(R.id.back);
        save=(FloatingActionButton)findViewById(R.id.save);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditTlineDetailsActivity.this,TLineDetailsActivity.class);
                Bundle bundle =new Bundle();
                bundle.putString("title",relation_title);
                Log.v("hhhh1",relation_title);
                bundle.putString("content",relation_content);
                bundle.putString("date",relation_date);
                intent.putExtra("adapter",bundle);
                startActivity(intent);
                EditTlineDetailsActivity.this.finish();
//                overridePendingTransition(R.anim.activity_up_move_in,R.anim.abc_fade_out);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                TLineListSQLiDateBaseHelper tLineListSQLiDateBaseHelper=new TLineListSQLiDateBaseHelper(getApplicationContext());
                SQLiteDatabase sqLiteDatabase=tLineListSQLiDateBaseHelper.getWritableDatabase();
//                Cursor cursor=sqLiteDatabase.query("tlinelist",new String[]{"relationtitle","relationcontent","relationdate","title","content","date"},null,null,null,null,null);

                title=mTitle.getText().toString();
                content=mContent.getText().toString();


                ContentValues contentValues=new ContentValues();
                contentValues.put("relationtitle",relation_title);
                contentValues.put("relationcontent",relation_content);
                contentValues.put("relationdate",relation_date);
                contentValues.put("title",title);
                contentValues.put("content",content);
                contentValues.put("date",AppConstant.getCurrentTime());
                Log.v("kkk",relation_title);
                Log.v("kkk",relation_content);
                Log.v("kkk",relation_date);
                Log.v("kkk",title);
                Log.v("kkk",content);
                Log.v("kkk",AppConstant.getCurrentTime());


                sqLiteDatabase.insert("textlinelist",null,contentValues);
                sqLiteDatabase.close();

                Intent intent=new Intent(EditTlineDetailsActivity.this,TLineDetailsActivity.class);
                Bundle bundle =new Bundle();
                bundle.putString("title",relation_title);
                bundle.putString("content",relation_content);
                bundle.putString("date",relation_date);
                intent.putExtra("adapter",bundle);
                startActivity(intent);
//                overridePendingTransition(R.anim.activity_up_move_in,R.anim.abc_fade_out);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_tline_details, menu);
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
