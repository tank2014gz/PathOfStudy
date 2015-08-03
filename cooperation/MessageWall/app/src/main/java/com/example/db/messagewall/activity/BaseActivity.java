package com.example.db.messagewall.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.db.messagewall.utils.ThemeHelper;
import com.support.android.designlibdemo.R;

public class BaseActivity extends AppCompatActivity {

    public int mThemeId = R.style.AppTheme_DarkActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mThemeId = ThemeHelper.getTheme(this);
        }
        else {
            mThemeId = savedInstanceState.getInt("ThemeId");
        }
        setTheme(mThemeId);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mThemeId != ThemeHelper.getTheme(this)) {
            reload();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("ThemeId", mThemeId);

    }

    protected void reload() {

        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
}
