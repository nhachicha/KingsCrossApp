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

import android.app.Application;
import android.content.SharedPreferences;

import com.nabilhachicha.kc.data.Database;
import com.nabilhachicha.kc.data.DatabaseImpl;
import com.nabilhachicha.kc.utils.ConnectionUtils;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Nabil Hachicha on 06/12/14.
 */
@Module(
        includes = ApiModule.class,
        complete = false,
        library = true
)
public final class DataModule {
    static final int DISK_CACHE_SIZE = 10 * 1024 * 1024; // 10MB
    static final int SOCKET_TIMEOUT = 10;

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Application app) {
        return app.getSharedPreferences("kc", MODE_PRIVATE);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Application app) {
        return createOkHttpClient(app);
    }

    @Provides
    @Singleton
    Picasso providePicasso(Application app, OkHttpClient client) {
        return new Picasso.Builder(app)
                .downloader(new OkHttpDownloader(client))
                .build();
    }

    static OkHttpClient createOkHttpClient(Application app) {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS);
        client.setReadTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS);

        // Install an HTTP cache in the application cache directory.
        try {
            File cacheDir = new File(app.getCacheDir(), "http");
            Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
            client.setCache(cache);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return client;
    }

    @Provides
    @Singleton
    Database provideDatabase (Application application) {
        return new DatabaseImpl(application);
    }

    @Provides @Singleton
    ConnectionUtils provideConnectionUtils (OkHttpClient client) {
        return new ConnectionUtils(client);
    }

}