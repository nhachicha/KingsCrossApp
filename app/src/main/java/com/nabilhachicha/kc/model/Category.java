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

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Nabil Hachicha on 29/11/14.
 */
public class Category implements Serializable {

    public enum Sort {
        BY_POSITION,
        BY_NAME;

        private Comparator<Category> comparator;

        private Sort () {
            switch (ordinal()) {
                case 0: {
                    comparator = (lhs, rhs) -> lhs.getPosition() - rhs.getPosition();
                    break;
                }
                case 1: {
                    comparator = (lhs, rhs) -> lhs.getName().compareTo(rhs.getName());
                    break;
                }
            }
        }

        public Comparator<Category> getComparator() {
            return comparator;
        }
    }
    /*
    "_id": 0,
    "name": "Our new building",
    "description": "The history of the SpitFire",
    "pos": 0,
    "img":
    */
    String id;
    String name;
    String description;
    int position;
    @SerializedName("image_url") String imgUrl;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPosition() {
        return position;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", position=" + position +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }

    public Category () {}
    public Category(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }
}
