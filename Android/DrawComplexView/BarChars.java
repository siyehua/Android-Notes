package com.caiyu.qqsd.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.caiyu.qqsd.R;
import com.caiyu.qqsd.common.SiAppLication;

import java.util.ArrayList;

/**
 * bar chars:use to the prediction fragment
 * Created by siyehua on 2016/12/9.
 */
public class BarChars extends View {
    public BarChars(Context context) {
        super(context);
        init();
    }

    public BarChars(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BarChars(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BarChars(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * item width
     */
    private int itemWidth;
    /**
     * size
     */
    private int itemSize;
    /**
     * start X position of draw
     */
    private float startX;

    /**
     * rect padding to the top
     */
    private float rectPadding;
    /**
     * rect width
     */
    private float rectWidth;
    /**
     * rect height
     */
    private float rectHeight;
    /**
     * top text size
     */
    private float topTextSize;
    /**
     * bottom text size
     */
    private float bottomTextSize;
    /**
     * top padding
     */
    private float topPadding;
    /**
     * bottom padding
     */
    private float bottomPadding;


    /**
     * top text color
     */
    private int topTextColor;
    /**
     * unHit rect color
     */
    private int unHitRectColr;
    /**
     * hit rect color
     */
    private int hitRectColr;
    /**
     * bottom text color
     */
    private int bottomTextColor;


    /**
     * top title list
     */
    private ArrayList<String> topList;

    /**
     * left data list
     */
    private ArrayList<Integer> leftList;
    /**
     * right data list
     */
    private ArrayList<Integer> rightList;

    private Paint mPaint = new Paint();


    private void init() {
        startX = getResources().getDimension(R.dimen.n30px);
        itemWidth = (int) ((SiAppLication.mScreenWidth - startX * 2) / 5);

        rectPadding = getResources().getDimension(R.dimen.n80px);
        rectWidth = getResources().getDimension(R.dimen.n44px);
        rectHeight = getResources().getDimension(R.dimen.n110px);

        topPadding = getResources().getDimension(R.dimen.n38px);
        topTextSize = getResources().getDimension(R.dimen.n24px);

        bottomPadding = getResources().getDimension(R.dimen.n214px);
        bottomTextSize = getResources().getDimension(R.dimen.n22px);

        topTextColor = getResources().getColor(R.color._999999);
        unHitRectColr = getResources().getColor(R.color._F8D8D8);
        hitRectColr = getResources().getColor(R.color._DD4A4A);
        bottomTextColor = getResources().getColor(R.color._464646);
    }


    public void setData(@NonNull ArrayList<String> topList, @NonNull ArrayList<Integer> leftList,
                        @NonNull ArrayList<Integer> rightList) {
        if (topList.size() != leftList.size() || topList.size() != rightList.size())
            throw new RuntimeException("view request topList.size() = leftList.size() = " +
                    "rightList" + ".size()");

        this.topList = topList;
        this.leftList = leftList;
        this.rightList = rightList;
        itemSize = topList.size() > 5 ? 5 : topList.size();
        //draw
        postInvalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < itemSize; i++) {
            //draw title text
            mPaint.setTextSize(topTextSize);
            float topTextWidth = mPaint.measureText(topList.get(i));
            float x = startX + (itemWidth - topTextWidth) / 2f + itemWidth * i;
            float y = topPadding - mPaint.getFontMetrics().top - (mPaint.getFontMetrics().descent
                    - mPaint.getFontMetrics().ascent - topTextSize);
            mPaint.setColor(topTextColor);
            canvas.drawText(topList.get(i), x, y, mPaint);

            //draw rect
            float percent = 1 - (float) rightList.get(i) / (float) leftList.get(i);
            int left = (int) (startX + (itemWidth - rectWidth) / 2f + itemWidth * i);
            int top = (int) rectPadding;
            int right = left + (int) rectWidth;
            int bottom = (int) (top + percent * rectHeight);
            //draw un hit rect
            mPaint.setColor(unHitRectColr);
            canvas.drawRect(left, top, right, bottom, mPaint);
            //draw hit rect
            mPaint.setColor(hitRectColr);
            canvas.drawRect(left, bottom, right, bottom + (int) ((1 - percent) * rectHeight),
                    mPaint);

            //draw bottom text
            mPaint.setTextSize(bottomTextSize);
            mPaint.setColor(bottomTextColor);
            String bottomContent = leftList.get(i) + "中" + rightList.get(i);
            float bottomTextWidth = mPaint.measureText(bottomContent);
            float bottomX = startX + (itemWidth - bottomTextWidth) / 2f + itemWidth * i;
            float bottomY = bottomPadding - mPaint.getFontMetrics().top - (mPaint.getFontMetrics
                    ().descent - mPaint.getFontMetrics().ascent - topTextSize);
            canvas.drawText(bottomContent, bottomX, bottomY, mPaint);

        }
    }
}
