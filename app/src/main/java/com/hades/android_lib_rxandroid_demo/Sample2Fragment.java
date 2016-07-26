package com.hades.android_lib_rxandroid_demo;

import android.widget.Toast;

import com.hades.android_lib_rxandroid_demo.base.BaseSampleListClickFragment;
import com.hades.android_lib_rxandroid_demo.base.LvItemBean;

import java.util.List;

/**
 * Created by hades on 7/26/2016.
 */
public class Sample2Fragment extends BaseSampleListClickFragment {
    private final String TAG = Sample2Fragment.class.getSimpleName();

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public String getDescTitle() {
        return "Create Observables";
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
