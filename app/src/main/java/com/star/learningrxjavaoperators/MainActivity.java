package com.star.learningrxjavaoperators;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "RxJava";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable
                .create((ObservableOnSubscribe<Integer>) emitter -> {
                    emitter.onNext(1);
                    emitter.onNext(2);
                    emitter.onNext(3);
                })
                .map(integer ->
                        "使用 Map变换操作符 将事件" + integer +
                                "的参数从 整型" + integer +
                                " 变换成 字符串类型" + integer)
                .subscribe(s -> Log.d(TAG, s));

        Observable
                .create((ObservableOnSubscribe<Integer>) emitter -> {
                    emitter.onNext(1);
                    emitter.onNext(2);
                    emitter.onNext(3);
                })
                .flatMap((Function<Integer, ObservableSource<String>>) integer -> {

                    final List<String> list = new ArrayList<>();

                    for (int i = 0; i < 3; i++) {
                        list.add("我是事件" + integer + "拆分后的子事件" + i);
                    }

                    return Observable.fromIterable(list);
                })
                .subscribe(s -> Log.d(TAG, s));

        Observable
                .create((ObservableOnSubscribe<Integer>) emitter -> {
                    emitter.onNext(1);
                    emitter.onNext(2);
                    emitter.onNext(3);
                })
                .concatMap((Function<Integer, ObservableSource<String>>) integer -> {

                    final List<String> list = new ArrayList<>();

                    for (int i = 0; i < 3; i++) {
                        list.add("我是事件" + integer + "拆分后的子事件" + i);
                    }

                    return Observable.fromIterable(list);
                })
                .subscribe(s -> Log.d(TAG, s));

        Observable
                .just(1, 2, 3, 4, 5)
                .buffer(3, 1)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        Log.d(TAG, "缓存区里的事件数量 = " + integers.size());

                        for (Integer value : integers) {
                            Log.d(TAG, "事件 = " + value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }
}
