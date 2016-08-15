package com.hades.android_lib_rxandroid_demo.base;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hades.android_lib_rxandroid_demo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by hades on 7/26/2016.
 */
public abstract class BaseSampleListClickFragment extends ListFragment implements ISampleFragment {
    private final String TAG = BaseSampleListClickFragment.class.getSimpleName();
    protected Context mContext = getActivity();

    private final String TAG_Title = "Title";
    private final String TAG_Desc = "Desc";

    @InjectView(R.id.desc)
    TextView desc;

    private List<HashMap<String, String>> mDataSource = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_sample_list_click_layout, container, false);
        ButterKnife.inject(this, view);

        createDataSource();
        desc.setText(getDescTitle());


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), mDataSource, android.R.layout.simple_list_item_2, new String[]{TAG_Title, TAG_Desc}, new int[]{android.R.id.text1, android.R.id.text2});
        setListAdapter(simpleAdapter);
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
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

    public abstract void createDataSource4Lv(List<HashMap<String, String>> mDataSource);

    public abstract String getFragmentTag();

    public HashMap<String, String> getItemBean(String title, String desc) {
        HashMap<String, String> bean = new HashMap<>();
        bean.put(TAG_Title, title);
        bean.put(TAG_Desc, desc);
        return bean;
    }
}
