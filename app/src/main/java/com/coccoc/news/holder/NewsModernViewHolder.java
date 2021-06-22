package com.coccoc.news.holder;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.coccoc.news.R;
import com.coccoc.news.base.views.recycler.RecyclerData;
import com.coccoc.news.repo.model.News;

public class NewsModernViewHolder extends NewsViewHolder {
    private static final int OVERLAY_VIEW_ID = 100;

    private ViewGroup mImgContainer;
    private View mGradientView;
    private View spaceTop, spaceBottom;

    public NewsModernViewHolder(@NonNull View itemView) {
        super(itemView);
        mImgContainer = itemView.findViewById(R.id.imgContainer);
        spaceTop = itemView.findViewById(R.id.spaceTop);
        spaceBottom = itemView.findViewById(R.id.spaceBottom);
    }

    @Override
    public void bindViewHolder(RecyclerData data) {
        super.bindViewHolder(data);

        if (data instanceof News) {
            News news = (News) data;
            if (newsLayout != null) {
                ((CardView) newsLayout).setCardBackgroundColor(news.getBackground());
            }

            if (tvTitle != null) {
                tvTitle.setTextColor(news.getTitleColor());
            }

            if (tvDomain != null) {
                tvDomain.setTextColor(news.getDomainColor());
            }

            if (tvDescription != null) {
                tvDescription.setTextColor(news.getDescriptionColor());
            }

            if (tvTime != null) {
                tvTime.setTextColor(news.getDomainColor());
            }
        }
    }

    @Override
    protected void addOverlayIfNeeded(int imgWidth, int imgHeight, int background) {

        boolean isFirstItem = getLayoutPosition() <= 0;

        spaceBottom.setVisibility(isFirstItem ? View.VISIBLE : View.GONE);
        spaceTop.setVisibility(isFirstItem ? View.GONE : View.VISIBLE);

        FrameLayout.LayoutParams imageLayoutParam = (FrameLayout.LayoutParams) mImgContainer.getLayoutParams();
        imageLayoutParam.gravity = isFirstItem ? Gravity.BOTTOM : Gravity.TOP;
        mImgContainer.setLayoutParams(imageLayoutParam);

        if (mGradientView == null) {
            mGradientView = new View(itemView.getContext());
            mGradientView.setId(OVERLAY_VIEW_ID);
        }

        GradientDrawable gd = new GradientDrawable(isFirstItem ? GradientDrawable.Orientation.BOTTOM_TOP : GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.TRANSPARENT, background});
        gd.setGradientRadius(2);
        mGradientView.setBackground(gd);

        if (mImgContainer.findViewById(OVERLAY_VIEW_ID) == null) {
            mImgContainer.addView(mGradientView, new LinearLayout.LayoutParams(imgWidth, imgHeight));
        }
    }
}
