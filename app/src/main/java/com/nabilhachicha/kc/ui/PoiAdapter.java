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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nabilhachicha.kc.model.POI;
import com.nabilhachicha.kc.view.TiltedCardView;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by Nabil Hachicha on 04/12/14.
 */
public class PoiAdapter extends BindableAdapter<List<POI>> {
    private List<List<POI>> feed  = Collections.emptyList();
    private final Picasso picasso;
    private TiltedCardView.OnPoiClickedListener listener;

    public PoiAdapter(Context context, Picasso picasso, TiltedCardView.OnPoiClickedListener listener) {
        super(context);
        this.picasso = picasso;
        this.listener = listener;
    }

    public void replaceWith(List<List<POI>> feed) {
        this.feed = feed;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return feed.size();
    }

    @Override
    public List<POI> getItem(int position) {
        return feed.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container) {
        return new TiltedCardView(getContext(), listener);
    }

    @Override
    public void bindView(List<POI> item, int position, View view) {
        ((TiltedCardView)view).bindTo(item.get(0), item.size()>1?item.get(1):null, picasso);
    }
}
