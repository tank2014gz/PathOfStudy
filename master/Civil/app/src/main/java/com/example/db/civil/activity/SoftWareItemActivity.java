package com.example.db.civil.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.db.civil.MainActivity;
import com.example.db.civil.R;
import com.example.db.civil.beans.MaterialInfo;
import com.melnykov.fab.FloatingActionButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import it.neokree.materialnavigationdrawer.util.Utils;

public class SoftWareItemActivity extends ActionBarActivity {


    public Button back,download;

    public TextView mRuleName,mDetails;

    public String url=null;

    public String name=null;

    public Handler handler;

    public String dowmloadUrl=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setStatus(true,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_ware_item);

        url=this.getIntent().getExtras().getString("url");
        name=this.getIntent().getExtras().getString("name");

        mRuleName=(TextView)findViewById(R.id.rule_name);
        mDetails=(TextView)findViewById(R.id.details);
        back=(Button)findViewById(R.id.back);
        download=(Button)findViewById(R.id.download);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SoftWareItemActivity.this, SoftDetailsActivity.class);
                startActivity(intent);
                SoftWareItemActivity.this.finish();
            }
        });

        mRuleName.setText(name);

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                MaterialInfo materialInfo=(MaterialInfo)msg.obj;
                if (materialInfo!=null){
                    String details=null;
                    details=materialInfo.getSize()+"\n\n"
                            +materialInfo.getEnviroment()+"\n\n"
                            +materialInfo.getLanguage()+"\n\n"
                            +materialInfo.getRate()+"\n\n"
                            +materialInfo.getRightform()+"\n\n"
                            +materialInfo.getUpdateTime()+"\n\n"
                            +materialInfo.getAuthor()+"\n\n"
                            +materialInfo.getInsertSoft()+"\n\n"
                            +materialInfo.getType()+"\n\n"
                            +materialInfo.getPsd()+"\n\n"
                            +materialInfo.getSafeCheck();
                    Log.v("mn",details);
                    mDetails.setText(details);
                    dowmloadUrl=materialInfo.getDownload();
                }else {
                    String details="资料大小：无"+"\n\n"
                            +"运行环境：无"+"\n\n"
                            +"资料语言：无"+"\n\n"
                            +"资料评级：无"+"\n\n"
                            +"授权形式：无"+"\n\n"
                            +"更新时间：无"+"\n\n"
                            +"发布作者：无"+"\n\n"
                            +"插件情况：无"+"\n\n"
                            +"文件类型：无"+"\n\n"
                            +"解压密码：无"+"\n\n"
                            +"安全检测：瑞星 江民 卡巴斯基 金山";
                    mDetails.setText(details);
                    dowmloadUrl="https://www.baidu.com";
                }
            }
        };

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dowmloadUrl!=null){
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(dowmloadUrl));
                   SoftWareItemActivity.this.startActivity(intent);
                }

            }
        });

        new RemoteDataTask0().execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_soft_ware_item, menu);
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
    private class RemoteDataTask0 extends AsyncTask<Void, Integer, MaterialInfo> {
        // Override this method to do custom remote calls

        public MaterialInfo materialInfo=new MaterialInfo();

        @Override
        protected MaterialInfo doInBackground(Void... params) {

            try {

                Document document= Jsoup.connect(url).get();
                Elements elements=document.getElementsByClass("m_g_c_left");
                Log.v("tr", String.valueOf(elements.size()));

                Elements download=document.getElementsByClass("m_g_downurl");

                for (int i=0;i<elements.size();i++){
                    Elements li=elements.get(i).getElementsByTag("li");
                    List<String> list=new ArrayList<String>();
                    for (int j=0;j<li.size();j++){
                        if (li.get(j).text()!=null){
                            list.add(li.get(j).text());
                        }else {
                            list.add("未找到相应内容!");
                        }
                        Log.v("dy",li.get(j).text());

                    }
                    materialInfo.setSize(list.get(0));
                    materialInfo.setEnviroment(list.get(1));
                    materialInfo.setLanguage(list.get(2));
                    materialInfo.setRate(list.get(3));
                    materialInfo.setRightform(list.get(4));
                    materialInfo.setUpdateTime(list.get(5));
                    materialInfo.setAuthor(list.get(6));
                    materialInfo.setInsertSoft(list.get(7));
                    materialInfo.setType(list.get(8));
                    materialInfo.setPsd(list.get(9));
                    materialInfo.setSafeCheck(list.get(10));
                    if (download.get(0).getElementsByTag("a").get(i).attr("href")!=null){
                        materialInfo.setDownload(download.get(0).getElementsByTag("a").get(i).attr("href"));
                    }else {
                        materialInfo.setDownload("https://www.baidu.com");
                    }

                }

            }catch (Exception e){
                e.printStackTrace();
            }

            return materialInfo;
        }
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);


        }
        @Override
        protected void onPostExecute(MaterialInfo result) {
            Message message=Message.obtain();
            message.what=0x123;
            message.obj=result;
            handler.sendMessage(message);
        }
    }
}
