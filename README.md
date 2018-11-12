# Mvp_Route

Android项目开发框架 基于Mvp
[bata测试版](https://github.com/TLocation/Mvp_Route_Demo/tree/dev)

 [ ![Download](https://api.bintray.com/packages/location/maven/mvproute/images/download.svg) ](https://bintray.com/location/maven/mvproute/_latestVersion) [![License][licensesvg]][license]

## 导入
```groovy
implementation 'com.location.mvp:mvproute:1.0.1'
```

## 简介
   MvpRoute是一款轻量级的项目开发框架

1.   包含了Mvp框架的简易分装  实现了  自动注册  Base类  对软键盘  Activity跳转回传实现了二次封装

2.   基础网络框架（Retrofit+RxJava） 自定义异常处理   cookie验证  自动刷新Token等多种功能

3.   基础Recycler适配器  比原生RecyclerAdapter少了50%代码  支持头布局  尾部局  空布局  自定义Holder

       头部 尾部 item item子view点击事件  自定义头部复用 Group分组等多种样式

4.   基础BobPopWindow  仿Dialog样式Popwindow 支持窗口蒙层  采用构建者模式 链式调用

5.   基础 SharedPreferences 工具类  Sputils 简化sp存储方式 支持存储未序列化的实体类对象 

6.   基础FragmentUtils 工具类  简化Fragment使用  一键实现app主页多Fragment样式  仿Glide调用方式 防止调用冲突 异常

7.   基础SpanUtils 工具类  链式调用  多种文本样式 优化布局层次 

8.   BannerView（测试） 自定义轮播图  采用RxJava调度 支持页面切换长度 切换时间  自定义指示器


## 使用的第三方




```groovy
 api 'io.reactivex.rxjava2:rxandroid:2.1.0'
 api 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
 api 'com.google.code.gson:gson:2.8.5'
```

## 目录
 目录| 链接
---|---
基础组件base|[使用方式](https://github.com/TLocation/Mvp_Route_Demo/blob/master/README/README.md)
网络框架  |[Souce方式](https://github.com/TLocation/Mvp_Route_Demo/blob/master/README/net.md)
适配器|[BaseAdapter](https://github.com/TLocation/Mvp_Route_Demo/blob/master/README/baseAdapter.md)
工具类|[Utils](https://github.com/TLocation/Mvp_Route_Demo/blob/master/README/utils.md)
View |[BobPopwindow](https://github.com/TLocation/Mvp_Route_Demo/blob/master/README/BopPopwindow.md)



[licensesvg]: https://img.shields.io/badge/License-Apache--2.0-brightgreen.svg
[license]: https://github.com/TLocation/MvpRoute/tree/master/LICENSE