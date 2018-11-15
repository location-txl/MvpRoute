v1.0.3:预览

- [x] BaseActivity BaseFragment 增加layout注解  舍弃之前的getLayout抽象方法
- [x] BaseActivity  使用注解方式 实现全屏  沉浸式体验
- [x] RxScheduer 调度器更新  增加 倒计时工具方法和防止view多次点击工具方法
- [ ] Activity Fragment传值支持 未序列化的Object对象
- [ ] BobPopwindow 增加默认动画  由下到上 由上到下 由左到右 由右到左
- [ ] 增加AndroidStudio模板文件 一键创建MvpRoute样式的Activity及Fragment

v1.0.2:

- BaseAdapter支持自定义ViewHolder 重构ViewHolder代码  对调用无影响
- 解决BaseAdapter尾部局大于头布局时滑动引起的崩溃bug
- 网络模块增加LoadingView接口 可以通过此接口在发起网络请求时弹出对应的dialog
- 去除网络模块通过Builder方式发起的网络请求
- 完善BobPopWindow  增加 top right left bottom四种显示方式
- 完善TimeUtils  支持以秒为单位的时间戳格式化
- 修改项目Gradle文件  去除 support design GsonAdapter RxJava依赖 优化项目体积
-  项目增加完整注释

v1.0.0
- mvproute发布
