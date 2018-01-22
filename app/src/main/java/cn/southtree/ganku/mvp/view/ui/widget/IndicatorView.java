package cn.southtree.ganku.mvp.view.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

import cn.southtree.ganku.R;

/**
 * 一个ViewPager指示器
 *
 * @author zhuo.chen
 * @version 2018/1/18.
 */

public class IndicatorView extends View implements ViewPager.OnPageChangeListener {
    private static final String TAG = "IndicatorView";
    private static final float M = 0.552284748831f;

    private Paint mPaint;
    private Path mPath;
    private int mHeight;
    private int mWidth;
    private int count = 4;              // 指示器的个数
    private int pos = 0;
    private int eachWidth = 0;
    private boolean isFirst = true;

    private BezierCircle bezierCircle;
    private ValueAnimator valueAnimator1;
    private ControlPoint edgePoint;  // 画布左控制点

    private List<Rect> rects = new ArrayList<>();


    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener onPageChangeListener;

    public void setViewPager(ViewPager vp) {
        this.mViewPager = vp;
        count = vp.getAdapter().getCount();
    }


    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);  // 抗锯齿
        mPaint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        mPath = new Path();
        bezierCircle = new BezierCircle();
        edgePoint = new ControlPoint(0, 0, 0);

    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.i(TAG, "onLayout: +"+top+","+bottom);
        super.onLayout(changed, left, top, right, bottom);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int minR = 50;
        switch (heightMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                heightSize = 2 * minR + 60;        //默认30的内边距
                break;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(edgePoint.getX(), 0);
        canvas.drawPath(bezierCircle.getPath(), mPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            valueAnimator1.start();
        }
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        boolean toRight = position + positionOffset - pos > 0;
        setBezierFlexible(position, positionOffset, toRight);
        edgePoint.setX(eachWidth * (position + positionOffset));
        Log.i(TAG, "onPageScrolled: " + edgePoint.getX());
        if (positionOffset == 0) {
            if (toRight) {
                pos++;
            } else {
                pos--;
            }
        }
        postInvalidate();


    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //
    private void translatePos() {

    }

    // 初始化根据tab个数，初始化部分布局
    public void setTagCount(int count) {
        this.count = count;
        eachWidth = 1080 / count;
        rects = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            rects.add(new Rect(i * eachWidth, 0, (i + 1) * eachWidth, 200));
        }
        Log.i(TAG, "setTagCount: " + rects);
        pos = mViewPager.getCurrentItem();
        bezierCircle.init(rects.get(pos).centerX(), rects.get(pos).centerY(), mHeight > eachWidth ? eachWidth / 3 : mHeight / 3);
        edgePoint.setX(0);
        postInvalidate();

    }

    /**
     * 仅设置BezierCircle的形变
     *
     * @param position 当前的position
     * @param offset   偏移的比例
     * @param toRight  往右还是左
     */
    private void setBezierFlexible(int position, float offset, boolean toRight) {
        // 注：向左向右是一个近似对等的，向左滑动时的形变可能就是向右滑动时的位置恢复！
        if (offset > 0 && offset <= 0.2) {  // 右控制点向右偏移；右控制点恢复
            bezierCircle.setFlexible(offset * 5,
                    BezierCircle.STATE_CHANGE_RIGHT);
        } else if (offset > 0.2f && offset <= 0.5f) {
            if (!toRight) { // null；左控制点回收，右控制点移动，上下点向左移动
                bezierCircle.setFlexible((offset - 0.2f) * 10 / 3, BezierCircle.STATE_CHANGE_LEFT);
                bezierCircle.setFlexible((offset - 0.2f) * 10 / 3, BezierCircle.STATE_REGRESS_RIGHT);
                bezierCircle.setFlexible((offset - 0.2f) * 10 / 3, BezierCircle.STATE_CHANGE_CENTER_LEFT);
            } else {    // 上下点右移；null
                bezierCircle.setFlexible((offset - 0.2f) * 10 / 3, BezierCircle.STATE_CHANGE_CENTER_RIGHT);
            }
        } else if (offset > 0.5f && offset <= 0.8f) {
            if (toRight) {  // 左控制点左移，右控制点恢复，上下点恢复；null
                bezierCircle.setFlexible((offset - 0.5f) * 10 / 3, BezierCircle.STATE_CHANGE_LEFT);
                bezierCircle.setFlexible((offset - 0.5f) * 10 / 3, BezierCircle.STATE_REGRESS_RIGHT);
                bezierCircle.setFlexible((offset - 0.5f) * 10 / 3, BezierCircle.STATE_REGRESS_CENTER_RIGHT);
            } else {    // null；上下点右移
                bezierCircle.setFlexible((offset - 0.5f) * 10 / 3, BezierCircle.STATE_REGRESS_CENTER_LEFT);
            }
        } else if (offset > 0.8f && offset <= 1.0f) { // 左点恢复；左点右移
            bezierCircle.setFlexible((offset - 0.8f) * 5, BezierCircle.STATE_REGRESS_LEFT);
        }
        // 请求重绘
        postInvalidate();
    }

    //******************** Some Inner Class *******************//
    class ControlPoint {
        // oop：point is point ，
        private float x;    // 横坐标
        private float y;    // 纵坐标
        private float d;    // 控制点距离

        // 弹性控制系数
        private float flexibleX = 1.0f;
        private float flexibleY = 1.0f;

        public float getX() {

            return x * flexibleX;
        }

        public float getY() {
            return y * flexibleY;
        }

        public float getLeftX() {
            return getX() - d;
        }

        public float getLeftY() {
            return getY();
        }

        public float getTopX() {
            return getX();
        }

        public float getTopY() {
            return getY() - d;
        }

        public float getRightX() {
            return getX() + d;
        }

        public float getRightY() {
            return getY();
        }

        public float getBottomX() {
            return getX();
        }

        public float getBottomY() {
            return getY() + d;
        }

        public void setXYZ(float x, float y, float d) {
            setX(x);
            setY(y);
            setD(d);
        }

        public void setX(float x) {
            this.x = x;
        }

        public void setY(float y) {
            this.y = y;
        }

        public void setD(float d) {
            this.d = d;
        }

        public void setXY(int x, int y) {
            setX(x);
            setY(y);
        }

        @Deprecated
        public void setFlexibleX(float flexibleX) {
            this.flexibleX = flexibleX;
        }

        @Deprecated
        public void setFlexibleY(float flexibleY) {
            this.flexibleY = flexibleY;
        }

        public ControlPoint(float x, float y, float d) {
            setXYZ(x, y, d);
        }

        public void translateX(float dx) {
            this.x += dx;
        }

    }

    class BezierCircle {
        public static final int STATE_0 = 0;                        // 预留
        public static final int STATE_CHANGE_RIGHT = 2;             // 右形变
        public static final int STATE_CHANGE_CENTER_RIGHT = 4;      // 中心右偏移形变
        public static final int STATE_CHANGE_CENTER_LEFT = 8;       // 中心左偏移形变
        public static final int STATE_CHANGE_LEFT = 16;             // 左形变
        public static final int STATE_REGRESS_RIGHT = 32;           // 右回归
        public static final int STATE_REGRESS_LEFT = 64;            // 左回归
        public static final int STATE_REGRESS_CENTER_RIGHT = 128;   // 中心右偏移回归
        public static final int STATE_REGRESS_CENTER_LEFT = 256;    // 中心左偏移回归

        // 一个圆通过四个点控制
        private ControlPoint left;
        private ControlPoint right;
        private ControlPoint top;
        private ControlPoint bottom;
        private Path mPath;

        // 弹性控制

        // 中心点坐标
        private float x;
        private float y;
        private float r;

        private float finalModX = 1 * 1f;
        private float middleModX = 1 * 1f / 2;

        public BezierCircle(float x, float y, float r) {
            init(x, y, r);
        }

        public BezierCircle() {
            this(0, 0, 0);
        }

        // 初始化，init坐标点
        private void init(float x, float y, float r) {
            this.x = x;
            this.y = y;
            this.r = r;

            if (null == mPath) {
                mPath = new Path();
            }
            float d = r * M;
            left = new ControlPoint(x - r, y, d);
            top = new ControlPoint(x, y - r, d);
            right = new ControlPoint(x + r, y, d);
            bottom = new ControlPoint(x, y + r, d);
        }

        // 获取路径
        public Path getPath() {
            if (null == mPath) {
                mPath = new Path();
            }
            mPath.reset();
            mPath.moveTo(left.getX(), left.getY());
            mPath.cubicTo(left.getTopX(), left.getTopY(), top.getLeftX(), top.getLeftY(), top.getX(), top.getY());
            mPath.cubicTo(top.getRightX(), top.getRightY(), right.getTopX(), right.getTopY(), right.getX(), right.getY());
            mPath.cubicTo(right.getBottomX(), right.getBottomY(), bottom.getRightX(), bottom.getRightY(), bottom.getX(), bottom.getY());
            mPath.cubicTo(bottom.getLeftX(), bottom.getLeftY(), left.getBottomX(), left.getBottomY(), left.getX(), left.getY());
            return mPath;
        }

        // 位置移动 distance x Or y
        @Deprecated
        public void translate(float dx, float dy) {
            x += dx;
            y += dy;
            //更改坐标
            init(x, y, r);
            // 请求重绘
            //invalidate();
        }

        // 形变，过程中基于bezierCircle的X or Y坐标进行形变
        // abs:flexible [0-1.0)
        public void setFlexible(float flexible, int state) {
            switch (state) {
                case STATE_0:   // 暂留
                    break;
                case STATE_CHANGE_RIGHT:   // 右形变
                    right.setX(this.x + r + r * finalModX * flexible);
                    break;
                case STATE_CHANGE_CENTER_RIGHT:   // 中心右偏移
                    top.setX(this.x + r * middleModX * flexible);
                    bottom.setX(this.x + r * middleModX * flexible);
                    break;
                case STATE_CHANGE_CENTER_LEFT:
                    top.setX(this.x - r * middleModX * flexible);
                    bottom.setX(this.x - r * middleModX * flexible);
                    break;
                case STATE_CHANGE_LEFT:   // 左形变
                    left.setX(this.x - r - r * finalModX * flexible);
                    break;
                case STATE_REGRESS_RIGHT:   // 右回归
                    right.setX(this.x + r + (1 - flexible) * r * finalModX);
                    break;
                case STATE_REGRESS_LEFT:   // 左回归
                    left.setX(this.x - r - (1 - flexible) * r * finalModX);
                    break;
                case STATE_REGRESS_CENTER_RIGHT:   // 中心右回归
                    top.setX(this.x + (1 - flexible) * r * middleModX);
                    bottom.setX(this.x + (1 - flexible) * r * middleModX);
                    break;
                case STATE_REGRESS_CENTER_LEFT:   // 中心左回归
                    top.setX(this.x - (1 - flexible) * r * middleModX);
                    bottom.setX(this.x - (1 - flexible) * r * middleModX);
                    break;
            }
        }

    }

}
