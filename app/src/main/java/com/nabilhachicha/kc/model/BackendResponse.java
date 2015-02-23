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

package com.nabilhachicha.kc.model;

import java.util.List;

/**
 * Created by Nabil Hachicha on 29/11/14.
 */
public class BackendResponse {
    List<Category> categories;
    List<POI> items;

    public List<Category> getCategories() {
        return categories;
    }

    public List<POI> getItems() {
        return items;
    }
}
