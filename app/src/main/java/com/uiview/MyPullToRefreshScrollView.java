package com.uiview;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.handmark.pulltorefresh.library.OverscrollHelper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.puddingvideoproject.R;

/**
 * Created by probuing on 2015/5/3.
 * 自定义的pulltorefreshscrollview
 */
public class MyPullToRefreshScrollView extends PullToRefreshBase<ScrollView> {

        public MyPullToRefreshScrollView(Context context) {
            super(context);
        }

        public MyPullToRefreshScrollView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MyPullToRefreshScrollView(Context context, Mode mode) {
            super(context, mode);
        }

        public MyPullToRefreshScrollView(Context context, Mode mode,
                                             AnimationStyle style) {
            super(context, mode, style);
        }

        @Override
        public final Orientation getPullToRefreshScrollDirection() {
            return Orientation.VERTICAL;
        }

        @Override
        protected ScrollView createRefreshableView(Context context,
                                                   AttributeSet attrs) {
            ScrollView scrollView;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                scrollView = new InternalScrollViewSDK9(context, attrs);
            } else {
                scrollView = new ScrollView(context, attrs);
            }

            scrollView.setId(R.id.scrollview);
            return scrollView;
        }

        @Override
        protected boolean isReadyForPullStart() {
            return mRefreshableView.getScrollY() == 0;
        }

        @Override
        protected boolean isReadyForPullEnd() {
            View scrollViewChild = mRefreshableView.getChildAt(0);
            if (null != scrollViewChild) {
                return mRefreshableView.getScrollY() >= (scrollViewChild
                        .getHeight() - getHeight());
            }
            return false;
        }

        final class InternalScrollViewSDK9 extends ScrollView {

            // 滑动距离及坐标
            private float xDistance, yDistance, xLast, yLast;

            public InternalScrollViewSDK9(Context context, AttributeSet attrs) {
                super(context, attrs);
            }

            @Override
            protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                                           int scrollY, int scrollRangeX, int scrollRangeY,
                                           int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

                final boolean returnValue = super.overScrollBy(deltaX, deltaY,
                        scrollX, scrollY, scrollRangeX, scrollRangeY,
                        maxOverScrollX, maxOverScrollY, isTouchEvent);

                // Does all of the hard work...
                OverscrollHelper.overScrollBy(MyPullToRefreshScrollView.this,
                        deltaX, scrollX, deltaY, scrollY, getScrollRange(),
                        isTouchEvent);

                return returnValue;
            }

            /**
             * Taken from the AOSP ScrollView source
             */
            private int getScrollRange() {
                int scrollRange = 0;
                if (getChildCount() > 0) {
                    View child = getChildAt(0);
                    scrollRange = Math.max(0, child.getHeight()
                            - (getHeight() - getPaddingBottom() - getPaddingTop()));
                }
                return scrollRange;
            }

            /**
             * 处理与ViewPager事件冲突
             */
            @Override
            public boolean onInterceptTouchEvent(MotionEvent ev) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        xDistance = yDistance = 0f;
                        xLast = ev.getX();
                        yLast = ev.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        final float curX = ev.getX();
                        final float curY = ev.getY();

                        xDistance += Math.abs(curX - xLast);
                        yDistance += Math.abs(curY - yLast);
                        xLast = curX;
                        yLast = curY;

                        if (xDistance > yDistance) {
                            return false;
                        }
                }
                return super.onInterceptTouchEvent(ev);
            }
        }
    }