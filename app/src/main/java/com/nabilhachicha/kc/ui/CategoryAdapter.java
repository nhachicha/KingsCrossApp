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

package com.nabilhachicha.kc.ui;

/**
 * Created by Nabil Hachicha on 10/12/14.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nabilhachicha.kc.R;
import com.nabilhachicha.kc.model.Category;
import com.nabilhachicha.kc.view.GalleryItemView;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;


public class CategoryAdapter extends BaseAdapter {

    private final LayoutInflater mLayoutInflater;
    private List<Category> categories = Collections.emptyList();
    private final Picasso mPicasso;

    public CategoryAdapter(final Context context, final Picasso picasso) {
        mLayoutInflater = LayoutInflater.from(context);
        mPicasso = picasso;
    }

    public void replace (List<Category> newCategories) {
        if (null != newCategories && !newCategories.isEmpty()) {
            categories = newCategories;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Category getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.gallery_view_image, parent, false);
            convertView.setTag(convertView);

        } else {
            convertView = (GalleryItemView) convertView.getTag();
        }

        final Category category = getItem(position);

        ((GalleryItemView) convertView).bindTo(category, mPicasso);

        return convertView;
    }

}
