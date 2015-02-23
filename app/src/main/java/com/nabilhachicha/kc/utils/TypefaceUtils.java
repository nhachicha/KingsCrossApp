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

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v4.util.LruCache;

/**
 * A helper loading {@link android.graphics.Typeface} avoiding the leak of the font when loaded
 * by multiple calls to {@link android.graphics.Typeface#createFromAsset(android.content.res.AssetManager, String)}
 * on pre-ICS versions.
 * <p/>
 * More details can be found here https://code.google.com/p/android/issues/detail?id=9904
 * <p/>
 * Credit: Yavor Ivanov .
 */
public final class TypefaceUtils {
    private final static LruCache<String, Typeface> sCachedFonts = new LruCache<>(2);

    /**
     * A helper loading a custom font.
     *
     * @param assetManager App's asset manager.
     * @param filePath     The path of the file.
     * @return Return {@link android.graphics.Typeface} or null if the path is invalid.
     */
    public static Typeface load(AssetManager assetManager, String filePath) {
        synchronized (sCachedFonts) {
            try {
                if (sCachedFonts.get(filePath) == null) {
                    Typeface typeface = Typeface.createFromAsset(assetManager, filePath);
                    sCachedFonts.put(filePath, typeface);
                    return typeface;
                }
            } catch (Exception e) {
                return null;
            }
            return sCachedFonts.get(filePath);
        }
    }

    private TypefaceUtils() {
    }
}