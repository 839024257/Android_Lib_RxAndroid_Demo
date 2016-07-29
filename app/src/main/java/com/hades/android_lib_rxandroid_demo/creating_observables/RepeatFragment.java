package com.hades.android_lib_rxandroid_demo.creating_observables;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hades.android_lib_rxandroid_demo.R;
import com.hades.android_lib_rxandroid_demo.base.ISampleFragment;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hades on 7/27/2016.
 */
public class RepeatFragment extends Fragment implements ISampleFragment {
    public static final String TAG = RepeatFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repeat_layout, container, false);

        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    @Override
    public String getTAG() {
        return TAG;
    }

    @OnClick(R.id.btn1)
    public void btn1() {
        /**
         *
         * <pre>
         btn1, call: integer=100
         btn1, call: integer=100
         </pre>
         */
        Log.d(TAG, "btn1: threadName=" + Thread.currentThread().getName());
        Observable.just(100).repeat(2).subscribeOn(Schedulers.io()).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d(TAG, "btn1, call: integer=" + integer);
                Log.d(TAG, "btn1: call,threadName=" + Thread.currentThread().getName());
            }
        });

    }

    @OnClick(R.id.btn2)
    public void btn2() {
        Log.d(TAG, "btn1: threadName=" + Thread.currentThread().getName());
        Observable.range(10, 11).repeat(2).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d(TAG, "btn1, call: integer=" + integer);
                Log.d(TAG, "btn1: call,threadName=" + Thread.currentThread().getName());
            }
        });
    }

    @OnClick(R.id.btn3)
    public void btn3() {
        Log.d(TAG, "threadName=" + Thread.currentThread().getName());
        Observable.just(1, 3).repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Void> observable) {
                return Observable.timer(5, TimeUnit.SECONDS);
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: integer=" + integer);
                Log.d(TAG, "onNext,threadName=" + Thread.currentThread().getName());
            }
        });
    }

    @OnClick(R.id.btn4)
    public void btn4() {
        Log.d(TAG, "threadName=" + Thread.currentThread().getName());
        Observable.just(1, 3).repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Void> observable) {
                return Observable.timer(5, TimeUnit.SECONDS);
            }
        }).observeOn(Schedulers.newThread()).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: integer=" + integer);
                Log.d(TAG, "onNext,threadName=" + Thread.currentThread().getName());
            }
        });
    }
}
