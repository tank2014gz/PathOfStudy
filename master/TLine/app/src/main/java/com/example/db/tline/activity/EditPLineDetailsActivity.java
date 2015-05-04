package com.example.db.tline.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.db.tline.R;
import com.example.db.tline.database.PLineListSQLIDataBaseHelper;
import com.example.db.tline.database.PLineSQLiDataBaseHelper;
import com.example.db.tline.floatingactionbutton.FloatingActionButton;
import com.example.db.tline.fragment.FabPictureFragment;
import com.example.db.tline.utils.AppConstant;
import com.example.db.tline.view.RevealLayout;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class EditPLineDetailsActivity extends ActionBarActivity {

    public EditText mTitle,mContent;
    public Button back,select;
    public FloatingActionButton save;
    public ImageView preview;

    public String title=null;
    public String content=null;
    public String url="https://www.baidu.com";

    public String relation_title=null;
    public String relation_content=null;
    public String relation_date=null;
    public Bundle bundle;

    public RevealLayout mRevealLayout;
    public boolean mIsAnimationSlowDown = false;
    public boolean mIsBaseOnTouchLocation = false;

    public DisplayImageOptions options;
    public ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppConstant.setStatus(true,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pline_details);

        imageLoader=ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.login)
                .showImageForEmptyUri(R.drawable.login)
                .showImageOnFail(R.drawable.login)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();


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
            Log.v("hhhh", relation_title);
            relation_date=bundle.getString("date");
            relation_content=bundle.getString("content");
        }


        mTitle=(EditText)findViewById(R.id.title);
        mContent=(EditText)findViewById(R.id.content);
        back=(Button)findViewById(R.id.back);
        save=(FloatingActionButton)findViewById(R.id.save);
        select=(Button)findViewById(R.id.select);
        preview=(ImageView)findViewById(R.id.preview);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditPLineDetailsActivity.this,PLineDetailsActivity.class);
                Bundle bundle =new Bundle();
                bundle.putString("title",relation_title);
                Log.v("hhhh1",relation_title);
                bundle.putString("content",relation_content);
                bundle.putString("date",relation_date);
                intent.putExtra("adapter",bundle);
                startActivity(intent);
                EditPLineDetailsActivity.this.finish();
//                overridePendingTransition(R.anim.activity_up_move_in,R.anim.abc_fade_out);
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 2);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PLineListSQLIDataBaseHelper pLineListSQLIDataBaseHelper=new PLineListSQLIDataBaseHelper(getApplicationContext());
                SQLiteDatabase sqLiteDatabase=pLineListSQLIDataBaseHelper.getWritableDatabase();

                title=mTitle.getText().toString();
                content=mContent.getText().toString();

                ContentValues contentValues=new ContentValues();
                contentValues.put("relationtitle",relation_title);
                contentValues.put("relationcontent",relation_content);
                contentValues.put("relationdate",relation_date);
                contentValues.put("title",title);
                contentValues.put("content",content);
                contentValues.put("date",AppConstant.getCurrentTime());
                if (url.length()!=0&&url!=null){
                    contentValues.put("url",url);
                }else {
                    contentValues.put("url","https://www.baidu.com");
                }

                sqLiteDatabase.insert("picturelinelist",null,contentValues);
                sqLiteDatabase.close();

                Intent intent=new Intent(EditPLineDetailsActivity.this,PLineDetailsActivity.class);
                Bundle bundle =new Bundle();
                bundle.putString("title",relation_title);
                bundle.putString("content",relation_content);
                bundle.putString("date",relation_date);
                intent.putExtra("adapter",bundle);
                startActivity(intent);

            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_pline_details, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2&&resultCode== Activity.RESULT_OK){
            Uri uri=data.getData();
            Log.v("haha",uri.toString());

            url=uri.toString();

            if (url.length()!=0){
                imageLoader.displayImage(url, preview, options);
            }
        }
    }
}
