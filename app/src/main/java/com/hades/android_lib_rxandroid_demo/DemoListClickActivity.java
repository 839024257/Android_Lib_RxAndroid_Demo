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
import com.hades.android_lib_rxandroid_demo.filtering_observables.AllFragment;
import com.hades.android_lib_rxandroid_demo.filtering_observables.ContainsFragment;
import com.hades.android_lib_rxandroid_demo.filtering_observables.FilterFragment;
import com.hades.android_lib_rxandroid_demo.filtering_observables.FirstFragment;

import java.util.HashMap;
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

            case 8:
                doPositionClick(new FilterFragment());
                break;

            case 9:
                doPositionClick(new FirstFragment());
                break;

            case 10:
                doPositionClick(new ContainsFragment());
                break;

            case 11:
                doPositionClick(new AllFragment());
                break;

            default:
                break;
        }
    }

    @Override
    public void createDataSource4Lv(List<HashMap<String, String>> mDataSource) {
        mDataSource.add(getItemBean("Sample 1", "btn0"));
        mDataSource.add(getItemBean("Observable.timer()", "btn1"));
        mDataSource.add(getItemBean("Observable.interval()", "btn2"));
        mDataSource.add(getItemBean("Observable.just()", "btn3"));
        mDataSource.add(getItemBean("Observable.create()", "btn4"));
        mDataSource.add(getItemBean("Observable.from()", "btn5"));
        mDataSource.add(getItemBean("Observable.defer()", "btn6"));
        mDataSource.add(getItemBean("Observable.repeat()", "btn7"));

        mDataSource.add(getItemBean("filter", "btn8"));
        mDataSource.add(getItemBean("first", "btn9"));
        mDataSource.add(getItemBean("contains", "btn10"));
        mDataSource.add(getItemBean("All", "btn11"));
    }

    @Override
    public String getActivityTag() {
        return TAG;
    }
}

