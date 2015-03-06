package com.example.db.qrtools;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.db.qrtools.Qr.IDQRFragment;
import com.example.db.qrtools.Qr.IPAdressFragment;
import com.example.db.qrtools.Qr.MailQRFragment;
import com.example.db.qrtools.Qr.MessageQRFragment;
import com.example.db.qrtools.Qr.SelfDefineFragment;
import com.example.db.qrtools.Utils.Utils;
public class ProductQR extends ActionBarActivity implements SelfDefineFragment.OnFragmentInteractionListener,
        IDQRFragment.OnFragmentInteractionListener,
        MailQRFragment.OnFragmentInteractionListener,
        MessageQRFragment.OnFragmentInteractionListener,
        IPAdressFragment.OnFragmentInteractionListener{
    private Button sort,back;
    SortPopwindow sortPopwindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utils.setStatus(true, this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_qr);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        if (findViewById(R.id.container)!=null){
            if (savedInstanceState!=null){
                return;
            }
            SelfDefineFragment selfDefineFragment=new SelfDefineFragment();
            selfDefineFragment.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().replace(R.id.container, selfDefineFragment).commit();
        }
        sort=(Button)findViewById(R.id.sort);
        back=(Button)findViewById(R.id.back);
        sortPopwindow=new SortPopwindow(this);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sortPopwindow.setAnimationStyle(R.style.PopupAnimation);
                sortPopwindow.showAtLocation(sort, Gravity.TOP,240,170);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProductQR.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_qr, menu);
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
    public void onFragmentInteraction(Uri uri) {

    }
}
