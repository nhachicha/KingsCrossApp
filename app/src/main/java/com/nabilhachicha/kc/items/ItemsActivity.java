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

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.nabilhachicha.kc.BaseActivity;
import com.nabilhachicha.kc.io.KcObservables;
import com.nabilhachicha.kc.R;
import com.nabilhachicha.kc.data.Database;
import com.nabilhachicha.kc.model.Category;
import com.nabilhachicha.kc.model.POI;
import com.nabilhachicha.kc.ui.PoiAdapter;
import com.nabilhachicha.kc.view.TiltedCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Nabil Hachicha on 11/12/14.
 */
public class ItemsActivity extends BaseActivity implements TiltedCardView.OnPoiClickedListener, ItemsLoaderHelper.ContentFlow<List<List<POI>>> {
    @Inject
    Database mDatabase;

    @Inject
    Picasso mPicasso;

    ListView mListView;
    PoiAdapter mAdapter;

    Category mCategory;

    ItemsLoaderHelper<List<List<POI>>> mRxFlowHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mCategory = (Category) getIntent().getSerializableExtra("category");

        setContentView(R.layout.items);

        mListView = (ListView) findViewById(android.R.id.list);
        mAdapter = new PoiAdapter(this, mPicasso, this);
        mListView.setAdapter(mAdapter);

        mRxFlowHelper = new ItemsLoaderHelper<>(this);
    }

    @Override
    public void onPoiClick(POI poi) {
        if (null != poi) {
            Intent intent = new Intent(this, ItemDetailActivity.class);
            intent.putExtra("item", poi);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRxFlowHelper.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRxFlowHelper.onStop();
    }

    // Rx flow
    @Override
    public void showError() {
//        Toast.makeText(this, "ERROR" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showContent(List<List<POI>> data) {
        mAdapter.replaceWith(data);
    }

    @Override
    public Observable<List<List<POI>>> queryDatabase() {
        return KcObservables.getItemsByCategory(mDatabase, mCategory.getId());
    }

}
