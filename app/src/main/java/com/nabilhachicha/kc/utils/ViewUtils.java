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

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Credit Yavor Ivanov.
 */
public class ViewUtils {

    private ViewUtils() {
    }


    /**
     * Set a new typeface by specifying it by a font name.
     *
     * @param view     The text view to have a new typeface.
     * @param fontName The name of the new font.
     */
    public static void setTypeface(TextView view, String fontName) {
        if (view.isInEditMode()) return;
        String fontPath = AssetUtils.getFontPath(fontName);
        view.setTypeface(TypefaceUtils.load(view.getResources().getAssets(), fontPath));
    }

    public static Typeface getTypeface (Context context, String fontname) {
        String fontPath = AssetUtils.getFontPath(fontname);
        return TypefaceUtils.load(context.getResources().getAssets(), fontPath);
    }
}