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

package com.nabilhachicha.kc.data;

import com.nabilhachicha.kc.model.Category;
import com.nabilhachicha.kc.model.POI;
import java.util.List;

/**
 * Created by Nabil Hachicha on 05/12/14.
 */
public interface Database {

    public POI savePoi(final POI poi);

    /**
     * Return all Point Of Interests for the given {@code category}
     *
     * @param category POI's category
     * @param sort     optional sort method (default is  {@code POI.Sort.BY_NAME})
     * @return List of POIs belonging to the  {@code category}
     */
    public List<POI> getPois(final String category, POI.Sort... sort);

    public void deletePois();

    public List<Category> getCategories(Category.Sort... sort);

    public Category saveCategory(final Category category);

    public void deleteCategories();

    public boolean isEmpty();

}
