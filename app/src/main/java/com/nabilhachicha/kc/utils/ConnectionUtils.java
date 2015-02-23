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


package com.nabilhachicha.kc.utils;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.http.HttpStatus;

import java.io.IOException;

/**
 * Created by Nabil Hachicha on 06/12/14.
 */
public class ConnectionUtils {
    private static final String CHECK_URL = "http://clients3.google.com/generate_204";
    private Request request = new Request.Builder().url(CHECK_URL).build();
    private OkHttpClient okHttpClient;

    public ConnectionUtils(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    /**
     * http://www.chromium.org/chromium-os/chromiumos-design-docs/network-portal-detection
     * DNS based detection techniques do not work at all hotspots. The one sure
     * way to check a walled garden is to see if a URL fetch on a known address
     * fetches the data we expect
     */
    public boolean isConnected() {
        try {
            Response response = okHttpClient.newCall(request).execute();
            return HttpStatus.SC_NO_CONTENT == response.code();
        } catch (IOException e) {
            //QLog.w("isConnected error", e);
            return false;
        }
    }
}
