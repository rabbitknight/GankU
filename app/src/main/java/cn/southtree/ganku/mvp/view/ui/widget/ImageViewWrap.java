package cn.southtree.ganku.mvp.view.ui.widget;


import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;


import java.lang.ref.WeakReference;


/**
 * 包装img
 * Created by zhuo.chen on 2018/1/4.
 */

public class ImageViewWrap implements GestureDetector.OnGestureListener,View.OnTouchListener,
        ScaleGestureDetector.OnScaleGestureListener,ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = ImageViewWrap.class.getSimpleName();

    private static final float SCALE_MAX = 4.0f;    //放大的系数
    private static float initScale = 1.0f;          //初始化的放大倍数
    private final float[] matrixValues = new float[9];  //矩阵信息数组
    private Matrix matrix = new Matrix();


    private ImageView img;
    private WeakReference<Context> context;
    private GestureDetector mDetector;
    private ScaleGestureDetector mScaleDetector;
    private float mLastX = 0;
    private float mLastY = 0;
    private boolean once = true;
    private int lastPointerCount = 0;
    private boolean isCanDrag = false;
    private float mTouchSlop;
    private boolean isCheckTopAndBottom;
    private boolean isCheckLeftAndRight;

    public ImageViewWrap(@NonNull ImageView img) {
        init(img, img.getContext());
    }

    // 获取img实例
    public ImageView getInstance() {
        return img;
    }

    // init
    private void init(ImageView img, Context context) {
        this.img = img;
        this.context = new WeakReference<Context>(context);
        mDetector = new GestureDetector(this.context.get(), this);
        mScaleDetector = new ScaleGestureDetector(this.context.get(), this);
        img.setScaleType(ImageView.ScaleType.MATRIX);
        img.setOnTouchListener(this::onTouch);
        img.getViewTreeObserver().addOnGlobalLayoutListener(this);

    }

    // 触摸回调
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleDetector.onTouchEvent(event);
        float x = 0,y = 0;
        final int pointerCount = event.getPointerCount();
        for (int i = 0; i < pointerCount; i ++){
            x += event.getX(i);
            y += event.getY(i);
        }
        x = x / pointerCount;
        y = y / pointerCount;
        if (pointerCount != lastPointerCount){
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }

        lastPointerCount = pointerCount;
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                float dx = x - mLastX;
                float dy = y - mLastY;
                if (!isCanDrag){
                    isCanDrag = isCanDrag(dx,dy);
                }
                if (isCanDrag){
                    RectF rectF = getMatrixRectF();
                    if (img.getDrawable()!=null){
                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                    }
                    if (rectF.width() < img.getWidth()){
                        dx = 0;
                        isCheckLeftAndRight = false;
                    }
                    if (rectF.height() < img.getHeight()){
                        dy = 0;
                        isCheckTopAndBottom = false;
                    }
                    matrix.postTranslate(dx,dy);
                    checkMatrixBounds();
                    img.setImageMatrix(matrix);
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_DOWN:
                lastPointerCount = 0;
                break;

        }
        return true;

    }

    private boolean isCanDrag(float dx, float dy) {
        return Math.sqrt((dx*dx)+(dy*dy)) >= mTouchSlop;
    }

    @Deprecated
    public void bindTouchEvent(MotionEvent event) {
        //mDetector.onTouchEvent(event);
    }

    // 以下为手势操作回调
    @Override
    public boolean onDown(MotionEvent e) {
        return false;

    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;

    }


    // 以下为收缩的方法回调
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        Log.i(TAG, "onScale: ++");
        float scale = getScale();                       // 当前放大情况
        float scaleFactor = detector.getScaleFactor();  // 放大系数
        if (img.getDrawable() == null) {
            Log.i(TAG, "onScale: drawable is null");
        }
        if ((scale < SCALE_MAX && scaleFactor > 1.0f)   //如果没放大最大，并且缩放因数小于1
                || (scale > initScale && scaleFactor < 1.0f)) {
            if (scaleFactor * scale < initScale) {
                scaleFactor = initScale / scale;
                Log.i(TAG, "onScale: 111" + scaleFactor);
            }
            if (scaleFactor * scale > SCALE_MAX) {
                scaleFactor = SCALE_MAX / scale;
                Log.i(TAG, "onScale: 222" + scaleFactor);

            }
            int x = img.getWidth();
            int y = img.getHeight();
            Log.i(TAG, "onScale: x=" + x + ",y=" + y);
            matrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            checkBorderAndCenterWhenScale();
            img.setImageMatrix(matrix);

        }
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    public float getScale() {
        matrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    @Override
    public void onGlobalLayout() {
        if (once) {
            Drawable d = img.getDrawable();
            if (d == null) return;
            int width = img.getWidth();
            int height = img.getHeight();
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();
            float scale = 1.0f;
            if (dw > width && dh <= height) {
                scale = width * 1.0f / dw;
            }
            if (dh > height && dw <= width) {
                scale = height * 1.0f / dh;
            }
            if (dw > width && dh > height) {
                scale = Math.min(dw * 1.0f / width, dh * 1.0f / height);
            }
            //initScale = scale;
            matrix.postTranslate((width - dw) / 2, (height - dh) / 2);
            //matrix.postScale(scale,scale,img.getWidth()/2,img.getHeight()/2);
            img.setImageMatrix(matrix);
            once = false;
        }

    }

    private void checkBorderAndCenterWhenScale() {

        RectF rect = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = img.getWidth();
        int height = img.getHeight();

        // 如果宽或高大于屏幕，则控制范围
        if (rect.width() >= width) {
            if (rect.left > 0) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
        }
        if (rect.height() >= height) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.bottom;
            }
        }
        // 如果宽或高小于屏幕，则让其居中
        if (rect.width() < width) {
            deltaX = width * 0.5f - rect.right + 0.5f * rect.width();
        }
        if (rect.height() < height) {
            deltaY = height * 0.5f - rect.bottom + 0.5f * rect.height();
        }
        Log.e(TAG, "deltaX = " + deltaX + " , deltaY = " + deltaY);

        matrix.postTranslate(deltaX, deltaY);

    }

    private void checkMatrixBounds() {
        RectF rect = getMatrixRectF();
        float deltaX = 0, deltaY = 0;
        final float viewWidth = img.getWidth();
        final float viewHeight = img.getHeight();
        // 判断移动或缩放后，图片显示是否超出屏幕边界
        if (rect.top > 0 && isCheckTopAndBottom)
        {
            deltaY = -rect.top;
        }
        if (rect.bottom < viewHeight && isCheckTopAndBottom)
        {
            deltaY = viewHeight - rect.bottom;
        }
        if (rect.left > 0 && isCheckLeftAndRight)
        {
            deltaX = -rect.left;
        }
        if (rect.right < viewWidth && isCheckLeftAndRight)
        {
            deltaX = viewWidth - rect.right;
        }
        matrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 根据当前图片的Matrix获得图片的范围
     *
     * @return
     */
    private RectF getMatrixRectF() {
        Matrix mMatrix = matrix;
        RectF rect = new RectF();
        Drawable d = img.getDrawable();
        if (null != d) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            mMatrix.mapRect(rect);
        }
        return rect;
    }

}