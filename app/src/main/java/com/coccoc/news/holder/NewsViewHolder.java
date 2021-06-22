package com.coccoc.news.holder;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.coccoc.news.R;
import com.coccoc.news.base.views.recycler.BaseRecyclerViewHolder;
import com.coccoc.news.base.views.recycler.RecyclerActionListener;
import com.coccoc.news.base.views.recycler.RecyclerData;
import com.coccoc.news.base.views.recycler.RecyclerViewType;
import com.coccoc.news.config.NewsConfig;
import com.coccoc.news.repo.model.News;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public abstract class NewsViewHolder extends BaseRecyclerViewHolder {
    private final static String DATE_FORMAT_SERVER = "yyyy-MM-dd hh:mm:ss";
    private final static String DATE_FORMAT_DESIGN = "dd/MM/yyyy";

    @Nullable
    protected TextView tvTitle;
    @Nullable
    protected TextView tvDescription;
    @Nullable
    protected ImageView imgCover;
    @Nullable
    protected ImageView imgLogo;
    @Nullable
    protected TextView tvDomain;
    @Nullable
    protected TextView tvTime;
    @Nullable
    protected View newsLayout;

    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvDescription = itemView.findViewById(R.id.tvDescription);
        imgCover = itemView.findViewById(R.id.imgNews);
        imgLogo = itemView.findViewById(R.id.imgLogo);
        tvDomain = itemView.findViewById(R.id.tvDomain);
        tvTime = itemView.findViewById(R.id.tvTime);
        newsLayout = itemView.findViewById(R.id.newsLayout);
    }

    @Override
    public void bindViewHolder(RecyclerData data) {
        if (data instanceof News) {
            News news = (News) data;

            // Title
            if (tvTitle != null) {
                tvTitle.setText(news.getTitle());
            }

            // Description
            if (tvDescription != null) {
                tvDescription.setText(news.getDescription());
            }

            // Main image of article
            if (imgCover != null) {
                Glide.with(imgCover)
                        .load(news.getImageUrl())
                        .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                                addOverlayIfNeeded(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), news.getBackground());
                                return false;
                            }
                        }).into(imgCover);
            }

            // Logo of source
            if (imgLogo != null) {
                Glide.with(imgLogo)
                        .load(news.getSource().getLogoBitmap())
                        .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                        .into(imgLogo);
            }

            // Domain
            if (tvDomain != null) {
                tvDomain.setText(news.getSource().getTitle());
            }

            // Publish time
            if (tvTime != null) {
                String eventTime = time2Text(news.getEventTime());
                tvTime.setText(eventTime);

                // Variation service to hide/show publish time
                boolean showTime = NewsConfig.isEnable(NewsConfig.VariationServiceFlag.CCNEWS_TEST_SHOW_TIME);
                tvTime.setVisibility(showTime ? View.VISIBLE : View.GONE);

            }
        }
    }

    protected void addOverlayIfNeeded(int imgWidth, int imgHeight, int background) {
        // Do nothing in default
    }


    public static NewsViewHolder create(View itemView, @RecyclerViewType int style) {
        switch (style) {
            case RecyclerViewType.TYPE_NEWS_STYLE_MODERN:
                return new NewsModernViewHolder(itemView);
            case RecyclerViewType.TYPE_NEWS_STYLE_SIMPLE_LARGE:
                return new NewsLargeViewHolder(itemView);
            case RecyclerViewType.TYPE_NEWS_STYLE_SIMPLE_MEDIUM:
            case RecyclerViewType.TYPE_NEWS_STYLE_SIMPLE_SMALL:
                return new NewsCompactViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void setupClickableViews(final RecyclerActionListener actionListener) {
        itemView.setOnClickListener(view -> {
            if (actionListener != null) {
                actionListener.onViewClick(getAdapterPosition(), itemView, NewsViewHolder.this);
            }
        });
    }

    private String time2Text(String date) {
        String result = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_SERVER, Locale.getDefault());
            Date newsDate = formatter.parse(date);
            formatter = new SimpleDateFormat(DATE_FORMAT_DESIGN, Locale.getDefault());
            date = formatter.format(newsDate);

            Date today = Calendar.getInstance().getTime();
            String todayString = formatter.format(today);
            result = String.format(" - %s", date.equals(todayString) ? "Hôm nay" : date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
