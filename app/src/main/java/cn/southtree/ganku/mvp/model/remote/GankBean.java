package cn.southtree.ganku.mvp.model.remote;

import java.util.List;

/**
 * Created by Southtree on 2017/11/16.
 */

public class GankBean {

    /**
     * _id : 5a0bcf5a421aa90fe725363c
     * createdAt : 2017-11-15T13:23:38.991Z
     * desc : [开发利器]在线查看对比 Android 和 Java 任意版本源码 IDEA插件
     * images : ["http://img.gank.io/a3fc2a25-adea-45de-b186-17884187280c"]
     * publishedAt : 2017-11-16T12:01:05.619Z
     * source : web
     * type : Android
     * url : https://github.com/pengwei1024/AndroidSourceViewer
     * used : true
     * who : 舞影凌风
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
