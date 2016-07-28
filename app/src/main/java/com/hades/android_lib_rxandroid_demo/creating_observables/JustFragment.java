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
import com.hades.android_lib_rxandroid_demo.base.User;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by hades on 7/27/2016.
 */
public class JustFragment extends Fragment implements ISampleFragment {
    public static final String TAG = "JustFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_just_layout, container, false);

        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    @OnClick(R.id.btn1)
    public void btn1() {
        Observable.just(DemoDatas.getStrData())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        /**
                         * btn1,call:s =Hello
                         */
                        Log.d(TAG, "btn1,call:s =" + s);
                    }
                });
    }

    @OnClick(R.id.btn2)
    public void btn2() {
        Observable.just(DemoDatas.getStrData(), DemoDatas.getStrData2())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        /**
                         * btn2,call: s=Hello
                         * btn2,call: s=Open
                         */
                        Log.d(TAG, "btn2,call: s=" + s);
                    }
                });

    }

    @OnClick(R.id.btn3)
    public void btn3() {
        Observable.just(DemoDatas.getUser()).subscribe(new Action1<User>() {
            @Override
            public void call(User user) {

                /**
                 * btn3,call: user=User{name='Jerry', time='2016072'}
                 */
                Log.d(TAG, "btn3,call: user=" + user);
            }
        });
    }

    @OnClick(R.id.btn4)
    public void btn4() {
        Observable.from(DemoDatas.getStrList()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                /**
                 *btn4,call: s=s12
                 btn4,call: s=s123
                 btn4,call: s=s13
                 btn4,call: s=s135
                 */
                Log.d(TAG, "btn4,call: s=" + s);
            }
        });
    }

    @Override
    public String getTAG() {
        return TAG;
    }
}
