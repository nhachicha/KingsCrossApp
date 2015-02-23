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

package com.nabilhachicha.kc.io;

import com.nabilhachicha.kc.data.Database;
import com.nabilhachicha.kc.model.BackendResponse;
import com.nabilhachicha.kc.model.Category;
import com.nabilhachicha.kc.model.POI;
import com.nabilhachicha.kc.service.BackendService;

import java.util.Collections;
import java.util.List;

import retrofit.RestAdapter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Nabil Hachicha on 10/12/14.
 */
public class KcObservables {

    public static Observable<List<Category>> getPoisAndCategories(final RestAdapter restAdapter, final Database database) {
        BackendService backendService = restAdapter.create(BackendService.class);
        final Observable<BackendResponse> pois = backendService.getPois();

        Observable<List<Category>> obsCategories = pois
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(backendResponse -> database.deleteCategories())
                .flatMap(backendResponse -> Observable.from(backendResponse.getCategories()))
                .map(database::saveCategory)
                .toList();

        Observable<Integer> obsPois = pois
                .doOnNext(backendResponse -> database.deletePois())
                .flatMap(backendResponse -> Observable.from(backendResponse.getItems()))
                .map(database::savePoi)
                .count();

        return Observable.zip(obsCategories, obsPois, (categories, nbPois) -> {// Categories & POIs runs in parallel
            if (null != categories
                    && !categories.isEmpty()
                    && nbPois > 0) {
                Collections.sort(categories, Category.Sort.BY_POSITION.getComparator());

                return categories;

            } else {
                throw new IllegalStateException("Empty categories or POIs");//TODO use this to ignore updating UI if we get a cached response
            }
        });
    }

    public static Observable<List<List<POI>>> getItemsByCategory(final Database database, final String category) {
        return Observable
                .from(database.getPois(category, POI.Sort.BY_RATING))
                .subscribeOn(Schedulers.io())
                .buffer(2)
                .toList();
    }
}
