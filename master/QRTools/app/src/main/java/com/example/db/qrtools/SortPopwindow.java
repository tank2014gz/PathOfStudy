package com.example.db.qrtools;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.db.qrtools.Qr.IDQRFragment;
import com.example.db.qrtools.Qr.IPAdressFragment;
import com.example.db.qrtools.Qr.MailQRFragment;
import com.example.db.qrtools.Qr.MessageQRFragment;
import com.example.db.qrtools.Qr.SelfDefineFragment;

/**
 * Created by db on 3/1/15.
 */
public class SortPopwindow extends PopupWindow{
    public ListView listView;
    public View menuView;
    public Context context;
    public static String[] title=new String[]{"自定义二维码","图片二维码","名片二维码","邮件二维码","短信二维码","网址二维码"};
    public SortPopwindow(final Activity context){
        super(context);
        this.context=context;
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        menuView=layoutInflater.inflate(R.layout.popwindow,null);
        listView=(ListView)menuView.findViewById(R.id.sort);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(context,R.layout.adapter_item,title);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentTransaction fragmentTransaction = context.getFragmentManager().beginTransaction();
                switch (i){
                    case 0:
                        SelfDefineFragment selfDefineFragment=new SelfDefineFragment();
                        selfDefineFragment.setArguments(context.getIntent().getExtras());
                        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right);
                        fragmentTransaction.replace(R.id.container,selfDefineFragment).commit();
                        dismiss();
                        break;
                    case 1:

                        break;
                    case 2:
                        IDQRFragment qridFragment=new IDQRFragment();
                        qridFragment.setArguments(context.getIntent().getExtras());
                        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right);
                        fragmentTransaction.replace(R.id.container,qridFragment).commit();
                        dismiss();
                        break;
                    case 3:
                        MailQRFragment mailQRFragment=new MailQRFragment();
                        mailQRFragment.setArguments(context.getIntent().getExtras());
                        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right);
                        fragmentTransaction.replace(R.id.container,mailQRFragment).commit();
                        dismiss();
                        break;
                    case 4:
                        MessageQRFragment messageQRFragment=new MessageQRFragment();
                        messageQRFragment.setArguments(context.getIntent().getExtras());
                        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right);
                        fragmentTransaction.replace(R.id.container,messageQRFragment).commit();
                        dismiss();
                        break;
                    case 5:
                        IPAdressFragment ipAdressFragment=new IPAdressFragment();
                        ipAdressFragment.setArguments(context.getIntent().getExtras());
                        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right);
                        fragmentTransaction.replace(R.id.container,ipAdressFragment).commit();
                        dismiss();
                        break;

                }
            }
        });
        //设置SelectPicPopupWindow的View
        this.setContentView(menuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(320);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(600);//695
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        menuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = menuView.findViewById(R.id.pop_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

    }
}
