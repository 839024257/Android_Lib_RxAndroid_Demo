package com.hades.android_lib_rxandroid_demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yc99656 on 7/20/2016.
 */
public class LvItemAdapter extends BaseAdapter {
    private List<LvItemBean> mDataSource;
    private Context mContext;
    private ILvItemBtnClickDelegate mDelegate;

    public LvItemAdapter(Context context, List<LvItemBean> dataSource) {
        this.mContext = context;
        this.mDataSource = dataSource;
    }

    @Override
    public int getCount() {
        return (null == mDataSource) ? 0 : mDataSource.size();
    }

    @Override
    public LvItemBean getItem(int position) {
        return (null == mDataSource) ? null : mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_item_view, null);
            viewHolder.desc = (TextView) convertView.findViewById(R.id.desc);
            viewHolder.btn = (Button) convertView.findViewById(R.id.btn);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (null == getItem(position).descText || getItem(position).descText.isEmpty()) {
            viewHolder.desc.setVisibility(View.GONE);
        } else {
            viewHolder.desc.setText(getItem(position).descText);
        }

        viewHolder.btn.setText(getItem(position).btnText);

        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == mDelegate) {
                    return;
                }

                mDelegate.onClick(position);
            }
        });
        return convertView;
    }

    public void adItem(LvItemBean bean) {
        if (null == bean) {
            return;
        }

        if (null == mDataSource) {
            mDataSource = new ArrayList<>();
        }

        mDataSource.add(bean);
        notifyDataSetChanged();
    }

    public void setDelegate(ILvItemBtnClickDelegate delegate) {
        mDelegate = delegate;
    }

    public static class ViewHolder {
        public TextView desc;
        public Button btn;
    }

    public interface ILvItemBtnClickDelegate {
        void onClick(int position);
    }
}
