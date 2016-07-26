package com.hades.android_lib_rxandroid_demo;

import android.widget.Toast;

import java.util.List;

/**
 * Created by hades on 7/26/2016.
 */
public class SampleFragment extends BaseSampleListClickFragment {
    private final String TAG = SampleFragment.class.getSimpleName();

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public String getDescTitle() {
        return null;
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "" + position + "," + getTAG(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createDataSource4Lv(List<LvItemBean> mDataSource) {
        mDataSource.add(new LvItemBean("1", "btn0"));
    }
}
