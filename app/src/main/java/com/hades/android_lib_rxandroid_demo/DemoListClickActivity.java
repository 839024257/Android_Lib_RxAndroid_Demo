package com.hades.android_lib_rxandroid_demo;

import com.hades.android_lib_rxandroid_demo.base.BaseSampleListClickActivity;
import com.hades.android_lib_rxandroid_demo.base.LvItemBean;
import com.hades.android_lib_rxandroid_demo.creating_observables.CreateFragment;
import com.hades.android_lib_rxandroid_demo.creating_observables.DeferFragment;
import com.hades.android_lib_rxandroid_demo.creating_observables.FromFragment;
import com.hades.android_lib_rxandroid_demo.creating_observables.IntervalFragment;
import com.hades.android_lib_rxandroid_demo.creating_observables.JustFragment;
import com.hades.android_lib_rxandroid_demo.creating_observables.RepeatFragment;
import com.hades.android_lib_rxandroid_demo.creating_observables.TimerFragment;

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
                doPositionClick(new TimerFragment());
                break;

            case 2:
                doPositionClick(new IntervalFragment());
                break;

            case 3:
                doPositionClick(new JustFragment());
                break;

            case 4:
                doPositionClick(new CreateFragment());
                break;

            case 5:
                doPositionClick(new FromFragment());
                break;

            case 6:
                doPositionClick(new DeferFragment());
                break;

            case 7:
                doPositionClick(new RepeatFragment());
                break;

            default:
                break;
        }
    }

    @Override
    public void createDataSource4Lv(List<LvItemBean> mDataSource) {
        mDataSource.add(0, new LvItemBean("Sample 1", "btn0"));
        mDataSource.add(new LvItemBean("Observable.timer()", "btn1"));
        mDataSource.add(new LvItemBean("Observable.interval()", "btn2"));
        mDataSource.add(new LvItemBean("Observable.just()", "btn3"));
        mDataSource.add(new LvItemBean("Observable.create()", "btn4"));
        mDataSource.add(new LvItemBean("Observable.from()", "btn5"));
        mDataSource.add(new LvItemBean("Observable.defer()", "btn6"));
        mDataSource.add(new LvItemBean("Observable.repeat()", "btn7"));
    }

    @Override
    public String getActivityTag() {
        return TAG;
    }
}

