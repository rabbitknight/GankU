package cn.southtree.ganku.mvp.view.ui.widget;


import android.content.Context;
import android.graphics.Matrix;
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
 * 包装img，使其可以
 * Created by zhuo.chen on 2018/1/4.
 */

public class ImageViewWrap implements GestureDetector.OnGestureListener,View.OnTouchListener,
        ScaleGestureDetector.OnScaleGestureListener,ViewTreeObserver.OnGlobalLayoutListener{
    private static final String TAG = ImageViewWrap.class.getSimpleName();

    private static final float SCALE_MAX = 4.0f;    //放大的系数
    private static float initScale = 1.0f;          //初始化的放大倍数
    private final float[] matrixValues = new float[9];  //矩阵信息数组
    private Matrix matrix = new Matrix();


    private ImageView img;
    private WeakReference<Context> context;
    private GestureDetector mDetector;
    private ScaleGestureDetector mScaleDetector;
    private int mLastX = 0;
    private int mLastY = 0;
    private boolean once = true;
    public ImageViewWrap(@NonNull ImageView img){
        init(img,img.getContext());
    }
    // 获取img实例
    public ImageView getInstance(){
        return img;
    }

    // init
    private void init(ImageView img,Context context){
        this.img = img;
        this.context = new WeakReference<Context>(context);
        mDetector = new GestureDetector(this.context.get(),this);
        mScaleDetector = new ScaleGestureDetector(this.context.get(),this);
        img.setScaleType(ImageView.ScaleType.MATRIX);
        img.setOnTouchListener(this::onTouch);
        img.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }
    // 触摸回调
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i(TAG, "onTouch: ++"+v.getId());
        return mScaleDetector.onTouchEvent(event);
    }
    @Deprecated
    public void bindTouchEvent(MotionEvent event){
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
        if (img.getDrawable()==null){
            Log.i(TAG, "onScale: drawable is null");
        }
        if ((scale < SCALE_MAX && scaleFactor > 1.0f)   //如果没放大最大，并且缩放因数小于1
                ||(scale > initScale && scaleFactor < 1.0f)){
            if (scaleFactor * scale < initScale){
                scaleFactor = initScale / scale;
                Log.i(TAG, "onScale: 111"+scaleFactor);
            }
            if (scaleFactor * scale > SCALE_MAX){
                scaleFactor = SCALE_MAX / scale;
                Log.i(TAG, "onScale: 222"+scaleFactor);

            }
            int x = img.getWidth();
            int y = img.getHeight();
            Log.i(TAG, "onScale: x="+x+",y="+y);
            matrix.postScale(scaleFactor,scaleFactor,img.getWidth()/2,img.getHeight()/2);
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

    public float getScale(){
        matrix.getValues(matrixValues);
        return matrixValues[Matrix.MSCALE_X];
    }

    @Override
    public void onGlobalLayout() {
        if (once){
            Drawable d = img.getDrawable();
            if (d == null) return;
            int width = img.getWidth();
            int height = img.getHeight();
            int dw = d.getIntrinsicWidth();
            int dh = d.getIntrinsicHeight();
            float scale = 1.0f;
            if (dw > width && dh <= height){
                scale = width * 1.0f / dw;
            }
            if (dh > height && dw <= width){
                scale = height * 1.0f / dh;
            }
            if (dw > width && dh > height){
                scale = Math.min(dw * 1.0f / width, dh * 1.0f / height);
            }
            initScale = scale;
            matrix.postTranslate((width - dw)/2,(height - dh)/2);
            matrix.postScale(scale,scale,img.getWidth()/2,img.getHeight()/2);
            img.setImageMatrix(matrix);
            once = false;
        }

    }
}
