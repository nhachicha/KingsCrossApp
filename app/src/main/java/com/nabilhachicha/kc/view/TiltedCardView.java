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
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.nabilhachicha.kc.R;
import com.nabilhachicha.kc.model.POI;
import com.nabilhachicha.kc.utils.ViewUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by Nabil Hachicha on 14/10/14.
 */
public class TiltedCardView extends View {
    private Path mPathTop;
    private Path mPathBottom;

    private Paint mCardTitlePaint;
    private TextPaint mCardBodyPaint;

    private Target mTargetTop = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Shader shaderTop = new BitmapShader(bitmap, Shader.TileMode.CLAMP,
                    Shader.TileMode.CLAMP);

            Shader gradient = new LinearGradient(0, bitmap.getHeight(), 0, 0, new int[]{
                    Color.BLACK, Color.TRANSPARENT},
                    null, Shader.TileMode.CLAMP);

            mPaintTop.setShader(
                    new ComposeShader(shaderTop, gradient, PorterDuff.Mode.SRC_OVER));

            invalidate();
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            // show place holder
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            // show place holder
        }
    };

    private Target mTargetBottom = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Shader shaderBottom = new BitmapShader(bitmap, Shader.TileMode.CLAMP,
                    Shader.TileMode.CLAMP);

            Shader gradient = new LinearGradient(0, bitmap.getHeight(), 0, 0, new int[]{
                    Color.BLACK, Color.TRANSPARENT},
                    null, Shader.TileMode.CLAMP);

            mPaintBottom.setShader(
                    new ComposeShader(shaderBottom, gradient, PorterDuff.Mode.SRC_OVER));

            invalidate();
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            // show place holder
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            // show place holder
        }
    };

    public static interface OnPoiClickedListener {
        public void onPoiClick(POI poi);
    }

    OnPoiClickedListener mListener;
    private GestureDetector mDetector;

    private POI mTopPoi;
    private POI mBottomPoi;
    private Paint mPaintSeparator;
    private Paint mPaintBottom;
    private Paint mPaintTop;
    private Region mRegionTop;

    private static int WIDTH;
    private static int HEIGHT;
    private final static int TILT_ANGLE = 7; //TODO integer degree (load from style)
    private static int SEPARATOR_WIDTH = 10;//TODO (in pixel) uses style with DP
    private static int SEPARATOR_COLOR;
    private static int CARD_DETAILS_WIDTH;
    private static int CARD_DETAILS_HEIGHT;
    private final static int CARD_DETAILS_MARGIN = 40;//TODO (in pixel) uses style with DP
    private static int SHIFT;
    private Rect mCardTopRect;
    private Rect mCardBottomRect;
    private StaticLayout mStaticLytTopBodyTxt;
    private StaticLayout mStaticLytBottomBodyTxt;
    private Rect mTitleRect;

    private int titleTextSize, descriptionTextSize;
    private int mColorTitle, mColorDescription;

    public TiltedCardView(Context context, OnPoiClickedListener listener) {
        super(context);

        mListener = listener;
        // Reading style
        TypedArray a = context.obtainStyledAttributes(null, R.styleable.TiltedView, 0, 0);
        SEPARATOR_WIDTH = (int) a.getDimension(R.styleable.TiltedView_tv_separatorSize, 10.0f);
        SEPARATOR_COLOR = a.getColor(R.styleable.TiltedView_tv_separatorColor, Color.WHITE);

        // textSize is already expressed in pixels
        titleTextSize = a.getDimensionPixelSize(R.styleable.TiltedView_tv_titleTextSize, 30);
        descriptionTextSize = a.getDimensionPixelSize(R.styleable.TiltedView_tv_descriptionTextSize, 40);

        mColorTitle = a.getColor(R.styleable.TiltedView_tv_titleColor, Color.WHITE);
        mColorDescription = a.getColor(R.styleable.TiltedView_tv_descriptionColor, Color.WHITE);
        //TODO read other properties (text size colors etc).

        a.recycle();
    }

    public TiltedCardView(Context context, POI top, POI bottom) {
        super(context);
        mTopPoi = top;
        mBottomPoi = bottom;

        // Reading style
        TypedArray a = context.obtainStyledAttributes(null, R.styleable.TiltedView, 0, 0);
        SEPARATOR_WIDTH = (int) a.getDimension(R.styleable.TiltedView_tv_separatorSize, 10.0f);
        SEPARATOR_COLOR = a.getColor(R.styleable.TiltedView_tv_separatorColor, Color.WHITE);

        // textSize is already expressed in pixels
        titleTextSize = a.getDimensionPixelSize(R.styleable.TiltedView_tv_titleTextSize, 30);
        descriptionTextSize = a.getDimensionPixelSize(R.styleable.TiltedView_tv_descriptionTextSize, 40);

        mColorTitle = a.getColor(R.styleable.TiltedView_tv_titleColor, Color.WHITE);
        mColorDescription = a.getColor(R.styleable.TiltedView_tv_descriptionColor, Color.WHITE);
        //TODO read other properties (text size colors etc).

        a.recycle();

        init();
    }

    public void bindTo(POI top, POI bottom, Picasso picasso) {
        mTopPoi = top;
        mBottomPoi = bottom;
        init();
        requestLayout();
    }

    public void setListener(OnPoiClickedListener listener) {
        mListener = listener;
    }

    private void init() {
        // Geometry & sizes
        WIDTH = getContext().getResources().getDisplayMetrics().widthPixels;//TODO maybe too big for tablet or landscape (use correct onMeasure)
        HEIGHT = WIDTH;

        CARD_DETAILS_WIDTH = WIDTH / 2;
        CARD_DETAILS_HEIGHT = (int) (WIDTH / 4 * 0.7);

        SHIFT = (int) (Math.tan(Math.toRadians(TILT_ANGLE)) * WIDTH);

        mCardTopRect = new Rect();
        mCardBottomRect = new Rect();

        // Placeholder image
        Bitmap placeholderBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.placeholder);
        Shader shaderPlaceholder = new BitmapShader(placeholderBitmap, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);

        // Various paints
        mPaintSeparator = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintSeparator.setColor(SEPARATOR_COLOR);
        mPaintSeparator.setStrokeWidth(SEPARATOR_WIDTH);
        mPaintSeparator.setStyle(Paint.Style.FILL);

        mPaintTop = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTop.setShader(shaderPlaceholder);

        mPaintBottom = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBottom.setShader(shaderPlaceholder);

        mCardTitlePaint = new Paint();
        mCardTitlePaint.setTypeface(ViewUtils.getTypeface(getContext(), "apercu_bold"));
        mCardTitlePaint.setColor(mColorTitle);
        mCardTitlePaint.setTextSize(titleTextSize);

        mCardBodyPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mCardTitlePaint.setTypeface(ViewUtils.getTypeface(getContext(), "apercu_regular"));
        mCardBodyPaint.setTextSize(descriptionTextSize);
        mCardBodyPaint.setColor(mColorDescription);

        //Detect a click on the top/bottom component
        mDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return super.onSingleTapUp(e);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                Point point = new Point();
                point.x = (int) event.getX();
                point.y = (int) event.getY();

                if (mRegionTop.contains(point.x, point.y)) {
                    mListener.onPoiClick(mTopPoi);
                } else {
                    mListener.onPoiClick(mBottomPoi);
                }

                return super.onSingleTapConfirmed(event);
            }
        });

        // Define the boundaries of the bottom part.
        mPathBottom = new Path();
        mPathBottom.moveTo(0, 0);
        mPathBottom.lineTo(WIDTH, 0);
        mPathBottom.lineTo(WIDTH, HEIGHT / 2);
        mPathBottom.lineTo(0, HEIGHT / 2);
        mPathBottom.close();

        // Define the boundaries of the top part.
        mPathTop = new Path();
        mPathTop.moveTo(0, 0);
        mPathTop.lineTo(WIDTH, 0);
        mPathTop.lineTo(WIDTH, HEIGHT / 2 - SHIFT);
        mPathTop.lineTo(0, HEIGHT / 2);
        mPathTop.close();

        // Define a region to help us detect whether the click is for the top or bottom part
        RectF rectF = new RectF();
        mPathTop.computeBounds(rectF, true);
        mRegionTop = new Region();
        mRegionTop.setPath(mPathTop, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));

        // misc
        mStaticLytTopBodyTxt = new StaticLayout(mTopPoi.getEllipsizedDescription(), mCardBodyPaint, CARD_DETAILS_MARGIN + CARD_DETAILS_WIDTH, Layout.Alignment.ALIGN_NORMAL, 1, 1, false);
        if (null != mBottomPoi) {
            mStaticLytBottomBodyTxt = new StaticLayout(mBottomPoi.getEllipsizedDescription(), mCardBodyPaint, CARD_DETAILS_WIDTH - CARD_DETAILS_MARGIN, Layout.Alignment.ALIGN_NORMAL, 1, 1, false);
        }
        mTitleRect = new Rect();

        // Load the images
        Picasso.with(getContext()).load(mTopPoi.getImgUrl()).resize(WIDTH, (WIDTH / 2) + SHIFT).centerCrop().into(mTargetTop);
        if (null != mBottomPoi) {
            Picasso.with(getContext()).load(mBottomPoi.getImgUrl()).resize(WIDTH, (WIDTH / 2) + SHIFT).centerCrop().into(mTargetBottom);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO write a decent onMeasure that takes into account orientation & tablet
        setMeasuredDimension(WIDTH, HEIGHT - SHIFT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //TODO add case where only top is available
        drawTopAndBottom(canvas);

        drawSeparator(canvas);

        drawCardDetailTop(canvas);
        if (null != mBottomPoi) drawCardDetailBottom(canvas);
    }

    private void drawTopAndBottom(final Canvas canvas) {
        canvas.save();
        canvas.translate(0, HEIGHT / 2 - SHIFT);
        canvas.drawPath(mPathBottom, mPaintBottom);
        canvas.restore();
        canvas.drawPath(mPathTop, mPaintTop);
    }

    private void drawSeparator(final Canvas canvas) {
        canvas.drawLine(0, HEIGHT / 2, WIDTH, HEIGHT / 2 - SHIFT, mPaintSeparator);
    }

    private void drawCardDetailTop(final Canvas canvas) {
        // Bounds of this card
        mCardTopRect.set(CARD_DETAILS_MARGIN,
                HEIGHT / 4 + (int) (SHIFT * 0.5),
                CARD_DETAILS_MARGIN + CARD_DETAILS_WIDTH,
                HEIGHT / 4 + (int) (SHIFT * 0.5) + CARD_DETAILS_HEIGHT);

        // Draw title text
        mCardTitlePaint.getTextBounds(mTopPoi.getName(), 0, mTopPoi.getName().length(), mTitleRect);
        canvas.drawText(mTopPoi.getName(), mCardTopRect.left, mCardTopRect.top, mCardTitlePaint);

        // Draw Body text
        canvas.save();
        canvas.translate(mCardTopRect.left, mCardTopRect.top + mTitleRect.height());
        mStaticLytTopBodyTxt.draw(canvas);
        canvas.restore();
    }

    private void drawCardDetailBottom(Canvas canvas) {
        // Bounds of this card
        mCardBottomRect.set(getMeasuredWidth() - CARD_DETAILS_WIDTH - CARD_DETAILS_MARGIN,
                (int) (HEIGHT * 0.75) - (int) (SHIFT * 0.5),
                getMeasuredWidth() - CARD_DETAILS_MARGIN,
                getMeasuredHeight() - CARD_DETAILS_MARGIN);

        // Draw title text
        mCardTitlePaint.getTextBounds(mBottomPoi.getName(), 0, mBottomPoi.getName().length(), mTitleRect);
        canvas.drawText(mBottomPoi.getName(), mCardBottomRect.left, mCardBottomRect.top, mCardTitlePaint);

        // Draw Body text
        canvas.save();
        canvas.translate(mCardBottomRect.left, mCardBottomRect.top + mTitleRect.height());
        mStaticLytBottomBodyTxt.draw(canvas);
        canvas.restore();
    }
}
