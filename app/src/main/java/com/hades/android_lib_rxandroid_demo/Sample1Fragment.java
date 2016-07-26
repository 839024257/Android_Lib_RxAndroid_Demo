package com.hades.android_lib_rxandroid_demo;

import android.util.Log;
import android.widget.Toast;

import com.hades.android_lib_rxandroid_demo.base.BaseSampleListClickFragment;
import com.hades.android_lib_rxandroid_demo.base.LvItemBean;

import org.json.JSONException;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hades on 7/26/2016.
 */
public class Sample1Fragment extends BaseSampleListClickFragment {
    private final String TAG = Sample1Fragment.class.getSimpleName();

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

        Subscriber<String> subscriber = new Subscriber<String>() {
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
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), String.valueOf(integer), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
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

    /**
     * [RxJava] The truth of position5 : 一个操作符是被应用于一个源Observable并且作为应用的结果它将返回一个新的Observable。
     */
    private void Schedulers4Position6() {
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
        });

        Observable observable2 = observable
                .subscribeOn(Schedulers.io())
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

        observable2.subscribe(subscriberAsReceiver);
    }

    /**
     * [RxJava] map操作符允许我们把一个发射故事的Observable变成一个发射这些故事标题的Observable。
     */
    private void mapSendUserPosition7() {
        demo71();
        demo72();
    }

    private void demo71() {
        Observable<UserInfo> observable = Observable.create(new Observable.OnSubscribe<UserInfo>() {
            @Override
            public void call(Subscriber<? super UserInfo> subscriber) {
                subscriber.onNext(getTopStory());
                subscriber.onCompleted();
            }
        });

        Observable<String> nameObservable = observable.map(new Func1<UserInfo, String>() {
            @Override
            public String call(UserInfo userInfo) {
                return userInfo.name + ", position 71";
            }
        });

        Subscriber<String> subscriberAsReceiver = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onNext: s=" + s);
            }
        };

        nameObservable.subscribe(subscriberAsReceiver);
    }

    private void demo72() {
        Subscription observable = Observable.create(new Observable.OnSubscribe<UserInfo>() {
            @Override
            public void call(Subscriber<? super UserInfo> subscriber) {
                subscriber.onNext(getTopStory());
                subscriber.onCompleted();
            }
        }).map(new Func1<UserInfo, String>() {
            @Override
            public String call(UserInfo userInfo) {
                return userInfo.name + ", position 72";
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: s=" + s);
            }
        });
    }

    /**
     * [RxJava] 在I/O线程发射HackerNews的故事, 但是将这些故事的标题传递给UI线程上的Observers。
     **/
    private void sendNameFromIOToMain4Position8() {
        demo81();
        demo82();
    }

    private void demo81() {
        Observable<UserInfo> observable = Observable.create(new Observable.OnSubscribe<UserInfo>() {
            @Override
            public void call(Subscriber<? super UserInfo> subscriber) {
                subscriber.onNext(getTopStory());
                subscriber.onCompleted();
            }
        });

        Observable<String> observable2 = observable.map(new Func1<UserInfo, String>() {
            @Override
            public String call(UserInfo userInfo) {
                return userInfo.name + ", position 81";
            }
        });

        Subscriber<String> subscriberAsReceiver = new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: s=" + s);
            }
        };
        observable2.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriberAsReceiver);
    }

    private void demo82() {
        Subscription observable = Observable.create(new Observable.OnSubscribe<UserInfo>() {
            @Override
            public void call(Subscriber<? super UserInfo> subscriber) {
                subscriber.onNext(getTopStory());
                subscriber.onCompleted();
            }
        }).map(new Func1<UserInfo, String>() {
            @Override
            public String call(UserInfo userInfo) {
                return userInfo.name + ", position 82";
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: s=" + s);
            }
        });
    }


    /**
     * RxJava解决重复查询 : 第一种方法，创建Observables并用Loader类取异步数据。
     * Building Observables on Top of Loadersit
     */
    private void useLoaderResolveRepeatQuery4Position9() {

    }

    /**
     * <pre>
     * Using cache or replay with Observables that Survive Configuration Changes
     * 用RxJava解决重复查询的问题
     * 第二种解决重复查询问题的方法是:
     * a) 确保Observables幸免于配置的改变
     * b) 使用cache或者replay操作符让这些Observables给未来的Observers发射相同的数据
     * </pre>
     */
    private void useCacheOrReplaceResolveRepeatQuery4Position10() {

    }

    @Override
    public String getDescTitle() {
        return "Common Demos for RxJava and RxAndroid";
    }

    @Override
    public void onItemClick(int position) {
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

            case 6:
                Schedulers4Position6();
                break;

            case 7:
                mapSendUserPosition7();
                break;

            case 8:
                sendNameFromIOToMain4Position8();
                break;


            default:
                break;
        }
    }

    @Override
    public void createDataSource4Lv(List<LvItemBean> mDataSource) {
        mDataSource.add(0, new LvItemBean("Send a string", "btn0"));
        mDataSource.add(1, new LvItemBean("Send a string. Observable.just is a simplification for Observable.create.", "btn1"));
        mDataSource.add(2, new LvItemBean("map的功能就是在observable和subscribe之间可以对数据进行操作", "btn2"));
        mDataSource.add(3, new LvItemBean("[RxJava] Observer as receiver.", "btn3"));
        mDataSource.add(4, new LvItemBean("[RxJava] How Observable to emit the story from API server..", "btn4"));
        mDataSource.add(5, new LvItemBean("[RaJava] Schedulers决定了Observables在哪个线程发射他们的异步数据流，也决定了Observers在哪个线程消费这些数据流。", "btn5"));
        mDataSource.add(6, new LvItemBean("[RxJava] The truth of position5 : 一个操作符是被应用于一个源Observable并且作为应用的结果它将返回一个新的Observable。", "btn6"));
        mDataSource.add(7, new LvItemBean("[RxJava] map操作符允许我们把一个发射故事的Observable变成一个发射这些故事标题的Observable。", "btn7"));
        mDataSource.add(8, new LvItemBean("[RxJava] 在I/O线程发射HackerNews的故事, 但是将这些故事的标题传递给UI线程上的Observers。", "btn8"));
    }

    @Override
    public String getFragmentTag() {
        return TAG;
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
            return "User{" +
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
