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
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by hades on 7/27/2016.
 */
public class AllFragment extends Fragment implements ISampleFragment {
    public static final String TAG = AllFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_layout, container, false);

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
                .all(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 10;
                    }
                }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                Log.d(TAG, "call: aBoolean=" + aBoolean);
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
