package com.hades.android_lib_rxandroid_demo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public abstract class BaseSampleListClickActivity extends Activity implements LvItemAdapter.ILvItemBtnClickDelegate {
    private static final String TAG = BaseSampleListClickActivity.class.getSimpleName();

    @InjectView(R.id.desc)
    TextView desc;

    @InjectView(R.id.lv)
    ListView listView;

    @InjectView(R.id.lvContainer)
    LinearLayout lvContainer;

    @InjectView(R.id.container)
    LinearLayout container;


    LvItemAdapter mAdapter;
    List<LvItemBean> mDataSource = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_base_sample_list_click_layout);

        ButterKnife.inject(this);

        desc.setText(getDescTitle());

        createDataSource();
        mAdapter = new LvItemAdapter(BaseSampleListClickActivity.this, mDataSource);
        mAdapter.setDelegate(this);
        listView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showFragment(false);
    }

    private void showFragment(boolean isShowFragment) {
        if (isShowFragment) {
            container.setVisibility(View.VISIBLE);
            lvContainer.setVisibility(View.GONE);
            desc.setVisibility(View.GONE);
            return;
        }
        container.setVisibility(View.GONE);
        lvContainer.setVisibility(View.VISIBLE);
        desc.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.reset(this);
        super.onDestroy();
    }

    public void onClick(int position) {
        onItemClick(position);
        showFragment(true);
    }

    public void createDataSource() {
        createDataSource4Lv(mDataSource);
    }

    public String getTAG() {
        return getActivityTag();
    }

    public void doPositionClick(ISampleFragment iSampleFragment) {
        getFragmentManager().beginTransaction().add(R.id.container, (Fragment) iSampleFragment, iSampleFragment.getTAG()).addToBackStack(iSampleFragment.getTAG()).commit();
    }

    public abstract String getDescTitle();

    public abstract void onItemClick(int position);

    public abstract void createDataSource4Lv(List<LvItemBean> mDataSource);

    public abstract String getActivityTag();
}
