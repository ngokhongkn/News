package com.coccoc.news.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.coccoc.news.R;
import com.coccoc.news.ui.fragment.MainSettingFragment;

public class NewsSettingActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_setting);
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationOnClickListener(view -> onBackPressed());
        openSettingFragment();
    }

    private void openSettingFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout, new MainSettingFragment())
                .commit();
        updateToolbarTitle("Tùy biến cho tin tức");
    }

    private void updateToolbarTitle(String message) {
        mToolbar.setTitle(message);
    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().popBackStack();
        if (getSupportFragmentManager().getBackStackEntryCount() <= 0) {
            super.onBackPressed();
        }
    }
}
