package cn.southtree.ganku.mvp.view.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.southtree.ganku.R;

/**
 * RecyclerView 的分割线
 * @author zhuo.chen
 * @version 2017/12/28
 */

public class MItemDecoration extends RecyclerView.ItemDecoration {
    private Rect mBound;
    private int mHeight;
    private Paint mPaint;
    private Context mContext;
    private int colorId;

    public MItemDecoration(Context context) {
        super();
        this.mContext = context;
        mBound = new Rect();
        mPaint = new Paint();
        colorId = R.color.colorDividerGray;
        mHeight = 2;
        mPaint.setColor(ContextCompat.getColor(mContext,colorId));
    }
    public void setmHeight(int pxHeight){
        this.mHeight = pxHeight;
    }
    public void setColorId(int id){
        this.colorId = id;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - left;
        final int childCount = parent.getChildCount();
        for (int i = 0; i< childCount-1; i++){
            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child,mBound);
            final int  bottom = (int) (mBound.bottom - child.getTranslationY());
            final int top = bottom - mHeight;
            c.save();
            c.drawRect(left,top,right,bottom,mPaint);
            c.restore();
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = mHeight;
    }
}
