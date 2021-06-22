package com.coccoc.news.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.coccoc.news.R;
import com.coccoc.news.listener.BottomSheetListener;
import com.coccoc.news.repo.model.News;
import com.coccoc.news.viewmodel.BottomSheetViewModel;
import com.coccoc.news.viewmodel.ViewModelFactory;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import static com.coccoc.news.ui.fragment.NewsContentFragment.NEWS_TAG;

public class NewsReadingFragment extends BottomSheetDialogFragment {
    public static final String KEY_NEWS_LIST = "key_news_list";
    public static final String KEY_NEWS_POSITION = "key_news_position";

    private List<News> newsList;
    private int currentPosition;

    private BottomSheetListener mBottomSheetListener;

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    public static NewsReadingFragment newInstance(ArrayList<News> newsList, int position) {
        NewsReadingFragment newsBottomSheetFragment = new NewsReadingFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_NEWS_LIST, newsList);
        bundle.putInt(KEY_NEWS_POSITION, position);

        newsBottomSheetFragment.setArguments(bundle);
        return newsBottomSheetFragment;
    }

    public void setBottomSheetListener(BottomSheetListener bottomSheetListener) {
        mBottomSheetListener = bottomSheetListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ccnews_bottom_sheet_fragment, container, false);
        ViewPager2 viewPager = rootView.findViewById(R.id.viewPager);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(KEY_NEWS_LIST)) {
            newsList = (List<News>) bundle.getSerializable(KEY_NEWS_LIST);
            currentPosition = bundle.getInt(KEY_NEWS_POSITION, 0);
        }

        NewsBottomSheetAdapter adapter = new NewsBottomSheetAdapter(this, newsList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentPosition, false);
        viewPager.setUserInputEnabled(newsList.size() <= 1);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomSheetViewModel viewModel = new ViewModelProvider(this, new ViewModelFactory()).get(BottomSheetViewModel.class);

        viewModel.getOpened().observe(getViewLifecycleOwner(), this::onActionChanged);
        viewModel.getClosed().observe(getViewLifecycleOwner(), this::onActionChanged);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void onActionChanged(BottomSheetViewModel.Event event) {
        if (event.isNotDone()) {
            event.done();
            NewsReadingFragment.this.dismiss();
        }
    }


    public static class NewsBottomSheetAdapter extends FragmentStateAdapter {
        private final List<News> mNewsList;

        public NewsBottomSheetAdapter(@NonNull Fragment fragment, List<News> newsList) {
            super(fragment);
            mNewsList = newsList;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            NewsContentFragment fragment = new NewsContentFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(NEWS_TAG, mNewsList.get(position));
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getItemCount() {
            return mNewsList == null ? 0 : mNewsList.size();
        }
    }
}
