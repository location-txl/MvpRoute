#### 网络请求

##### 说明

> MvpRoute  封装了Retrofit 和RxJava结合的网络请求
>
> 内置了  错误处理 自动刷新token cookie持久化操作

##### 使用前的配置

###### 1. 设置数据结构

>  正常的后台服务器返回数据都会遵循一定的格式
>
> ```json
> {
>     "errorCode":0,
>     "data":null,
>     "errorMsg":"error_msg"
> }
> ```
>
> 如上面  当errorCode  为0的时候  请求成功  data 为返回的数据
>
>                                         其他值的时候请求失败  如token失效  密码错误之类的
>
> 而客户端 最后只想拿到的数据其实只是data里面的数据  所以MvpRoute专门对这里进行了处理

> 编写一个类  实现IBaseBean<T>  接口  来个MvpRoute当前请求是否成功  及错误原因

```java
public class BaseData<T> implements IBaseBean<T> {
    //这里  服务端的基础返回数据
	private int errorCode;
	private String errorMsg;
	private T data;

    //返回客户端只想要的那串 数据 
	@Override
	public T getData() {
		return data;
	}

    //是否请求成功
	@Override
	public boolean isOk() {
		return errorCode==0;
	}

    //返回错误原因 当请求失败后  Retrofit 会获取此错误码
	@Override
	public String getErrorMsg() {
		return errorMsg;
	}

    
    //返回错误码
	@Override
	public int getStatusCode() {
		return errorCode;
	}
}
```

> 编写基础数据结构类 后  客户端生成实体类 只需要生成data里面的数据即可

1. 如一个登陆接口返回的数据 我们只需要生成data的实体类即可


```json
{
    "data": {
        "collectIds": [
            1339,
            1494,
            1533,
            1260,
            1258,
            1255,
            96,
            94,
            203,
            2994,
            2922,
            -1,
            -1,
            -1,
            1165
        ],
        "email": "tttx0307@163.com",
        "icon": "",
        "id": 428,
        "password": "123",
        "token": "",
        "type": 0,
        "username": "tianxiaolong"
    },
    "errorCode": 0,
    "errorMsg": ""
}
```

```java
public class LoginResponse  {

   /**
    * collectIds : [1339,1494,1533,1260,1258,1255,96,94,203,2994,2922,-1,-1,-1,1165]
    * email : tttx0307@163.com
    * icon :
    * id : 428
    * password : 123
    * token :
    * type : 0
    * username : tianxiaolong
    */

   private String email;
   private String icon;
   private int id;
   private String password;
   private String token;
   private int type;
   private String username;
   private List<Integer> collectIds;

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getIcon() {
      return icon;
   }

   public void setIcon(String icon) {
      this.icon = icon;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getToken() {
      return token;
   }

   public void setToken(String token) {
      this.token = token;
   }

   public int getType() {
      return type;
   }

   public void setType(int type) {
      this.type = type;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public List<Integer> getCollectIds() {
      return collectIds;
   }

   public void setCollectIds(List<Integer> collectIds) {

      this.collectIds = collectIds;
   }

   @Override
   public String toString() {
      return "LoginResponse{" +
            "email='" + email + '\'' +
            ", icon='" + icon + '\'' +
            ", id=" + id +
            ", password='" + password + '\'' +
            ", token='" + token + '\'' +
            ", type=" + type +
            ", username='" + username + '\'' +
            ", collectIds=" + collectIds +
            '}';
   }
}
```

###### 2.配置异常类

> 实现  IResponseErrorMsg  接口

```java
public interface IResponseErrorMsg {
   String getErrorMsg(int errcode);

}
```

> 配置服务器的异常信息  如果errorMsg没有返回

```java
public class ErrorResponse implements IResponseErrorMsg {

   @Override
   public String getErrorMsg(int errcode) {
      String msg = "";
      switch(errcode){
         case -1:
            msg=  "好像账号不对啊";
            default:
      }
      return msg;
   }
}
```

###### 3.  配置刷新token类 如果需要

> 实现  IRefreshToken类

```java
public interface IRefreshToken {
    
    //刷新token网络请求
   Observable refreshTokenSuccful();
    //是否需要刷新token
   boolean isTokenException(int code,String errorMsg);
}
```

> 实现

```java
public class RefreshToken implements IRefreshToken {
   @Override
   public synchronized Observable refreshTokenSuccful() {
         LoginService api = RetrofitClient.getInstance().createRefreshToken(LoginService.class);
       //  如果需要保存信息 调用doOnnext操作符即可
         return api.login("tianxiaolong", "123");

   }

   @Override
   public boolean isTokenException(int code, String errorMsg) {
      if ("请先登录！".equals(errorMsg)) {
         return true;
      }
      return false;
   }
}
```

###### 4. 初始化Retrofit  

> 在 Application中初始化Retrofit

```java
//配置baseUrl
RetrofitConfig config = new RetrofitConfig("http://www.wanandroid.com/");
//配置拦截器
OkHttpClient.Builder builder = new OkHttpClient.Builder();
//配置cokkie持久化
builder.cookieJar(new CookiesManager(this));
//配置异常类
config.setErrorResponse(new ErrorResponse());
//配置刷新token类
config.setiRefreshToken(new RefreshToken());
//配置基础数据结构类  即实现IBaseBean的类
config.setGsonClass(BaseData.class);
//初始化
config.setBuilder(builder);
RetrofitClient.init(config);
```

###### 5. 使用   

1. 编写网络请求Service

   ```java
   public interface LoginService {
      @FormUrlEncoded
      @POST("user/login")
      Observable<LoginResponse> login(@Field("username") String username, @Field("password") String passworld);
   
   
      @GET("lg/collect/list/{page}/json")
      Observable<CollectListBean> getCollect(@Path("page") String page);
   
   }
   ```

   2. 调用

      ```java
      //获取Service	
      loginService = RetrofitClient.getInstance().createApi(LoginService.class);
      @Override
      public void getCollectList(String page,String userNmae,String passwrold) {
                loginService.getCollect(page)
                    //线程调度 回调到主线程
                  .compose(new RxScheduer.IO_MAIN<CollectListBean>())
                    //异常处理
                  .onErrorResumeNext(new RxScheduer.HandlerException<CollectListBean>())
                    //这里是在presenter里面请求的  自带rxmanager view
                  .subscribe(new BaseOberver<CollectListBean>(rxManager,view) {
                     @Override
                     public void onNext(CollectListBean collectListBean) {
                         //请求成功
                         view.showCollectList(collectListBean);
                     }
                    //如果请求失败  会自动回调  BaseActivity的showError方法
      
                  });
      }
      ```



   ##### 结语

   >  关于  token刷新  内部使用的是动态代理  针对每个service进行动态代理  已经处理了多个请求访问时出现的token失效问题
   >
   > 关于   异常处理    自定义Retrofit解析器    在请求失败时抛出异常 
   >
   >                              由onErrorResumeNext操作符拦截  并处理





   ##### 
