# Mvp_Route

Android项目开发框架 基于Mvp
[bata测试版](https://github.com/TLocation/Mvp_Route_Demo/tree/dev)

 [ ![Download](https://api.bintray.com/packages/location/maven/mvproute/images/download.svg) ](https://bintray.com/location/maven/mvproute/_latestVersion)[![License][licensesvg]][license]

## [更新日志](https://github.com/TLocation/MvpRoute/blob/master/README/update.md)
   
## 导入
```groovy
implementation 'com.location.mvp:mvproute:1.0.2'
```

## 简介
   MvpRoute是一款轻量级的项目开发框架

1. 包含了Mvp框架的简易分装  实现了  自动注册  Base类  对软键盘  Activity跳转回 传值 沉浸式传实现了二次封装

2. 封装网络框架（Retrofit+RxJava） 自定义异常处理   cookie验证  自动刷新Token等多种功能

3. 封装RecyclerView适配器  比原生RecyclerAdapter少了50%代码  支持头布局  尾部局  空布局  自定义Holder

     头部 尾部 item item子view点击事件  自定义头部复用 Group分组等多种样式

4. 封装BobPopWindow  仿Dialog样式Popwindow 支持窗口蒙层  采用构建者模式 链式调用 更友好的api

5. 封装多种工具类  尺寸转换 时间转换  富文本编辑 Toast Fragment管理 json串格式 SharedPreferences存储

   日志工具   view多次防点击 倒计时控件  与MvpRoute深度解耦 可以拷贝单独使用

6. BannerView  自定义轮播图  采用RxJava调度 支持页面切换长度 切换时间  自定义指示器

## 使用

在Application中初始化

```java
//-------初始化MvpRoute
   		//使用默认配置
		RouteManager.init(this);
		
		//使用自定义配置
		RouteOptions options = new RouteOptions();
		//设置view点击的过滤时间为2秒
		options.setFilterClickTime(2);
		//设置全局沉浸式状态栏
		options.setTransStatus(true);
		//设置在沉浸式状态栏下布局内容在状态栏之下
		options.setStatusPaddingTop(true);
		RouteManager.init(this,options);
		
//-------初始化网络
		//实例化Config对象  设置Retrofit baseUrl
		RetrofitConfig config = new RetrofitConfig("baseurl");
		//设置自定义的Okhttpclient 可选
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		config.setBuilder(builder);
		//设置异常解析类  可选
		config.setErrorResponse(new ErrorResponse());
		//设置刷新token类  可选
		config.setiRefreshToken(new RefreshToken());
		//设置解析父类 必选
		config.setGsonClass(BaseData.class);
		//设置网络请求时弹出的dialog 可选
		config.setLodingView(new LoadingView());
		//初始化
		RetrofitClient.init(config);
```




## 使用的第三方




```groovy
 api 'io.reactivex.rxjava2:rxandroid:2.1.0'
 api 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
 api 'com.google.code.gson:gson:2.8.5'
```

## 目录

**[MvpBase类](https://github.com/TLocation/Mvp_Route_Demo/blob/master/README/README.md)**

**[网络框架Retrofit结合RxJava](https://github.com/TLocation/Mvp_Route_Demo/blob/master/README/net.md)**

**[RecyclerView适配器](https://github.com/TLocation/Mvp_Route_Demo/blob/master/README/baseAdapter.md)**

**[工具类](https://github.com/TLocation/Mvp_Route_Demo/blob/master/README/utils.md)**

**[BobPopwindow](https://github.com/TLocation/Mvp_Route_Demo/blob/master/README/BopPopwindow.md)**




[licensesvg]: https://img.shields.io/badge/License-Apache--2.0-brightgreen.svg
[license]: https://github.com/TLocation/MvpRoute/blob/master/LICENSE
