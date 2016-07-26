package com.hades.android_lib_rxandroid_demo;

import com.hades.android_lib_rxandroid_demo.base.BaseSampleListClickActivity;
import com.hades.android_lib_rxandroid_demo.base.LvItemBean;

import java.util.List;

public class DemoListClickActivity extends BaseSampleListClickActivity {
    private static final String TAG = DemoListClickActivity.class.getSimpleName();

    @Override
    public String getDescTitle() {
        return "Demos for RxAndroid(Included RxJava)";
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                doPositionClick(new Sample1Fragment());
                break;
            case 1:
                doPositionClick(new Sample2Fragment());

            default:
                break;
        }
    }

    @Override
    public void createDataSource4Lv(List<LvItemBean> mDataSource) {
        mDataSource.add(0, new LvItemBean("Sample 1", "btn1"));
        mDataSource.add(1, new LvItemBean("Sample 2", "btn2"));
    }

    @Override
    public String getActivityTag() {
        return TAG;
    }
}

