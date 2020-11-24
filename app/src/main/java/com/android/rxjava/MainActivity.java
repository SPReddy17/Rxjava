package com.android.rxjava;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView text;
    private CompositeDisposable disposables = new CompositeDisposable();

    //disposables
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // disposables
//        Observable<Task> taskObservable = Observable
//                .fromIterable(DataSource.createTasksList())
//                .subscribeOn(Schedulers.io())
//                .filter(new Predicate<Task>() {
//                    @Override
//                    public boolean test(Task task) throws Exception {
//                        Log.d(TAG, "test: called" + Thread.currentThread().getName());
//
//                        return task.isComplete();
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread());
//
//        taskObservable.subscribe(new Observer<Task>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d)    {
//
//                disposables.add(d);
//                Log.d(TAG, "onSubscribe:  called");
//
//            }
//
//            @Override
//            public void onNext(@NonNull Task task) {
//
//                Log.d(TAG, "onNext:  called" + Thread.currentThread().getName());
//                Log.d(TAG, "onNext: ..." + task.getDescription());
//
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                Log.e(TAG, "onError: ", e );
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "onComplete: called....");
//            }
//        });
//
//        disposables.add(taskObservable.subscribe(new Consumer<Task>() {
//            @Override
//            public void accept(Task task) throws Throwable {
//
//            }
//        }));



        // create operator
        final Task task = new Task("Walk the dog", false,3);
        Observable<Task> taskObservable = Observable
                .create(new ObservableOnSubscribe<Task>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Task> emitter) throws Throwable {
                        if (!emitter.isDisposed()) {
                            emitter.onNext(task);
                            emitter.onComplete();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.d(TAG, "onNext: "+task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        disposables.clear();
//
//    }
}