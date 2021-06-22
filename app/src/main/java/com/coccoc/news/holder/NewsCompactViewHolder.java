package com.coccoc.news.holder;

import android.view.View;

import androidx.annotation.NonNull;

import com.coccoc.news.R;
import com.coccoc.news.base.views.recycler.RecyclerData;

public class NewsCompactViewHolder extends NewsViewHolder {
    public NewsCompactViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bindViewHolder(RecyclerData data) {
        super.bindViewHolder(data);
        if (newsLayout != null) {
            newsLayout.setBackgroundResource(getLayoutPosition() <= 0 ? R.drawable.ccnews_style_first_bg : R.drawable.ccnews_style_bg);
        }
    }
}
