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
import rx.functions.Action1;

/**
 * Created by hades on 7/27/2016.
 */
public class TimerFragment extends Fragment implements ISampleFragment {
    public static final String TAG = TimerFragment.class.getSimpleName();
    Subscription observable4Timer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer_layout, container, false);

        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();

        stop();
    }

    /**
     * Observable.timer()
     * timer与interval都是默认运行在一个新线程上面
     * timer按指定时间延迟发送一个0L.
     */
    @OnClick(R.id.btn1)
    public void btn1() {

        Observable.timer(5, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                // position0_1, call: aLong=0
                Log.d(TAG, "position0_1, call: aLong=" + aLong);
            }
        });
    }


    /**
     * Timer 每间隔指定时间发送一个递增的计数(轮询器)（已经过时了，而是由interval操作符来间隔执行）
     */
    @OnClick(R.id.start)
    public void start() {

        if (!(null == observable4Timer || observable4Timer.isUnsubscribed())) {
            return;
        }

        observable4Timer = Observable.timer(0, 2, TimeUnit.SECONDS).subscribe(new Observer<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {
                // position0_1, call: aLong=0
                // position0_1, call: aLong=1
                // position0_1, call: aLong=2
                // position0_1, call: aLong=3
                // position0_1, call: aLong=...直到用代码终止
                Log.d(TAG, "position0_2, onNext: aLong=" + aLong);

            }
        });
    }

    @OnClick(R.id.stop)
    public void stop() {
        if (null == observable4Timer || observable4Timer.isUnsubscribed()) {
            return;
        }
        observable4Timer.unsubscribe();
    }


    @Override
    public String getTAG() {
        return TAG;
    }
}
