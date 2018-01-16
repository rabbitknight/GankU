package cn.southtree.ganku.mvp.view.ui.listener;

import android.support.v4.view.ViewPager;

/**
 * ViewPager滑动到最后一页跳转方法
 *
 * @author zhuo.chen
 * @version 2018/1/15.
 */

public class ViewPagerChangeListener implements ViewPager.OnPageChangeListener {
    private boolean isScrolled = false;
    private ViewPager viewPager;
    public OnScrolled onScrolled = (isHeader) -> {

    };

    public ViewPagerChangeListener(ViewPager viewPager) {
        this(viewPager, null);
    }

    public ViewPagerChangeListener(ViewPager viewPager, OnScrolled onScrolled) {
        if (null != onScrolled) this.onScrolled = onScrolled;
        this.viewPager = viewPager;
    }

    public void setListener(OnScrolled onScrolled) {
        this.onScrolled = onScrolled;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                isScrolled = false;
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                isScrolled = true;
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                if (1 != viewPager.getAdapter().getCount() && viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isScrolled) {
                    onScrolled.scroll(false);
                } else if (0 == viewPager.getCurrentItem() && !isScrolled) {
                    onScrolled.scroll(true);
                }
                isScrolled = true;
                break;
        }
    }

    public interface OnScrolled {
        void scroll(boolean isHeader);
    }
}
