/*
 * Copyright (C) 2015 The App Business.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nabilhachicha.kc.home;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Nabil Hachicha on 10/12/14.
 * Helper to orchestrate the dynamic of getting the content
 * handle use of cache, errors et update in the background thread
 *
 * //1- show SplashScreen by default (UI Thread)
 * //2- check cache (BG Thread)
 * //3- cache available, (hide splash, show cache & try to update in background) (UI Thread/BG Thread)
 * //3.1 show content & start update in background
 * //4- no cache, keep showing splash, (check internet & update)
 * //4.1 Internet available -> start updating -> show content
 * //4.1 Internet available -> start updating -> error -> show error screen
 * //4.1 Internet not available -> show error screen
 */
public class DataLoaderHelper<T> {
    public interface ContentFlow<T> {
        /**
         * Display network or database error
         */
        void showError();

        /**
         * Act like a progress bar or splash screen
         */
        void showContent(T data);

        /**
         * Update already shown content
         */
        void updateContent(T data);

        /**
         * do we have any cached data to show?
         */
        boolean isCacheAvailable();

        /**
         * Query the cache for content
         */
        T queryCache();

        /**
         * Network call to query for the content
         */
        Observable<T> queryBackend();
    }

    ContentFlow<T> flowListener;

    Subscription mSubscription;

    public DataLoaderHelper(ContentFlow<T> flowListener) {
        this.flowListener = flowListener;
    }

    public void onStart() {
        mSubscription =
                Observable.just(flowListener.isCacheAvailable())
                        .flatMap(cacheAvailable -> {
                            if (cacheAvailable) {// show cache + update in bg
                                return Observable.just(flowListener.queryCache()); // replace with abstract (could be POI)
                            } else {//
                                return Observable.error(new Exception("no cache"));
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError(throwable ->
                                        flowListener.queryBackend()
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(flowListener::showContent, throwable1 -> flowListener.showError())
                        )
                        .subscribe(data -> {
                            flowListener.showContent(data);// end the flow & notify UI
                            flowListener.queryBackend() // startCheckingInBackground
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(flowListener::updateContent);
                        });
    }

    public void onStop() {
        if (null != mSubscription) {
            mSubscription.unsubscribe();
        }
    }
}
