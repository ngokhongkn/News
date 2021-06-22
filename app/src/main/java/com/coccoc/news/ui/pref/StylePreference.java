package com.coccoc.news.ui.pref;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.coccoc.news.R;
import com.coccoc.news.ui.adapter.StringSpinAdapter;
import com.coccoc.news.factory.StyleFactory;
import com.coccoc.news.model.Style;
import com.coccoc.news.repo.Repo;
import com.coccoc.news.repo.RepoImpl;

import java.util.ArrayList;
import java.util.List;

public class StylePreference extends Preference {
    private List<Style> mStyles;
    private AppCompatImageView mImageStyleBg;
    private AppCompatImageView mImageBottomBar;
    private AppCompatSpinner mSpinnerStyle;
    private StringSpinAdapter mStyleAdapter;
    private Repo repo = RepoImpl.getInstance();

    public StylePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(R.layout.ccnews_setting_pref_style);
        mStyles = StyleFactory.getAllStyles();
    }

    private void updateStyleView(Style style) {
        mImageStyleBg.setBackgroundResource(style.getDrawableId());

        int index = mStyles.indexOf(style);
        if (index >= 0) {
            mSpinnerStyle.setSelection(index);
            mStyleAdapter.setSelectedPosition(index);
        }
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        mImageStyleBg = (AppCompatImageView) holder.findViewById(R.id.imageStyle);
        mSpinnerStyle = (AppCompatSpinner) holder.findViewById(R.id.spinnerStyle);
        mImageBottomBar = (AppCompatImageView) holder.findViewById(R.id.imageBar);

        initSpinner(holder.itemView.getContext());
        updateStyleView(repo.getCurrentStyle());
        setBottomBarVisible(repo.isToolbarVisible());
    }

    private List<String> getStyleNames() {
        List<String> names = new ArrayList<>();
        for (Style style : mStyles) {
            names.add(style.getName());
        }
        return names;
    }

    private void initSpinner(Context context) {
        mStyleAdapter = new StringSpinAdapter(context, getStyleNames());
        mSpinnerStyle.setAdapter(mStyleAdapter);
        mSpinnerStyle.setOnItemSelectedListener(mSpinnerAdapterListener);
    }

    private final AdapterView.OnItemSelectedListener mSpinnerAdapterListener =
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Style selectedStyle = mStyles.get(i);
                    updateStyleView(selectedStyle);
                    repo.setCurrentStyle(selectedStyle);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            };

    public void setBottomBarVisible(boolean toolbarVisible) {
        mImageBottomBar.setVisibility(toolbarVisible ? View.VISIBLE : View.GONE);
    }
}
