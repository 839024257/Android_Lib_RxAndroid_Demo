package com.hades.android_lib_rxandroid_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity implements LvItemAdapter.ILvItemBtnClickDelegate {
    private static final String TAG = "MainActivity";
    @InjectView(R.id.lv)
    ListView listView;

    LvItemAdapter mAdapter;
    List<LvItemBean> mDataSource = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        createDataSource();
        mAdapter = new LvItemAdapter(MainActivity.this, mDataSource);
        mAdapter.setDelegate(this);

        listView.setAdapter(mAdapter);
    }

    /**
     * Observable.create
     */
    private void sendStringForPosition0() {
        final String str = getBuiltStrData();
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(str);
                subscriber.onCompleted();
            }
        });

        Subscriber subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: s=" + s);
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        };
        observable.subscribe(subscriber);
        // send for the second time
        observable.subscribe(subscriber);
    }

    /**
     * Observable.just is a simplification for Observable.create.
     */
    private void sendStringForPosition1() {
        final String str = getBuiltStrData();
        Observable<String> observable = Observable.just(str + ",position1");
        Action1<String> action1Receiver = new Action1<String>() {
            @Override
            public void call(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        };
        observable.subscribe(action1Receiver);
    }

    /**
     * map的功能就是在observable和subscribe之间可以对数据进行操作。
     */
    private void mapTrans4Position2() {
        final String str = getBuiltStrData();
        Func1<String, Integer> func1Receiver = new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                return (null == s) ? 0 : s.length();
            }
        };

        Action1<Integer> action1Receiver = new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Toast.makeText(MainActivity.this, String.valueOf(integer), Toast.LENGTH_SHORT).show();
            }
        };
        Observable.just(str).map(func1Receiver).subscribe(action1Receiver);
    }

    /**
     * [RxJava] Observer as receiver.
     */
    private void observerAsReceiver4Position3() {
        final String str = getBuiltStrData();
        Observable<String> observable = Observable.just(str + ",Position3");
        Observer<String> observerReceiver = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: ");
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        };
        observable.subscribe(observerReceiver);
    }


    /**
     * <pre>
     * [RxJava] How Observable to emit the story from API server.
     * onCompleted
     * </pre>
     */
    private void observerAsReceiver4Position4() {
        Log.d(TAG, "observerAsReceiver4Position4: " + Thread.currentThread().getId());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Observable<UserInfo> observable = Observable.create(new Observable.OnSubscribe<UserInfo>() {
                    @Override
                    public void call(Subscriber<? super UserInfo> subscriber) {
                        Log.d(TAG, "call: " + Thread.currentThread().getId());
                        if (subscriber.isUnsubscribed()) {
                            return;
                        }

                        try {
                            UserInfo topStory = getTopStory();
                            subscriber.onNext(topStory);

//                    subscriber.onCompleted();

                            UserInfo newestStory = getNewestStory();
                            subscriber.onNext(newestStory);

                            subscriber.onCompleted();

                            throw new JSONException("JSONException");
                        } catch (JSONException ex) {
                            subscriber.onError(ex); //6
                        }

                    }
                });

                Subscriber<UserInfo> subscriberAsReceiver = new Subscriber<UserInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        Log.d(TAG, "onNext: " + Thread.currentThread().getId());
                        Log.d(TAG, "onNext: userInfo=" + userInfo.toString());
                    }
                };
                observable.subscribe(subscriberAsReceiver);
            }
        }).start();

    }

    /**
     * <pre>
     * [RaJava] Schedulers决定了Observables在哪个线程发射他们的异步数据流，也决定了Observers在哪个线程消费这些数据流。
     * <pre/>
     */
    private void Schedulers4Position5() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.d(TAG, "run: threadId=" + Thread.currentThread().getId());

                /**
                 * <pre>
                 *     Schedulers.io()会返回一个Scheduler，用来调度Observable.OnSubscribe对象里面的call()方法跑在一个I/O线程(sub thread)上。
                 *     使用Scheduler还有另一个方法:observeOn()。使用这个方法决定Observer在哪一个线程上消费由Observable subscribeOn()方法发出的数据，因此你能把observeOn()方法关联到通过subscribeOn()返回的Observable上:
                 *     AndroidSchedulers.mainThread()实际上不属于RxJava库，但是可以通过依赖RxAndroid库来使用。调用observeOn()方法主要点在于，你可以修改Observer消费由Observable发出的数据的线程。
                 * </pre>
                 */
                Observable observable = Observable.create(new Observable.OnSubscribe<UserInfo>() {
                    @Override
                    public void call(Subscriber<? super UserInfo> subscriber) {
                        Log.d(TAG, "call: threadId=" + Thread.currentThread().getId());
                        subscriber.onNext(getNewestStory());
                        subscriber.onCompleted();
                    }
                })
//                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

                Subscriber<UserInfo> subscriberAsReceiver = new Subscriber<UserInfo>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(UserInfo userInfo) {
                        Log.d(TAG, "onNext: userInfo=" + userInfo);
                        Log.d(TAG, "onNext: threadId=" + Thread.currentThread().getId());

                    }
                };

                observable.subscribe(subscriberAsReceiver);
            }
        }).start();
    }

    @Override
    public void onClick(int position) {
        switch (position) {
            case 0:
                sendStringForPosition0();
                break;

            case 1:
                sendStringForPosition1();
                break;

            case 2:
                mapTrans4Position2();
                break;

            case 3:
                observerAsReceiver4Position3();
                break;

            case 4:
                observerAsReceiver4Position4();
                break;

            case 5:
                Schedulers4Position5();
                break;


            default:
                break;
        }
    }

    public void createDataSource() {
        mDataSource.add(0, new LvItemBean("Send a string", "btn0"));
        mDataSource.add(1, new LvItemBean("Send a string. Observable.just is a simplification for Observable.create.", "btn1"));
        mDataSource.add(2, new LvItemBean("map的功能就是在observable和subscribe之间可以对数据进行操作", "btn2"));
        mDataSource.add(3, new LvItemBean("[RxJava] Observer as receiver.", "btn3"));
        mDataSource.add(4, new LvItemBean("[RxJava] How Observable to emit the story from API server..", "btn4"));
        mDataSource.add(5, new LvItemBean("[RaJava] Schedulers决定了Observables在哪个线程发射他们的异步数据流，也决定了Observers在哪个线程消费这些数据流。", "btn5"));
    }

    private String getBuiltStrData() {
        return "Hello";
    }

    private UserInfo getTopStory() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        return new UserInfo("Tom", "20160719");
    }

    private UserInfo getNewestStory() {
        return new UserInfo("Jerry", "2016072");
    }

    @Override
    protected void onDestroy() {
        ButterKnife.reset(this);
        super.onDestroy();
    }

    public class UserInfo {
        String name;
        String time;

        public UserInfo() {
        }

        public UserInfo(String name, String time) {
            this.name = name;
            this.time = time;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "name='" + name + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }
    }

    /**
     * <pre>
     *     Ref:
     *     http://blog.csdn.net/eastmoon502136/article/details/50846646
     * </pre>
     */
}
