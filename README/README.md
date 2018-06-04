# Mvp_Route_Demo
<<<<<<< HEAD
![image](https://travis-ci.org/Alamofire/Alamofire.svg?branch=master)
![image](https://img.shields.io/badge/API-14+-brightgreen.svg)
![image](https://img.shields.io/badge/Mvp_Route-v1.16.1-brightgreen.svg)
=======
---
> 第一版本正在编写 详情[点击](https://github.com/TLocation/Mvp_Route_Demo/tree/dev)
---


```
graph TD
A[Mvp_Route]
A --> |Modle|B[Modle]
A --> |Presenter|C[BasePresenter]
A --> |View|D[BaseView]
B --> E[Http]
E --- q[Source]
q -->|网络管理|e[RxManager]
q --> |数据处理|r[RxScheduer]
r --> |数据解析|o[map]
r --> |异常处理|p[handlerException]
r --> |线程调度|pi[io_main]
E --- w[Gson]

B --> F[Utils]
F --> I[FragmentUtils]
F --> J[SharpfrenceUtils]
D --> 1[BaseActivity]
D --> 2[BaseFragment]

```


>>>>>>> db295bc9e2647df22fc00e14e782e73be509423f
