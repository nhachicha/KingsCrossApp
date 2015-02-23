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

/*
    "_id": 1,
    "name": "Place name 1",
    "description": "Place description based on our findings",
    "review": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
    "rating": 1,
    "location": {
        "lat": 51.235685,
        "long": -1.309197
        },
    "img": "http://c2.staticflickr.com/8/7376/12493508345_cc799141cc_n.jpg",
    "tag": 1
 */

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Point Of Interest (can be a restaurant, coffee shop, etc.)
 * Created by Nabil Hachicha on 14/10/14.
 */
public class POI implements Serializable {
    private static final int MAX_DESCRIPTION_LENGTH = 54;// TODO replace with resource

    public void setCategory(String category) {
        this.category = category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getCommentary() {
        return commentary;
    }

    public LatLng getLocation() {
        return new LatLng(location.lat, location.lng);
    }

    public enum Sort {
        BY_RATING,
        BY_NAME;

        private Comparator<POI> comparator;

        private Sort () {
            switch (ordinal()) {
//                case 0: {
//                    comparator = (lhs, rhs) -> rhs.getRating().ordinal() - lhs.getRating().ordinal();
//                    break;
//                }
                default: {
                    comparator = (lhs, rhs) -> lhs.getName().compareTo(rhs.getName());
                    break;
                }
            }
        }

        public Comparator<POI> getComparator() {
            return comparator;
        }
    }

    public enum Rating {
        @SerializedName("1") ONE_STAR(0),
        @SerializedName("2") TWO_STAR(1),
        @SerializedName("3") THREE_STAR(2);

        public int starLevel;//FIXME is public the right pattern?

        Rating(int i) {
            starLevel = i;
        }
    }

    private String id;
    private String name;
    private String description;
    private String commentary;
    @SerializedName("image_url") private String imgUrl;
    private Rating rating = Rating.THREE_STAR;
    @SerializedName("category_id") private String category;

    public void setLocation(Location location) {
        this.location = location;
    }

    private @SerializedName("location")  Location location;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getEllipsizedDescription () {//FIXME refactor this to use native ellipsized
        if (description.length() < MAX_DESCRIPTION_LENGTH) {
            return description;
        } else {
             return  description.substring(0, MAX_DESCRIPTION_LENGTH) + "...";
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
    public String getCategory() {
        return category;
    }
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "POI{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                '}';
    }


}
