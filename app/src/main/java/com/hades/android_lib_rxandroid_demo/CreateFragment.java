package com.hades.android_lib_rxandroid_demo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hades.android_lib_rxandroid_demo.base.ISampleFragment;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.observables.AsyncOnSubscribe;
import rx.observables.SyncOnSubscribe;

/**
 * Created by hades on 7/27/2016.
 */
public class CreateFragment extends Fragment implements ISampleFragment {
    public static final String TAG = CreateFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_layout, container, false);

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
        // QA: 7/28/2016 内存泄漏
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                // do string

                subscriber.onNext(10);
                subscriber.onNext(20);

//                subscriber.onCompleted();

                subscriber.onNext(100);

            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                /**
                 *onNext: integer=10
                 onNext: integer=20
                 onNext: integer=100
                 */

                /**
                 * onNext: integer=10
                 onNext: integer=20
                 */
                Log.d(TAG, "onNext: integer=" + integer);
            }
        });
    }

    @OnClick(R.id.btn2)
    public void btn2() {
        Observable.create(new AsyncOnSubscribe<List<String>, String>() {
            @Override
            protected List<String> generateState() {
                return null;
            }

            @Override
            protected List<String> next(List<String> state, long requested, Observer<Observable<? extends String>> observer) {
                return null;
            }
        });
    }

    @OnClick(R.id.btn3)
    public void btn3() {
        Observable.create(new SyncOnSubscribe<List<String>, String>() {
            @Override
            protected List<String> generateState() {
                return null;
            }

            @Override
            protected List<String> next(List<String> state, Observer<? super String> observer) {
                return null;
            }
        });

    }
}
