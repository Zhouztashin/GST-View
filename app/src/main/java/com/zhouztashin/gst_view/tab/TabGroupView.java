package com.zhouztashin.gst_view.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.zhouztashin.gst_view.L;
import com.zhouztashin.gst_view.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouztashin on 2018/3/26.
 * 标签栏容器控件
 */

public class TabGroupView extends ViewGroup {

    private int mCurrentSelected = -1;
    private int mLastSelected = -1;
    private TabCheckListener l;
    private final List<TabItem> mTabList = new ArrayList<>();
    private final SparseArray<Pair<Integer, Integer>> mTabLoc = new SparseArray<>();
    private float mSpace;
    private float mTextSize;
    private int mTextColor;
    private int mTextCheckColor;

    public TabGroupView(@NonNull Context context) {
        this(context, null);
    }

    public TabGroupView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabGroupView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.TabGroupView);
        if (typedArray != null) {
            mSpace = typedArray.getDimension(R.styleable.TabGroupView_space, 0);
            mTextSize = typedArray.getDimension(R.styleable.TabGroupView_tabTextSize, 0);
            mTextColor = typedArray.getColor(R.styleable.TabGroupView_textColor, 0);
            mTextCheckColor = typedArray.getColor(R.styleable.TabGroupView_textCheckedColor, 0);

        }
    }


    public void setTabCheckListener(TabCheckListener l) {
        this.l = l;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        return super.drawChild(canvas, child, drawingTime);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mTabLoc.clear();
        final int childCount = getChildCount();
        int left = 0;
        int width = (r - l) / childCount;
        int right = width;
        int top = 0;
        int bottom = b - t;
        for (int i = 0; i < childCount; i++) {
            mTabLoc.put(i, new Pair<Integer, Integer>(left, right));
            getChildAt(i).layout(left, top, right, bottom);
            left = left + width;
            right = right + width;
        }
    }

    public void addTabItem(TabItem item) {
        mTabList.add(item);
        BottomTabView bottomTabView = new BottomTabView(getContext());
        bottomTabView.setTabItem(item);
        bottomTabView.mSpace = mSpace;
        bottomTabView.mTextColor = mTextColor;
        bottomTabView.mTextCheckColor = mTextCheckColor;
        bottomTabView.setTextSize(mTextSize);
        addView(bottomTabView);
    }

    /**
     * 添加/删除/隐藏等操作
     *
     * @param index
     */
    public void setDefaultTab(int index) {
        if (index == mLastSelected) {
            return;
        }
        mLastSelected = index;
        if (index > getChildCount() - 1) {
            return;
        }
        if (mLastSelected > getChildCount() - 1) {
            return;
        }
        ((BottomTabView) getChildAt(mLastSelected)).isCheck = false;
        ((BottomTabView) getChildAt(mLastSelected)).invalidate();
        ((BottomTabView) getChildAt(index)).isCheck = true;
        ((BottomTabView) getChildAt(index)).invalidate();
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof TabGroupLayoutParams;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new TabGroupLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new TabGroupLayoutParams(p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        L.log("onTouch");
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mCurrentSelected = judgeWhichTab(event.getX());
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            //是否还在当前Tab
            if (event.getY() >= 0 && event.getY() <= getMeasuredHeight()) {
                if (mCurrentSelected >= 0 && judgeWhichTab(event.getX()) == mCurrentSelected) {
                    //选中了某个Tab
                    ((BottomTabView) getChildAt(mLastSelected)).isCheck = false;
                    ((BottomTabView) getChildAt(mLastSelected)).invalidate();
                    ((BottomTabView) getChildAt(mCurrentSelected)).isCheck = true;
                    ((BottomTabView) getChildAt(mCurrentSelected)).invalidate();
                    mLastSelected = mCurrentSelected;
                    if (l != null) {
                        l.onCheck(mCurrentSelected, (BottomTabView) getChildAt(mCurrentSelected));
                    }

                }
            }
        }
        return super.onTouchEvent(event);

    }

    public int judgeWhichTab(float x) {
        for (int i = 0; i < mTabLoc.size(); i++) {
            if (mTabLoc.get(i).first <= x && mTabLoc.get(i).second >= x) {
                return i;
            }
        }
        return -1;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //将大小平均分给不同View

        final int childCount = getChildCount();
        int newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec) / childCount,
                MeasureSpec.getMode(widthMeasureSpec));
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            measureChildWithMargins(child, newWidthMeasureSpec, 0, heightMeasureSpec, 0);
        }
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));

    }

    public class TabGroupLayoutParams extends MarginLayoutParams {

        public TabGroupLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public TabGroupLayoutParams(int width, int height) {
            super(width, height);
        }

        public TabGroupLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public TabGroupLayoutParams(LayoutParams source) {
            super(source);
        }
    }


}
