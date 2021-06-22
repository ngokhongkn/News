package com.coccoc.news.holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.coccoc.news.R;
import com.coccoc.news.base.views.recycler.BaseRecyclerViewHolder;
import com.coccoc.news.base.views.recycler.RecyclerActionListener;
import com.coccoc.news.base.views.recycler.RecyclerData;
import com.coccoc.news.repo.model.Category;

public class CategoryViewHolder extends BaseRecyclerViewHolder {

    private CheckBox mCheckBox;
    private ImageView mImageView;
    private TextView mTextView;
    private Category mCategory;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        mCheckBox = itemView.findViewById(R.id.checkbox);
        mImageView = itemView.findViewById(R.id.imageView);
        mTextView = itemView.findViewById(R.id.textView);
    }

    @Override
    public void bindViewHolder(RecyclerData data) {
        if (data instanceof Category) {
            mCategory = (Category) data;
            mCheckBox.setChecked(mCategory.hasSubscribed());
            Glide.with(itemView).load(mCategory.getImageUrl()).into(mImageView);
            mTextView.setText(mCategory.getName());
        }
    }

    @Override
    public void setupClickableViews(RecyclerActionListener actionListener) {
        mCheckBox.setOnCheckedChangeListener((compoundButton, isCheck) -> mCategory.setSubscribed(isCheck));
    }
}
