package com.example.db.tline.activity;

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
import android.widget.ListView;
import android.widget.TextView;

import com.example.db.tline.MainActivity;
import com.example.db.tline.R;
import com.example.db.tline.adapter.PictureLineDetailsAdapter;
import com.example.db.tline.adapter.TextLineDetailsAdapter;
import com.example.db.tline.beans.PLineListInfo;
import com.example.db.tline.beans.TLineListInfo;
import com.example.db.tline.database.PLineListSQLIDataBaseHelper;
import com.example.db.tline.database.TLineListSQLiDateBaseHelper;
import com.example.db.tline.utils.AppConstant;
import com.example.db.tline.view.RevealLayout;

import java.util.ArrayList;

public class PLineDetailsActivity extends ActionBarActivity {

    public com.example.db.tline.floatingactionbutton.FloatingActionButton mFloatingActionButton;
    public ListView mListView;
    public Button back;

    public TextView mTitle,mDate;

    public String title=null;
    public String content=null;
    public String date=null;

    public RevealLayout mRevealLayout;
    public boolean mIsAnimationSlowDown = false;
    public boolean mIsBaseOnTouchLocation = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppConstant.setStatus(true,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pline_details);

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

        Bundle bundle=this.getIntent().getBundleExtra("adapter");
        if (bundle!=null){
            title=bundle.getString("title");
            date=bundle.getString("date");
            content=bundle.getString("content");

        }



        mListView=(ListView)findViewById(R.id.text_line);
        View mHeadView= LayoutInflater.from(this).inflate(R.layout.head_view,null);
        mTitle=(TextView)mHeadView.findViewById(R.id.title);
        mDate=(TextView)mHeadView.findViewById(R.id.date);

        if (title.length()!=0&&date.length()!=0){
            mTitle.setText(title);
            mDate.setText(date);
        }

        View mFootView =LayoutInflater.from(this).inflate(R.layout.foot_view,null);
        mListView.addHeaderView(mHeadView);
        mListView.addFooterView(mFootView);


        back=(Button)findViewById(R.id.back);

        mFloatingActionButton=(com.example.db.tline.floatingactionbutton.FloatingActionButton)findViewById(R.id.fab_edit);


        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(PLineDetailsActivity.this,EditPLineDetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("title",title);
                Log.v("jjjj",title);
                bundle.putString("content",content);
                bundle.putString("date",date);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PLineDetailsActivity.this, MainActivity.class);
                startActivity(intent);
                PLineDetailsActivity.this.finish();
//                overridePendingTransition(R.anim.activity_up_move_in,R.anim.abc_fade_out);
            }
        });

        if (getPLineListInfo().size()!=0){
            PictureLineDetailsAdapter pictureLineDetailsAdapter=new PictureLineDetailsAdapter(getApplicationContext(),getPLineListInfo());
            mListView.setAdapter(pictureLineDetailsAdapter);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pline_details, menu);
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

    public ArrayList<PLineListInfo> getPLineListInfo(){

        ArrayList<PLineListInfo> pLineListInfos=new ArrayList<PLineListInfo>();
        PLineListSQLIDataBaseHelper pLineListSQLIDataBaseHelper=new PLineListSQLIDataBaseHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase=pLineListSQLIDataBaseHelper.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.query("picturelinelist",new String[]{"relationtitle","relationcontent","relationdate","title","content","date","url"},null,null,null,null,null);

        if (cursor.getCount()!=0){
            for (int i=0;i<cursor.getCount();i++){
                cursor.moveToPosition(i);
                if (cursor.getString(cursor.getColumnIndex("relationtitle")).equals(title)
                        &&cursor.getString(cursor.getColumnIndex("relationcontent")).equals(content)){
                    PLineListInfo pLineListInfo=new PLineListInfo();
                    pLineListInfo.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    pLineListInfo.setContent(cursor.getString(cursor.getColumnIndex("content")));
                    pLineListInfo.setDate(cursor.getString(cursor.getColumnIndex("date")));
                    pLineListInfo.setRelationtitle(cursor.getString(cursor.getColumnIndex("relationtitle")));
                    pLineListInfo.setRelationcontent(cursor.getString(cursor.getColumnIndex("relationcontent")));
                    pLineListInfo.setRelationdate(cursor.getString(cursor.getColumnIndex("relationdate")));
                    pLineListInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));

                    pLineListInfos.add(pLineListInfo);
                }

            }
        }

        return pLineListInfos;
    }
}
