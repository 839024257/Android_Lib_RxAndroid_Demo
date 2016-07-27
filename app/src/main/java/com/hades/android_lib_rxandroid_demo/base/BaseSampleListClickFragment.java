package com.hades.android_lib_rxandroid_demo.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.hades.android_lib_rxandroid_demo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hades on 7/26/2016.
 */
public abstract class BaseSampleListClickFragment extends Fragment implements ISampleFragment, LvItemAdapter.ILvItemBtnClickDelegate {
    private final String TAG = BaseSampleListClickFragment.class.getSimpleName();
    protected Context mContext = getActivity();

    @InjectView(R.id.desc)
    TextView desc;

    @InjectView(R.id.lv)
    ListView listView;

    LvItemAdapter mAdapter;
    List<LvItemBean> mDataSource = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_sample_list_click_layout, container, false);
        ButterKnife.inject(this, view);
        createDataSource();
        mAdapter = new LvItemAdapter(getActivity(), mDataSource);
        mAdapter.setDelegate(this);
        mAdapter.setTextWhite(true);

        desc.setText(getDescTitle());

        listView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    @Override

    public void onClick(int position) {
        onItemClick(position);
    }

    public void createDataSource() {
        createDataSource4Lv(mDataSource);
    }

    @Override
    public String getTAG() {
        return getFragmentTag();
    }

    public abstract String getDescTitle();

    public abstract void onItemClick(int position);

    public abstract void createDataSource4Lv(List<LvItemBean> mDataSource);

    public abstract String getFragmentTag();
}
