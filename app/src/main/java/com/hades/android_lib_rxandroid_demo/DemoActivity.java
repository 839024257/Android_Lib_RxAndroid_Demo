package com.hades.android_lib_rxandroid_demo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class DemoActivity extends Activity implements LvItemAdapter.ILvItemBtnClickDelegate {
    private static final String TAG = DemoActivity.class.getSimpleName();

    @InjectView(R.id.desc)
    View desc;

    @InjectView(R.id.lv)
    ListView listView;

    @InjectView(R.id.container)
    LinearLayout container;

    LvItemAdapter mAdapter;
    List<LvItemBean> mDataSource = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity_layout);

        ButterKnife.inject(this);

        createDataSource();
        mAdapter = new LvItemAdapter(DemoActivity.this, mDataSource);
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
            listView.setVisibility(View.GONE);
            desc.setVisibility(View.GONE);
            return;
        }
        container.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        desc.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(int position) {
        switch (position) {
            case 0:
                doPositionClick(new Sample1Fragment());
                break;

            default:
                break;
        }
        showFragment(true);
    }

    private void doPositionClick(ISampleFragment iSampleFragment) {
        getFragmentManager().beginTransaction().add(R.id.container, (Fragment) iSampleFragment, iSampleFragment.getTAG()).addToBackStack(iSampleFragment.getTAG()).commit();
    }

    public void createDataSource() {
        mDataSource.add(0, new LvItemBean("Sample 1", "btn1"));
    }

    @Override
    protected void onDestroy() {
        ButterKnife.reset(this);
        super.onDestroy();
    }
}
