package com.coccoc.news.ui.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;

import com.coccoc.news.R;
import com.coccoc.news.base.repository.api.common.Resource;
import com.coccoc.news.config.NewsConfig;
import com.coccoc.news.repo.model.Category;
import com.coccoc.news.ui.pref.StylePreference;
import com.coccoc.news.ui.pref.SubscribeCategoryPreference;
import com.coccoc.news.viewmodel.NewsViewModel;
import com.coccoc.news.viewmodel.ViewModelFactory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public class MainSettingFragment extends PreferenceFragmentCompat {

    @Retention(RetentionPolicy.SOURCE)
    public @interface Key {
        String GROUP_CATEGORY = "groupCategory";
        String PREF_CATEGORY = "prefCategory";
    }

    private NewsViewModel mNewsViewModel;
    private PreferenceCategory mGroupCategory;
    private SubscribeCategoryPreference mPrefCategory;
    private Preference mPrefBottomBar;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.news_setting);
        mPrefCategory = findPreference(Key.PREF_CATEGORY);
        mGroupCategory = findPreference(Key.GROUP_CATEGORY);

        setPreferenceVisible(mGroupCategory, NewsConfig.isEnable(NewsConfig.VariationServiceFlag.CCNEWS_TEST_CATEGORY));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (NewsConfig.isEnable(NewsConfig.VariationServiceFlag.CCNEWS_TEST_CATEGORY)) {
            mNewsViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(NewsViewModel.class);
            mNewsViewModel.queryCategories().observe(getViewLifecycleOwner(), this::fetchCategories);
        }
    }

    private void fetchCategories(Resource<List<Category>> resource) {
//        setPreferenceVisible(mPrefCategory, resource.isSuccess());
        if (resource.isSuccess()) {
            mPrefCategory.setCategoryData(resource.getData());
        }
    }

    private void setPreferenceVisible(Preference preference, boolean visible) {
        if (preference != null) {
            preference.setVisible(visible);
        }
    }

    private void fetchToolbarVisible(Boolean enable) {
        mPrefBottomBar.setEnabled(enable);
    }

    private void initBottomBarPref() {
        mPrefBottomBar.setOnPreferenceChangeListener((preference, newValue) -> {
            mNewsViewModel.toggleToolbarVisibility();
            return true;
        });
    }

    @Override
    public void onDestroyView() {
        if (NewsConfig.isEnable(NewsConfig.VariationServiceFlag.CCNEWS_TEST_CATEGORY)) {
            mNewsViewModel.updateCategories(mPrefCategory.getCategories());
        }
        super.onDestroyView();
    }
}
