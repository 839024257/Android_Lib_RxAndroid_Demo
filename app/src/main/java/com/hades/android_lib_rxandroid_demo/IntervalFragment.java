package com.hades.android_lib_rxandroid_demo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hades.android_lib_rxandroid_demo.base.ISampleFragment;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by hades on 7/26/2016.
 */
public class IntervalFragment extends Fragment implements ISampleFragment {
    private final String TAG = IntervalFragment.class.getSimpleName();

    private Subscription observable4Interval;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interval_layout, container, false);

        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }


    /**
     * Observable.interval()
     * timer与interval都是默认运行在一个新线程上面
     * 每间隔指定时间发送一个递增的计数(轮询器)。
     */
    @OnClick(R.id.start)
    public void start() {
        if (!(null == observable4Interval || observable4Interval.isUnsubscribed())) {
            return;
        }

        observable4Interval = Observable.interval(1, 2, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(Long aLong) {
                Log.d(TAG, "position1,onNext : aLong = " + aLong);
            }

        });
    }

    /**
     * Observable.interval
     * 轮询器如何停止？ 执行Subscription .unsubscribe()。
     */
    @OnClick(R.id.stop)
    public void stop() {
        if (null == observable4Interval || observable4Interval.isUnsubscribed()) {
            return;
        }
        observable4Interval.unsubscribe();
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
