# GankU
+ 根据[gank.io](http://gank.io/api)公开API实现的内容阅读类App。作为对近期知识内容梳理总结。
更多参考[个人博客：https://southtree.cn/2018/01/25/A008/#more](https://southtree.cn/2018/01/25/A008/#more)
### TL;DR
技术|概要
---|---
RxJava2+Retrofit|RxJava仅配合Retrofit，作为网络请求资源的处理使用。
MVP|使用mvp架构思想，M层未做较优处理。V、P层隔离、仅接口访问。Activity与Fragment既是V层，又是P与V结合的地方。
Dagger2|强化Dagger的使用，P层、控件、网络请求均使用Dagger注入到Act\Frag。
M D|使用ToolBar、NavView、SRL、CoordinatorLayout等。
Animator|部分使用了属性动画、包含animated-vector向量动画的使用。
Custom View|为ImageView添加包装类实现手势操作，一个使用了贝塞尔曲线的indicator，自定义的ItemDecoration等。
Web|使用腾讯X5内核作为WebView，待添加CustomTabs获取更优秀的效果。
...|...
---|---
### 项目结构
```
└─ganku
    ├─common			+ common包，静态变量等
    ├─di				+ dagger依赖注入包
    │  ├─component		- 注射器 
    │  ├─module			- 模块
    │  └─scope				- 作用域
    ├─mvp				+ mvp包，项目为mvp架构
    │  ├─model			+ 数据层，有缺陷
    │  │  └─remote			- bean类等
    │  ├─presenter		+ 表现层，专注业务逻辑处理
    │  │  ├─base			- 最基本接口
    │  │  ├─impl			- 接口实现
    │  │  └─interfaces		- 集成接口
    │  └─view			+ 显示层，专注View的显示
    │      ├─base			- 最基本的接口
    │      ├─interfaces		- 集成接口
    │      └─ui				+ ui显示包，接口实现
    │          ├─activity		- Activity
    │          ├─adapter		- 适配器
    │          ├─fragment		- 碎片，用于显示
    │          ├─listener		- 监听器
    │          └─widget			- 自定义组件
    ├─net				+ 网络访问包，注：已被废弃
    │  └─api				- Retorfit的接口
    └─utils				+ utils辅助类，String处理等
```
### 运行Demo
desc|img
---|---
launcher|![launcher](https://github.com/rabbitknight/GankU/blob/master/gifs/gif_launcher2.gif?raw=true)
main|![main1](https://github.com/rabbitknight/GankU/blob/master/gifs/gif_main1.gif?raw=true)
main2|![main2](https://github.com/rabbitknight/GankU/blob/master/gifs/gif_main2.gif?raw=true)
Web|![web](https://github.com/rabbitknight/GankU/blob/master/gifs/gif_web.gif?raw=true)
---|---
### 使用技术：
参考build.gradle
```
    compile 'com.android.support:appcompat-v7:26.1.0'
    compile 'com.android.support:support-v4:26.1.0'
    compile 'com.android.support:design:26.1.0'
    compile 'com.android.support:recyclerview-v7:26.1.0'
    compile 'com.android.support:cardview-v7:26.1.0'
    compile 'com.android.support:customtabs:26.1.0'
    testCompile 'junit:junit:4.12'
    compile 'com.google.dagger:dagger:2.13'
    annotationProcessor 'com.google.dagger:dagger-compiler:2.13'
    compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
    compile 'io.reactivex.rxjava2:rxjava:2.1.6'
    compile 'com.github.bumptech.glide:glide:4.3.1'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.3.1'
    compile 'com.squareup.retrofit2:retrofit:2.3.0'
    compile 'com.squareup.retrofit2:adapter-rxjava:2.3.0'
    compile 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
    compile 'com.squareup.retrofit2:converter-gson:2.3.0'
    compile 'com.squareup.okhttp3:okhttp:3.9.0'
    compile 'com.jakewharton:butterknife:8.4.0'
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.4.0'
```
### TODO
+ IMG
    - ImageViewWrap，手势包装类的优化
    - 妹子图的下载收藏等
+ Web
	- CustomTab的集成，获取更好体验
	- 右上角分享、复制URL等功能
+ 主页
	- 主题切换
	- 部分细节优化
+ ...
