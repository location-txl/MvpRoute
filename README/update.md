v1.0.2:

- Baseactivity BaseFragment增加 layout injectBundle注解

  layout注解用于绑定布局 无需重写getLayout()方法
  InjectBundle用于动态注入activity fragment传值 并且支持位序列化的object对象

- BaseActivity 增加FullScreen StatusBar注解

   FullScreen注解用于使Activity展示全屏状态
   StatusBar注解用于设置沉浸式状态栏

- RxScheduer 调度器更新  增加countDown 倒计时方法

                                               增加click    防止view多次点击方法

- BobPopwindow优化 增加 更多显示方法

- 删除onDoubleClcik方法  后续统一使用RxScheduer方法

- 新增AppManager类  用于记录当前在内的Activity BaseActivity内部已经自动调用

  开发者可以使用此类来关闭Activity

- MvpRoute新增全局初始化方法 RouteManager方法  新增RouteManager配置类RouteOptions

- 新增MvpRoute模板文件 开发者可以一键生成Mvp样式的activity和fragment


v1.0.1:

- BaseAdapter支持自定义ViewHolder 重构ViewHolder代码  对调用无影响
- 解决BaseAdapter尾部局大于头布局时滑动引起的崩溃bug
- 网络模块增加LoadingView接口 可以通过此接口在发起网络请求时弹出对应的dialog
- 去除网络模块通过Builder方式发起的网络请求
- 完善BobPopWindow  增加 top right left bottom四种显示方式
- 完善TimeUtils  支持以秒为单位的时间戳格式化
- 修改项目Gradle文件  去除 support design GsonAdapter RxJava依赖 优化项目体积
- 项目增加完整注释

v1.0.0
- mvproute发布
