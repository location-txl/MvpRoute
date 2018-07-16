# Mvp_Route_Demo

![image](https://travis-ci.org/Alamofire/Alamofire.svg?branch=master)
![image](https://img.shields.io/badge/API-14+-brightgreen.svg)
![image](https://img.shields.io/badge/Mvp_Route-v1.0.0-brightgreen.svg)

Android项目开发框架 基于Mvp
[bata测试版](https://github.com/TLocation/Mvp_Route_Demo/tree/dev)
## 简介
  1. 基础Presenter View BaseActivity BaseFragment 利用RxJava快速绑定生命周期
  2. Retrofit和RxJava完美封装 
     1. 可以自主下载好Modle使用它DataSouce实现 自行扩展
     2.  使用内置ParamsBuilder实现网络请求（扩展性小）
  3. RecyclerView万能适配器  支持EmptyView HeaderView FooterView 刷新动画
     多布局  友好扩展ViewHolder
  4. 封装多种工具类 LogUtils SpUtils SpanUtils ToastUtils FragmentUtils
  5. 封装Popwindow像dialog一样使用popwindow

## 使用的第三方




```
compile 'io.reactivex.rxjava2:rxjava:2.1.0'

compile 'io.reactivex.rxjava2:rxandroid:2.0.1'

compile 'com.squareup.retrofit2:retrofit:2.4.0'

compile 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'

compile 'com.squareup.retrofit2:converter-gson:2.2.0'
```

## 目录
 目录| 链接
---|---
基础组件base|[使用方式](https://github.com/TLocation/Mvp_Route_Demo/blob/master/README/README.md)
网络框架  |[Souce方式](https://github.com/TLocation/Mvp_Route_Demo/blob/master/README/net.md)
   ||[Builder方式](https://github.com/TLocation/Mvp_Route_Demo/blob/master/README/paramBuilder.md)
适配器|[BaseAdapter](https://github.com/TLocation/Mvp_Route_Demo/blob/master/README/baseAdapter.md)
工具类|[Utils](https://github.com/TLocation/Mvp_Route_Demo/blob/master/README/utils.md)
View |[BobPopwindow](https://github.com/TLocation/Mvp_Route_Demo/blob/master/README/BopPopwindow.md)



  
