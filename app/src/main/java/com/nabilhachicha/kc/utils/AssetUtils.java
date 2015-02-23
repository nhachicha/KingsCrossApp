/*
 * Copyright (C) The App Business.
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

import java.util.NoSuchElementException;

/**
 * Credit to Yavor Ivanov.
 */
public final class AssetUtils {

    public enum Asset {
        APERCU_REGULAR("apercu_regular", "fonts/apercu_regular.otf"),
        APERCU_BOLD("apercu_bold", "fonts/apercu_bold.otf");

        private String name;
        private String path;

        Asset(String name, String path) {
            this.name = name;
            this.path = path;
        }

        public String getName() {
            return name;
        }

    }

    /**
     * Get the file path of an asset.
     *
     * @param asset An instance of {@link com.nabilhachicha.kc.utils.AssetUtils.Asset}.
     * @return File path, e.g. fonts/TimesClassicText-BoldItalic.ttf.
     */
    private static String getFile(Asset asset) {
        return asset.path;
    }

    public static String getFontPath(String fontName) {
        if (Asset.APERCU_BOLD.name.equals(fontName))
            return getFile(Asset.APERCU_BOLD);
        else if (Asset.APERCU_REGULAR.name.equals(fontName)) {
            return getFile(Asset.APERCU_REGULAR);
        } else {
            throw new NoSuchElementException("fontName: " + fontName + " not supported");
        }
    }

}