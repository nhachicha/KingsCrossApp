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


package com.nabilhachicha.kc.di;

import com.nabilhachicha.kc.BuildConfig;
import com.nabilhachicha.kc.service.BackendService;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;

/**
 * Created by Nabil Hachicha on 06/12/14.
 */
@Module(
    complete = false,
    library = true
)
public final class ApiModule {
    public static final String PRODUCTION_API_URL = BuildConfig.BACKEND_ENDPOINT;

    @Provides
    @Singleton
    Endpoint provideEndpoint() {
        return Endpoints.newFixedEndpoint(PRODUCTION_API_URL);
    }

    @Provides @Singleton
    Client provideClient(OkHttpClient client) {
        return new OkClient(client);
    }

    @Provides @Singleton
    RestAdapter provideRestAdapter(Endpoint endpoint, Client client) {
        return new RestAdapter.Builder() //
                .setClient(client) //
                .setEndpoint(endpoint) //
                .setLogLevel(RestAdapter.LogLevel.BASIC)//TODO remove in production
                .build();
    }

    @Provides @Singleton
    BackendService provideGalleryService(RestAdapter restAdapter) {
        return restAdapter.create(BackendService.class);
    }

}
