package com.hades.android_lib_rxandroid_demo.creating_observables;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hades.android_lib_rxandroid_demo.R;
import com.hades.android_lib_rxandroid_demo.base.DemoDatas;
import com.hades.android_lib_rxandroid_demo.base.ISampleFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;

/**
 * Created by hades on 7/27/2016.
 */
public class DeferFragment extends Fragment implements ISampleFragment {
    public static final String TAG = DeferFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_defer_layout, container, false);

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
        Observable.defer(new Func0<Observable<Integer>>() {
            @Override
            public Observable<Integer> call() {
                return Observable.just(DemoDatas.getInteger());
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                /**
                 * btn1,call: integer=2016
                 */
                Log.d(TAG, "btn1,call: integer=" + integer);
            }
        });
    }
//
//    @OnClick(R.id.btn2)
//    public void btn2() {
//
//    }

    //    @OnClick(R.id.btn3)
//    public void btn3() {
//
//    }

    //    @OnClick(R.id.btn4)
//    public void btn4() {
//
//    }
}
