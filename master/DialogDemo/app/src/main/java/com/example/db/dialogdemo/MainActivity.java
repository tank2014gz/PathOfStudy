package com.example.db.dialogdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoolMaterialDialog.Builder defaultBuilder = new CoolMaterialDialog.Builder(MainActivity.this);
                defaultBuilder.setPrimaryHeaderImageResource(R.drawable.scroll1)
                        .setOnPrimaryButtonClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "Primary button", Toast.LENGTH_SHORT).show();
                            }

                        }, true) // false if you don't want to dismiss the Dialog after the onClick is completed.
                        .setOnSecondaryButtonClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "Secondary button", Toast.LENGTH_SHORT).show();
                            }

                        }, true) // false if you don't want to dismiss the Dialog after the onClick is completed.
                        .setTitle("Vanilla")
                        .create().show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
