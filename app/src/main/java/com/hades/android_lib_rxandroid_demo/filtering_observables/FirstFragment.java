package com.hades.android_lib_rxandroid_demo.filtering_observables;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hades.android_lib_rxandroid_demo.R;
import com.hades.android_lib_rxandroid_demo.base.ISampleFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by hades on 7/27/2016.
 */
public class FirstFragment extends Fragment implements ISampleFragment {
    public static final String TAG = FirstFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_layout, container, false);

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
        Observable.just(1, 10, 20)
                .first().subscribe(new Subscriber<Integer>() {
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
            }
        });
    }

    @OnClick(R.id.btn2)
    public void btn2() {
        Observable.just(1, 10, 20)
                .first(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 5;
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
            }
        });
    }

    @OnClick(R.id.btn3)
    public void btn3() {
        Observable.just(1, 10, 20)
                .firstOrDefault(99, new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 30;
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
            }
        });
    }

    //    @OnClick(R.id.btn4)
//    public void btn4() {
//
//    }
}
