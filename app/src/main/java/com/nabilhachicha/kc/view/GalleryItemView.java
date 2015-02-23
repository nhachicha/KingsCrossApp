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

package com.nabilhachicha.kc.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nabilhachicha.kc.R;
import com.nabilhachicha.kc.model.Category;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

/**
 * Credit Jake Wharton (with modification
 */
public class GalleryItemView extends FrameLayout {
    ImageView image;
    TextView title;

    private RequestCreator request;

    public GalleryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        image = (ImageView) findViewById(R.id.gallery_image_image);
        title = (TextView) findViewById(R.id.gallery_image_title);
    }

    public void bindTo(Category category, Picasso picasso) {
        request = picasso.load(category.getImgUrl());
        requestLayout();
        title.setText(category.getName());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException("layout_width must be match_parent");
        }

        int width = MeasureSpec.getSize(widthMeasureSpec);
        // Honor aspect ratio for height but no larger than 2x width.
        float aspectRatio = 1;
        int height = Math.min((int) (width / aspectRatio), width * 2);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (request != null) {
            request.resize(width, height).centerCrop().into(image);
            request = null;
        }
    }
}
