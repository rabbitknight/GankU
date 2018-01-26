package cn.southtree.ganku.mvp.view.interfaces;

import cn.southtree.ganku.mvp.view.base.IBaseView;

/**
 * WebActivity的View接口
 *
 * @author zhuo.chen
 * @version 2018/1/8.
 */

public interface WebV extends IBaseView {
    void setTitle(String title);

    void changeProgress(int progress);

    void refresh();

    void reload();

    String getShareContent();

    void send2Apps(String content, boolean isUrl);
}
