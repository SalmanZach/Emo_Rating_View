package com.salman.zach.emorater;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Zach on 5/27/2017.
 */

public class EmoRatingView extends View {

    private static final int NO_RATING = 0;
    private static final int MAX_RATE = 5;
    private static final int DEFAULT_RATE_HIGHT = 120;
    private static final int DEFAULT_RATE_WIDTH = 120;
    private boolean isSliding;
    private float slidePosition;
    private PointF[] points;
    private float itemWidth;
    private VectorDrawableCompat[] ratingSmiles;
    private VectorDrawableCompat[] defaultSmile;
    private IEmoRatingListener listener;
    private int currentRating = NO_RATING;
    private int smileWidth, smileHeight;
    private int horizontalSpacing;
    private boolean isEnabled;
    private int rating = NO_RATING;

    public EmoRatingView(Context context) {
        super(context);
        init();


    }

    public EmoRatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public EmoRatingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init() {
        init(null);
    }

    private void init(AttributeSet attrs) {
        isSliding = false;

        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.emoRate, 0, 0);
            try {
                smileWidth = ta.getDimensionPixelSize(R.styleable.emoRate_smileWidth, DEFAULT_RATE_WIDTH);
                smileHeight = ta.getDimensionPixelSize(R.styleable.emoRate_smileHeight, DEFAULT_RATE_HIGHT);
                horizontalSpacing = ta.getDimensionPixelSize(R.styleable.emoRate_horizontalSpace, 0);
                isEnabled = ta.getBoolean(R.styleable.emoRate_enabled, true);
                rating = ta.getInt(R.styleable.emoRate_rating, NO_RATING);
                int default1, default2, default3, default4, default5, res1, res2, res3, res4, res5;

                default1 = ta.getResourceId(R.styleable.emoRate_Default1, R.drawable.default_rate1);
                default2 = ta.getResourceId(R.styleable.emoRate_Default2, R.drawable.default_rate2);
                default3 = ta.getResourceId(R.styleable.emoRate_Default3, R.drawable.default_rate3);
                default4 = ta.getResourceId(R.styleable.emoRate_Default4, R.drawable.default_rate4);
                default5 = ta.getResourceId(R.styleable.emoRate_Default5, R.drawable.default_rate5);
                res1 = ta.getResourceId(R.styleable.emoRate_Rate1, R.drawable.rate1);
                res2 = ta.getResourceId(R.styleable.emoRate_Rate2, R.drawable.rate2);
                res3 = ta.getResourceId(R.styleable.emoRate_Rate3, R.drawable.rate3);
                res4 = ta.getResourceId(R.styleable.emoRate_Rate4, R.drawable.rate4);
                res5 = ta.getResourceId(R.styleable.emoRate_Rate5, R.drawable.rate5);

                defaultSmile = new VectorDrawableCompat[]{
                        VectorDrawableCompat.create(getResources(), default1, getContext().getTheme()),
                        VectorDrawableCompat.create(getResources(), default2, getContext().getTheme()),
                        VectorDrawableCompat.create(getResources(), default3, getContext().getTheme()),
                        VectorDrawableCompat.create(getResources(), default4, getContext().getTheme()),
                        VectorDrawableCompat.create(getResources(), default5, getContext().getTheme())

                };
                ratingSmiles = new VectorDrawableCompat[]{

                        VectorDrawableCompat.create(getResources(), res1, getContext().getTheme()),
                        VectorDrawableCompat.create(getResources(), res2, getContext().getTheme()),
                        VectorDrawableCompat.create(getResources(), res3, getContext().getTheme()),
                        VectorDrawableCompat.create(getResources(), res4, getContext().getTheme()),
                        VectorDrawableCompat.create(getResources(), res5, getContext().getTheme())

                };
                if (smileWidth == 0)
                    smileWidth = defaultSmile[default1].getIntrinsicWidth();

                if (smileHeight == 0)
                    smileHeight = defaultSmile[default1].getIntrinsicHeight();


            } finally {
                ta.recycle();
            }
        }

        points = new PointF[MAX_RATE];
        for (int i = 0; i < MAX_RATE; i++) {
            points[i] = new PointF();
        }
        if (rating != NO_RATING)
            setRating(rating);
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
        super.setEnabled(enabled);
    }

    public void setOnRatingSliderChangeListener(IEmoRatingListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            // Disable all input if the slider is disabled
            return false;
        }
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE: {
                isSliding = true;
                slidePosition = getRelativePosition(event.getX());
                rating = (int) Math.ceil(slidePosition);
                if (listener != null && rating != currentRating) {
                    currentRating = rating;
                    listener.onRatingPending(rating);
                }
                break;
            }
            case MotionEvent.ACTION_UP:
                currentRating = NO_RATING;
                if (listener != null)
                    listener.onRatingFinal((int) Math.ceil(slidePosition));
                rating = (int) Math.ceil(slidePosition);
                break;
            case MotionEvent.ACTION_CANCEL:
                currentRating = NO_RATING;
                if (listener != null)
                    listener.onRatingCancel();
                isSliding = false;
                break;
            default:
                break;
        }

        invalidate();
        return true;
    }

    private float getRelativePosition(float x) {
        float position = x / itemWidth;
        position = Math.max(position, 0);
        return Math.min(position, MAX_RATE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        itemWidth = w / (float) MAX_RATE;
        updatePositions();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = smileWidth * MAX_RATE + horizontalSpacing * (MAX_RATE - 1) +
                getPaddingLeft() + getPaddingRight();
        int height = smileHeight + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < MAX_RATE; i++) {
            PointF pos = points[i];
            canvas.save();
            canvas.translate(pos.x, pos.y);
            drawSmile(canvas, i);
            canvas.restore();
        }
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 0 || rating > MAX_RATE)
            throw new IndexOutOfBoundsException("Rating must be between 0 and " + MAX_RATE);

        this.rating = rating;
        slidePosition = (float) (rating - 0.1);
        isSliding = true;
        invalidate();
        if (listener != null)
            listener.onRatingFinal(rating);
    }

    private void drawSmile(Canvas canvas, int position) {

        drawSmile(canvas, defaultSmile[position]);

        // Draw the rated smile
        if (isSliding && position <= slidePosition) {
            VectorDrawableCompat[] smiles = ratingSmiles;

            int rating = (int) Math.ceil(slidePosition);
            int smileIndex = rating - 1;
            if (rating > 0)
                drawSmile(canvas, smiles[smileIndex]);
        }
    }

    private void drawSmile(Canvas canvas, VectorDrawableCompat smile) {
        canvas.save();
        canvas.translate(-smileWidth / 2, -smileHeight / 2);
        smile.setBounds(0, 0, smileWidth, smileHeight);
        smile.draw(canvas);
        canvas.restore();
    }

    private void updatePositions() {
        float left = 0;
        for (int i = 0; i < MAX_RATE; i++) {
            float posY = getHeight() / 2;
            float posX = left + smileWidth / 2;
            left += smileWidth;
            if (i > 0) {
                posX += horizontalSpacing;
                left += horizontalSpacing;
            } else {
                posX += getPaddingLeft();
                left += getPaddingLeft();
            }

            points[i].set(posX, posY);

        }
    }

}