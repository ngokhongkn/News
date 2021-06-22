package com.coccoc.news.ui;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;

import com.coccoc.news.R;
import com.coccoc.news.base.AppLog;
import com.coccoc.news.base.repository.api.common.Resource;
import com.coccoc.news.base.repository.paging.PageResult;
import com.coccoc.news.base.views.recycler.BasePagingAdapter;
import com.coccoc.news.base.views.recycler.BaseRecyclerViewHolder;
import com.coccoc.news.base.views.recycler.RecyclerActionListener;
import com.coccoc.news.model.Reason;
import com.coccoc.news.model.Style;
import com.coccoc.news.repo.model.Category;
import com.coccoc.news.repo.model.News;
import com.coccoc.news.repo.model.Source;
import com.coccoc.news.ui.activity.NewsSettingActivity;
import com.coccoc.news.ui.fragment.NewsReadingFragment;
import com.coccoc.news.viewmodel.NewsViewModel;
import com.coccoc.news.viewmodel.ViewModelFactory;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class NewsView extends FrameLayout implements NewsBehavior, TabLayout.OnTabSelectedListener {

    private BasePagingAdapter<News> newsAdapter;

    private NewsViewModel newsVM;
    private ViewModelStoreOwner viewModelStoreOwner;
    private TabLayout categoryTabLayout;
    private List<Category> newsCategory;
    private Category selectedCategory;

    private final RecyclerActionListener newsActionListener = new RecyclerActionListener() {
        @Override
        public void onViewClick(int position, View view, BaseRecyclerViewHolder viewHolder) {
            ArrayList<News> newsList = new ArrayList<>();
            newsList.add(newsAdapter.getItem(position));
            NewsReadingFragment bottomFragment = NewsReadingFragment.newInstance(newsList, 0);
            if (getContext() instanceof AppCompatActivity) {
                bottomFragment.show(((AppCompatActivity) getContext()).getSupportFragmentManager(), NewsReadingFragment.class.getName());
            }
        }
    };

    public NewsView(@NonNull Context context) {
        super(context);
        inflate(context, null);
    }

    public NewsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void initNews(ViewModelStoreOwner viewModelStoreOwner) {
        this.viewModelStoreOwner = viewModelStoreOwner;
        newsVM = new ViewModelProvider(viewModelStoreOwner, new ViewModelFactory()).get(
                NewsViewModel.class);

        newsVM.queryPersonalizeNews(String.valueOf(System.currentTimeMillis())).observe(
                (LifecycleOwner) viewModelStoreOwner, this::observeNewsPaging);
        newsVM.queryCurrentStyle().observe((LifecycleOwner) viewModelStoreOwner,
                this::observeStyle);
        newsVM.querySubCategories().observe((LifecycleOwner) viewModelStoreOwner,
                this::observeSubscribeCategories);

        // Start loading news
        newsVM.loadNews();
    }

    private void inflate(@NonNull Context context, @Nullable AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.ccnews_layout, this, false);
        addView(view);
        setupViews();
    }

    private void setupViews() {
        newsAdapter = new BasePagingAdapter<>(newsActionListener);
        RecyclerView rvNews = findViewById(R.id.rvNews);

        rvNews.setAdapter(newsAdapter);
        categoryTabLayout = findViewById(R.id.categoryTabLayout);
        categoryTabLayout.addOnTabSelectedListener(this);
        ImageView newsSetting = findViewById(R.id.settingNews);
        newsSetting.setOnClickListener(
                view -> getContext().startActivity(
                        new Intent(getContext(), NewsSettingActivity.class)));
    }

    private void observeNewsPaging(PageResult<News> pageResult) {
        pageResult.getStatus().removeObservers((LifecycleOwner) viewModelStoreOwner);
        pageResult.getPageData().removeObservers((LifecycleOwner) viewModelStoreOwner);

        pageResult.getStatus().observe((LifecycleOwner) viewModelStoreOwner,
                this::observeStatus);
        pageResult.getPageData().observe((LifecycleOwner) viewModelStoreOwner,
                this::observeNews);
    }

    private void observeSubscribeCategories(Resource<List<Category>> resource) {
        if (!resource.isSuccess()) return;

        newsCategory = resource.getData();

        boolean found = false;

        int categorySize = newsCategory.size();
        for (int position = 0; position < categorySize; position++) {
            Category category = newsCategory.get(position);
            TabLayout.Tab tab = categoryTabLayout.getTabAt(position);
            if (tab == null) {
                View tabView = LayoutInflater.from(getContext()).inflate(R.layout.ccnews_tab_category, null);
                TextView tvTabTitle = tabView.findViewById(R.id.tvTabTitle);
                tvTabTitle.setText(category.getName());
                tab = categoryTabLayout.newTab().setCustomView(tabView);
                categoryTabLayout.addTab(tab, position);
            } else {
                TextView tvTabTitle = tab.view.findViewById(R.id.tvTabTitle);
                tvTabTitle.setText(category.getName());
            }

            if (category.getCategoryId() == selectedCategory.getCategoryId()) {
                found = true;
                categoryTabLayout.selectTab(tab);
            }
        }

        if (!found) {
            categoryTabLayout.selectTab(categoryTabLayout.getTabAt(0));
        }

        int tabCount = categoryTabLayout.getTabCount();
        for (int position = tabCount - 1; position >= newsCategory.size(); position--) {
            TabLayout.Tab tab = categoryTabLayout.getTabAt(position);
            if (tab != null) {
                categoryTabLayout.removeTab(tab);
            }
        }
    }

    private void observeStyle(Style style) {
        newsAdapter.notifyDataSetChanged();
    }

    private void observeNews(PagedList<News> news) {
        newsAdapter.submitList(news);
    }

    private void observeStatus(Integer status) {
        AppLog.d("Status: " + status);
    }

    @Override
    public void refresh() {
        newsVM.loadNews();
    }

    @Override
    public void scrollToTop() {

    }

    @Override
    public void hideArticle(News news) {

    }

    @Override
    public void hideSource(Source source) {
        newsVM.removeHidingSource(source);
    }

    @Override
    public void open(News news) {

    }

    @Override
    public void openInNewTab(News news) {

    }

    @Override
    public void openInNewIncognito(News news) {

    }

    @Override
    public void sendReport(News news, Reason reason) {
        newsVM.sendReportNews(news, reason);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        if (position < newsCategory.size()) {
            selectedCategory = newsCategory.get(position);
            newsVM.queryNewsByCategory(selectedCategory, String.valueOf(System.currentTimeMillis())).observe((LifecycleOwner) viewModelStoreOwner, this::observeNewsPaging);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
