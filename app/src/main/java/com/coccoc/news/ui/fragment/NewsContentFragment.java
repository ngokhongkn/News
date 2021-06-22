package com.coccoc.news.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.coccoc.news.R;
import com.coccoc.news.repo.model.News;
import com.coccoc.news.viewmodel.BottomSheetViewModel;
import com.coccoc.news.viewmodel.ViewModelFactory;

public class NewsContentFragment extends Fragment {
    public static final String NEWS_TAG = "news";
    private News mNews;
    private BottomSheetViewModel mViewModel;

    private final View.OnClickListener mCloseListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mViewModel.close(new BottomSheetViewModel.Event(mNews));
        }
    };

    private final View.OnClickListener mOpenListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mViewModel.open(new BottomSheetViewModel.Event(mNews));
        }
    };

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.ccnews_bottom_sheet_item_layout, container, false);

        mViewModel = new ViewModelProvider(requireActivity(), new ViewModelFactory()).get(
                BottomSheetViewModel.class);
        TextView textTitle = rootView.findViewById(R.id.textTitle);
        textTitle.setOnClickListener(mOpenListener);

        TextView textUrl = rootView.findViewById(R.id.textUrl);
        textUrl.setOnClickListener(mOpenListener);

        ImageView imgFavicon = rootView.findViewById(R.id.imgFavicon);
        imgFavicon.setOnClickListener(mOpenListener);

        ImageView imgClose = rootView.findViewById(R.id.imgClose);
        imgClose.setOnClickListener(mCloseListener);

        WebView webView = rootView.findViewById(R.id.newsWebView);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mNews = (News) bundle.getSerializable(NEWS_TAG);

            if (mNews != null) {
                webView.loadUrl(mNews.getUrl());
                textTitle.setText(mNews.getTitle());
                textUrl.setText(mNews.getUrl());
                imgFavicon.setImageBitmap(mNews.getSource().getLogoBitmap());
            }
        }

        return rootView;
    }

}
