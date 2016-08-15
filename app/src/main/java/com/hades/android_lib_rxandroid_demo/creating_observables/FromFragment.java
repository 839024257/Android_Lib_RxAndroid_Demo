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
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by hades on 7/27/2016.
 */
public class FromFragment extends Fragment implements ISampleFragment {
    public static final String TAG = FromFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_from_layout, container, false);

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
         <pre>
         btn1,call: integer=10
         btn1,call: integer=11
         btn1,call: integer=12
         btn1,call: integer=13
         btn1,call: integer=14
         btn1,call: integer=15
         call: complete
         </pre>
         */
        Observable.from(DemoDatas.getIntegerArray()).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d(TAG, "btn1,call: integer=" + integer);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.d(TAG, "btn1,call: throwable");
            }
        }, new Action0() {
            @Override
            public void call() {
                Log.d(TAG, "call: complete");
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
