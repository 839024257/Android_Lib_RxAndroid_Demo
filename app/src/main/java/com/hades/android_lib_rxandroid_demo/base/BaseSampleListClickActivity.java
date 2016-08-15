package com.hades.android_lib_rxandroid_demo.base;

import android.app.Fragment;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hades.android_lib_rxandroid_demo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public abstract class BaseSampleListClickActivity extends ListActivity {
    private static final String TAG = BaseSampleListClickActivity.class.getSimpleName();

    private final String TAG_Title = "Title";
    private final String TAG_Desc = "Desc";

    @InjectView(R.id.desc)
    TextView desc;

    @InjectView(R.id.lvContainer)
    LinearLayout lvContainer;

    @InjectView(R.id.container)
    LinearLayout container;

    private List<HashMap<String, String>> mDataSource = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_base_sample_list_click_layout);

        ButterKnife.inject(this);

        desc.setText(getDescTitle());

        createDataSource();
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, mDataSource, android.R.layout.simple_list_item_2, new String[]{TAG_Title, TAG_Desc}, new int[]{android.R.id.text1, android.R.id.text2});
        setListAdapter(simpleAdapter);

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

        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        onItemClick(position);
        showFragment(true);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.reset(this);
        super.onDestroy();
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

    public abstract void createDataSource4Lv(List<HashMap<String, String>> mDataSource);

    public abstract String getActivityTag();

    public HashMap<String, String> getItemBean(String title, String desc) {
        HashMap<String, String> bean = new HashMap<>();
        bean.put(TAG_Title, title);
        bean.put(TAG_Desc, desc);
        return bean;
    }
}
