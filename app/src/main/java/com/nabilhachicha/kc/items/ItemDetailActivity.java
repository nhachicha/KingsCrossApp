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

package com.nabilhachicha.kc.items;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nabilhachicha.kc.BaseActivity;
import com.nabilhachicha.kc.R;
import com.nabilhachicha.kc.model.POI;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

/**
 * Created by Nabil on 11/12/14.
 */
public class ItemDetailActivity extends BaseActivity implements OnMapReadyCallback {
    @Inject
    Picasso mPicasso;

    ImageView mItemImg;
    TextView mTextName, mTextDescription, mTextCommentary;
    POI mPOI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mPOI = (POI) getIntent().getSerializableExtra("item");

        setContentView(R.layout.item_details);

        mItemImg = (ImageView) findViewById(R.id.itemImg);
        mTextName = (TextView) findViewById(R.id.textName);
        mTextDescription = (TextView) findViewById(R.id.textDescription);
        mTextCommentary = (TextView) findViewById(R.id.textCommentary);

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        mPicasso.load(mPOI.getImgUrl()).resize(screenWidth, screenWidth).centerInside().into(mItemImg);
        mTextName.setText(mPOI.getName());
        mTextDescription.setText(mPOI.getDescription());
        mTextCommentary.setText(mPOI.getCommentary());

        findViewById(R.id.map).getLayoutParams().height = screenWidth;

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        Fragment mMapFragment = getFragmentManager().findFragmentById(R.id.map);
//        mMapFragment.setW

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = mPOI.getLocation();
        googleMap.addMarker(new MarkerOptions()
                .position(location)
                .title(mPOI.getName()));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
    }
}
