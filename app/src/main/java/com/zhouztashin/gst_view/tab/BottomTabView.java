package com.zhouztashin.gst_view.tab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhouztashin on 2018/3/26.
 * 底部导航栏
 */

public class BottomTabView extends View {
    private Paint mBitmapPaint;
    private TextPaint mTextPaint;
    private TabItem mTabItem;
    boolean isCheck;
    float mSpace;
    int mTextColor;
    int mTextCheckColor;
    public BottomTabView(Context context) {
        super(context);
        init();
    }

    public BottomTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public BottomTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void setTextSize(float textSize) {
        mTextPaint.setTextSize(textSize);
    }

    private void init() {
        mBitmapPaint = new Paint();
        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(15);
    }

    public void setTabItem(TabItem tabItem) {
        mTabItem = tabItem;
    }


    public TabItem getTabItem() {
        return mTabItem;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mTabItem == null || mTabItem.getIcon() <= 0) return;
        //画图标
        BitmapDrawable drawable;
        int textColor = 0;
        if (!isCheck) {
            textColor = mTextColor;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable = (BitmapDrawable) getContext().getDrawable(mTabItem.getIcon());
            } else {
                drawable = (BitmapDrawable) getContext().getResources().getDrawable(mTabItem.getIcon());
            }
        } else {
            textColor = mTextCheckColor;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable = (BitmapDrawable) getContext().getDrawable(mTabItem.getCheckIcon());
            } else {
                drawable = (BitmapDrawable) getContext().getResources().getDrawable(mTabItem.getCheckIcon());
            }
        }
        //画文字
        final String text = mTabItem.getText();
        Rect rect = new Rect();
        mTextPaint.setColor(textColor);
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        final int textX = getMeasuredWidth() / 2 - rect.width() / 2;

        final int iconY = (int) (getMeasuredHeight() / 2 - (drawable.getBitmap().getHeight() / 2 + rect.height() / 2 + mSpace / 2));
        final int iconX = getMeasuredWidth() / 2 - (drawable.getBitmap().getWidth() / 2);
        canvas.drawBitmap(drawable.getBitmap(), iconX, iconY, mBitmapPaint);
        canvas.drawText(text, textX, iconY + rect.height() + mSpace + drawable.getBitmap().getHeight(), mTextPaint);
    }
}
