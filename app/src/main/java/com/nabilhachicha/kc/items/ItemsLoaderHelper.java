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

package com.nabilhachicha.kc.items;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Nabil Hachicha on 10/12/14.
 * Helper to load items from the database
 */
public class ItemsLoaderHelper<T> {
    public interface ContentFlow<T> {// Contract
        /**
         * Display network or database error
         */
        void showError();

        /**
         * Act like a progress bar or splash screen
         */
        void showContent(T data);

        /**
         * Network call to query for the content
         */
        Observable<T> queryDatabase();
    }

    ContentFlow<T> flowListener;

    Subscription mSubscription;

    public ItemsLoaderHelper(ContentFlow<T> flowListener) {
        this.flowListener = flowListener;
    }

    public void onStart() {
        mSubscription = flowListener.queryDatabase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> flowListener.showError())
                .subscribe(flowListener::showContent);
    }

    public void onStop() {
        if (null != mSubscription) {
            mSubscription.unsubscribe();
        }
    }
}
