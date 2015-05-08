package com.example.db.tline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.db.tline.fragment.HomeFragment;
import com.example.db.tline.utils.AppConstant;
import com.example.db.tline.view.RevealLayout;
import com.example.db.tline.view.materialedittext.MaterialEditText;
import com.example.db.tline.view.switchbutton.SwitchButton;


public class SetPasswordActivity extends ActionBarActivity implements View.OnClickListener{

    public RevealLayout mRevealLayout;
    public boolean mIsAnimationSlowDown = false;
    public boolean mIsBaseOnTouchLocation = false;

    public Button btn_zero,btn_one,btn_two,btn_three,btn_four,btn_five,btn_six,btn_seven,btn_eight,btn_nine;
    public MaterialEditText materialEditText;
    public Button mTick,mDelete,mBack;

    public SwitchButton switchButton;

    public String psd="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppConstant.setStatus(true,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

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


        initWidget();

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.db.tline", Context.MODE_PRIVATE); //私有数据
                    SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                    editor.putBoolean("status",true);
                    editor.commit();//提交修改
                }else {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.db.tline", Context.MODE_PRIVATE); //私有数据
                    SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                    editor.putBoolean("status",false);
                    editor.commit();//提交修改
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_password, menu);
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_ok:

                if (materialEditText.length()!=0&&materialEditText.length()==4){
                    SharedPreferences sharedPreferences = this.getSharedPreferences("com.db.tline", Context.MODE_PRIVATE); //私有数据
                    SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                    editor.putString("password", psd);
                    editor.putBoolean("FLAG", true);
                    editor.commit();//提交修改

                    Intent intent=new Intent(SetPasswordActivity.this,MainActivity.class);
                    startActivity(intent);
                    SetPasswordActivity.this.finish();
                }else {
                    Toast.makeText(getApplicationContext(),"输入的密码格式不正确!",Toast.LENGTH_SHORT).show();
                }

                break;
        }
        switch (view.getId()) {
            case R.id.btn_zero:
                if (materialEditText.getText().length() == 0) {
                    materialEditText.setText("*");
                    psd = psd + "0";
                } else if (materialEditText.length() != 0 && materialEditText.length() == 1) {
                    materialEditText.setText("**");
                    psd = psd + "0";
                } else if (materialEditText.length() != 0 && materialEditText.length() == 2) {
                    materialEditText.setText("***");
                    psd = psd + '0';
                } else if (materialEditText.length() != 0 && materialEditText.length() == 3) {
                    materialEditText.setText("****");
                    psd = psd + "0";
                } else {
                    Toast.makeText(getApplicationContext(), "已经输入四位密码了!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_one:
                if (materialEditText.getText().length() == 0) {
                    materialEditText.setText("*");
                    psd = psd + "1";
                } else if (materialEditText.length() != 0 && materialEditText.length() == 1) {
                    materialEditText.setText("**");
                    psd = psd + "1";
                } else if (materialEditText.length() != 0 && materialEditText.length() == 2) {
                    materialEditText.setText("***");
                    psd = psd + '1';
                } else if (materialEditText.length() != 0 && materialEditText.length() == 3) {
                    materialEditText.setText("****");
                    psd = psd + "1";
                } else {
                    Toast.makeText(getApplicationContext(), "已经输入四位密码了!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_two:
                if (materialEditText.getText().length() == 0) {
                    materialEditText.setText("*");
                    psd = psd + "2";
                } else if (materialEditText.length() != 0 && materialEditText.length() == 1) {
                    materialEditText.setText("**");
                    psd = psd + "2";
                } else if (materialEditText.length() != 0 && materialEditText.length() == 2) {
                    materialEditText.setText("***");
                    psd = psd + '2';
                } else if (materialEditText.length() != 0 && materialEditText.length() == 3) {
                    materialEditText.setText("****");
                    psd = psd + "2";
                } else {
                    Toast.makeText(getApplicationContext(), "已经输入四位密码了!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_three:
                if (materialEditText.getText().length() == 0) {
                    materialEditText.setText("*");
                    psd = psd + "3";
                } else if (materialEditText.length() != 0 && materialEditText.length() == 1) {
                    materialEditText.setText("**");
                    psd = psd + "3";
                } else if (materialEditText.length() != 0 && materialEditText.length() == 2) {
                    materialEditText.setText("***");
                    psd = psd + '3';
                } else if (materialEditText.length() != 0 && materialEditText.length() == 3) {
                    materialEditText.setText("****");
                    psd = psd + "3";
                } else {
                    Toast.makeText(getApplicationContext(), "已经输入四位密码了!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_four:
                if (materialEditText.getText().length() == 0) {
                    materialEditText.setText("*");
                    psd = psd + "4";
                } else if (materialEditText.length() != 0 && materialEditText.length() == 1) {
                    materialEditText.setText("**");
                    psd = psd + "4";
                } else if (materialEditText.length() != 0 && materialEditText.length() == 2) {
                    materialEditText.setText("***");
                    psd = psd + '4';
                } else if (materialEditText.length() != 0 && materialEditText.length() == 3) {
                    materialEditText.setText("****");
                    psd = psd + "4";
                } else {
                    Toast.makeText(getApplicationContext(), "已经输入四位密码了!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_five:
                if (materialEditText.getText().length() == 0) {
                    materialEditText.setText("*");
                    psd = psd + "5";
                } else if (materialEditText.length() != 0 && materialEditText.length() == 1) {
                    materialEditText.setText("**");
                    psd = psd + "5";
                } else if (materialEditText.length() != 0 && materialEditText.length() == 2) {
                    materialEditText.setText("***");
                    psd = psd + '5';
                } else if (materialEditText.length() != 0 && materialEditText.length() == 3) {
                    materialEditText.setText("****");
                    psd = psd + "5";
                } else {
                    Toast.makeText(getApplicationContext(), "已经输入四位密码了!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_six:
                if (materialEditText.getText().length() == 0) {
                    materialEditText.setText("*");
                    psd = psd + "6";
                } else if (materialEditText.length() != 0 && materialEditText.length() == 1) {
                    materialEditText.setText("**");
                    psd = psd + "6";
                } else if (materialEditText.length() != 0 && materialEditText.length() == 2) {
                    materialEditText.setText("***");
                    psd = psd + '6';
                } else if (materialEditText.length() != 0 && materialEditText.length() == 3) {
                    materialEditText.setText("****");
                    psd = psd + "6";
                } else {
                    Toast.makeText(getApplicationContext(), "已经输入四位密码了!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_seven:
                if (materialEditText.getText().length() == 0) {
                    materialEditText.setText("*");
                    psd = psd + "7";
                } else if (materialEditText.length() != 0 && materialEditText.length() == 1) {
                    materialEditText.setText("**");
                    psd = psd + "7";
                } else if (materialEditText.length() != 0 && materialEditText.length() == 2) {
                    materialEditText.setText("***");
                    psd = psd + '7';
                } else if (materialEditText.length() != 0 && materialEditText.length() == 3) {
                    materialEditText.setText("****");
                    psd = psd + "7";
                } else {
                    Toast.makeText(getApplicationContext(), "已经输入四位密码了!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_eight:
                if (materialEditText.getText().length() == 0) {
                    materialEditText.setText("*");
                    psd = psd + "8";
                } else if (materialEditText.length() != 0 && materialEditText.length() == 1) {
                    materialEditText.setText("**");
                    psd = psd + "8";
                } else if (materialEditText.length() != 0 && materialEditText.length() == 2) {
                    materialEditText.setText("***");
                    psd = psd + '8';
                } else if (materialEditText.length() != 0 && materialEditText.length() == 3) {
                    materialEditText.setText("****");
                    psd = psd + "8";
                } else {
                    Toast.makeText(getApplicationContext(), "已经输入四位密码了!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_nine:
                if (materialEditText.getText().length() == 0) {
                    materialEditText.setText("*");
                    psd = psd + "9";
                } else if (materialEditText.length() != 0 && materialEditText.length() == 1) {
                    materialEditText.setText("**");
                    psd = psd + "9";
                } else if (materialEditText.length() != 0 && materialEditText.length() == 2) {
                    materialEditText.setText("***");
                    psd = psd + '9';
                } else if (materialEditText.length() != 0 && materialEditText.length() == 3) {
                    materialEditText.setText("****");
                    psd = psd + "9";
                } else {
                    Toast.makeText(getApplicationContext(), "已经输入四位密码了!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_delete:
                materialEditText.setText("");
                psd = "";
                break;
            case R.id.back:
                Intent intent=new Intent(SetPasswordActivity.this,MainActivity.class);
                startActivity(intent);
                SetPasswordActivity.this.finish();
                break;
        }
    }

    public void initWidget(){

        btn_zero=(Button)findViewById(R.id.btn_zero);
        btn_one=(Button)findViewById(R.id.btn_one);
        btn_two=(Button)findViewById(R.id.btn_two);
        btn_three=(Button)findViewById(R.id.btn_three);
        btn_four=(Button)findViewById(R.id.btn_four);
        btn_five=(Button)findViewById(R.id.btn_five);
        btn_six=(Button)findViewById(R.id.btn_six);
        btn_seven=(Button)findViewById(R.id.btn_seven);
        btn_eight=(Button)findViewById(R.id.btn_eight);
        btn_nine=(Button)findViewById(R.id.btn_nine);

        switchButton=(SwitchButton)findViewById(R.id.psd_switch);

        mTick=(Button)findViewById(R.id.btn_ok);
        mDelete=(Button)findViewById(R.id.btn_delete);
        mBack=(Button)findViewById(R.id.back);

        materialEditText=(MaterialEditText)findViewById(R.id.psd);

        btn_zero.setOnClickListener(this);
        btn_one.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        btn_four.setOnClickListener(this);
        btn_five.setOnClickListener(this);
        btn_six.setOnClickListener(this);
        btn_seven.setOnClickListener(this);
        btn_eight.setOnClickListener(this);
        btn_nine.setOnClickListener(this);

        mTick.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mBack.setOnClickListener(this);
    }
}
