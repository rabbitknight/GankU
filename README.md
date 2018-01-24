# GankU
根据[gank.io](http://gank.io/api)公开API实现的内容阅读类App。作为对近期知识内容梳理总结。

###使用技术：

 1. MVP
 2. RxJava2
 3. Dagger2
 4. ...
 5. 
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
Main2|![main2](https://github.com/rabbitknight/GankU/blob/master/gifs/gif_main2.gif?raw=true)
Web|![web](https://github.com/rabbitknight/GankU/blob/master/gifs/gif_web.gif?raw=true)
----|---