package com.coccoc.news;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import com.coccoc.news.base.views.recycler.RecyclerViewType;
import com.coccoc.news.factory.StyleFactory;
import com.coccoc.news.ui.NewsView;
import com.coccoc.news.ui.activity.NewsSettingActivity;
import com.coccoc.news.viewmodel.NewsViewModel;
import com.coccoc.news.viewmodel.ViewModelFactory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private NewsViewModel newsVM;
    private boolean mIsLightMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnChangeMode).setOnClickListener(this::changeStyle);
        findViewById(R.id.btnRefresh).setOnClickListener(this::refresh);

        newsVM = new ViewModelProvider(this, new ViewModelFactory()).get(NewsViewModel.class);

        NewsView newsView = findViewById(R.id.newsLayout);
        newsView.initNews(this);
    }

    private void refresh(View view) {
        newsVM.loadNews();
    }

    private void changeStyle(View view) {
        mIsLightMode = !mIsLightMode;
        ((NewsApp) getApplication()).changeMode(mIsLightMode);
    }

    public void openSetting(View view) {
        startActivity(new Intent(MainActivity.this, NewsSettingActivity.class));
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}