package com.coccoc.news.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.coccoc.news.R;

import java.util.List;

public class StringSpinAdapter extends BaseAdapter implements SpinnerAdapter {
    private Context mContext;
    private List<String> mData;
    private int mSelectedPosition;

    public StringSpinAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.mData = data;
    }

    public void setSelectedPosition(int selectedPosition) {
        mSelectedPosition = selectedPosition;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolderDropdown holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.ccspinner_dropdown_item, null);
            holder = new ViewHolderDropdown(convertView);
            convertView.setTag(holder);

            if (position == 0) {
                convertView.setPadding(0, 8, 0, 0);
            } else if (position == getCount() - 1) {
                convertView.setPadding(0, 0, 0, 8);
            }
        } else {
            holder = (ViewHolderDropdown) convertView.getTag();
        }

        holder.bindData(mData.get(position), mSelectedPosition == position);
        return convertView;
    }

    @Override
    public int getCount() {
        return mData != null ? mData.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.ccspinner_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.bindData(mData.get(position));

        return convertView;
    }

    private static class ViewHolder {
        private final TextView mTvTitle;

        public ViewHolder(View view) {
            mTvTitle = view.findViewById(R.id.tvTitle);
        }

        public void bindData(String data) {
            mTvTitle.setText(data);
        }
    }

    private static class ViewHolderDropdown {
        private final TextView mTvTitle;
        private final ImageView mCboSelection;

        public ViewHolderDropdown(View view) {
            mTvTitle = view.findViewById(R.id.tvTitle);
            mCboSelection = view.findViewById(R.id.cboSelection);
        }

        public void bindData(String data, boolean isChecked) {
            mTvTitle.setText(data);
            mCboSelection.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        }
    }

}