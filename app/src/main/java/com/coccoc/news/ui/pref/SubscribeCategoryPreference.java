package com.coccoc.news.ui.pref;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coccoc.news.R;
import com.coccoc.news.base.views.recycler.BaseRecyclerAdapter;
import com.coccoc.news.repo.model.Category;

import java.util.ArrayList;
import java.util.List;

public class SubscribeCategoryPreference extends Preference {
    private BaseRecyclerAdapter<Category> mCategoryAdapter;

    public SubscribeCategoryPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(R.layout.ccnews_setting_pref_category);
    }

    public void setCategoryData(List<Category> categories) {
        mCategoryAdapter.update(categories);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        RecyclerView rvCategory = (RecyclerView) holder.findViewById(R.id.rvCategory);
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mCategoryAdapter = new BaseRecyclerAdapter<>();
        rvCategory.setAdapter(mCategoryAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        Drawable dividerDrawable = getContext().getDrawable(R.drawable.ccnews_divider);
        if (dividerDrawable != null) {
            divider.setDrawable(dividerDrawable);
            rvCategory.addItemDecoration(divider);
        }
    }

    public List<Category> getCategories() {
        return mCategoryAdapter.getData();
    }
}
