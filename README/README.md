

## MvpRoute_Base

#### 简介

> MvpRoute  内置了基础BaseActivity BaseFragment  BasePresenter BaseView  

- 实现了mvp模式  view对presenter的自动注册功能
- BaseActivity  BaseFragment 内置了对Edittext软键盘处理 点击外部取消软键盘
- BaseActivity BaseFragment 解决了跳转回传分发难题 采用注解方式 处理回传

##### 使用方式

1. 创建Contract契约类

   > view  继承 BaseView  编写回调方法
   >
   > Presenter  继承BasePresenter类  泛型为自动注册的view
   >
   > presenter 创建presenter方法

```java
public interface NetContract {


	interface View extends BaseView {
		void loginSuccful(LoginResponse response);

		void showCollectList(CollectListBean response);

		void cleanLoginSuccful();
	}

	abstract class Presenter extends BasePresenter<View> {
		public abstract void loginWanAndroid(String userNmae, String passworld);

		public abstract void cleanLogin();

		public abstract void getCollectList(String page,String username,String passworld);
	}

```

> BasePresenter方法

```java
public abstract class BasePresenter<T extends BaseView> {
	protected RxManager rxManager;
	protected T view;


     /**
     *  自动注册解注册方法  
     */
	protected void unRegist() {
		rxManager.clear();
		view = null;
	}

	protected void regist(T view) {
		this.view = view;
		rxManager = new RxManager();
	}
}
```

2. 自定义NetPresenter继承NetContract.Presenter类

   > 编写Presenter逻辑代码

   ```
   public class NetPresenter extends NetContract.Presenter {
   
   	private final LoginService loginService;
   
   	public NetPresenter() {
   		loginService = RetrofitClient.getInstance().createApi(LoginService.class);
   	}
   
   	@Override
   	public void loginWanAndroid(String userNmae, String passworld) {
   		
   
   	}
   
   	@Override
   	public void cleanLogin() {
   		
   
   	}
   
   	@Override
   	public void getCollectList(String page,String userNmae,String passwrold) {
            
   	}
   }
   ```


3. 自定义Activity继承BaseActivity

   > 部分 BaseActivity代码

    

```java
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {
	protected T presenter;
	protected final String TAG = getClass().getSimpleName();



	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayout());
		presenter = createPresenter();
        //注册presenter类
		if (presenter != null) {
			presenter.regist(this);
		}
		initView();
		loadData();
	}
    //返回布局id
	protected abstract @LayoutRes
	int getLayout();
   
    //初始化view
	protected abstract void initView();

    
    //加载数据
	protected abstract void loadData();

    //创建presenter实现类
	protected abstract @NonNull
	T createPresenter();

	@Override
	protected void onDestroy() {
		super.onDestroy();
        //取消注册
		if (presenter != null) {
			presenter.unRegist();
		}
	}
    //分发跳转回传
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Class<? extends BaseActivity> aClass = getClass();

		Method[] declaredMethods = aClass.getDeclaredMethods();
		for (Method declaredMethod : declaredMethods) {
			Request request = declaredMethod.getAnnotation(Request.class);
			List<Integer> results = new ArrayList<>();
			if (request != null) {
				for (int i : request.result()) {
					results.add(i);
				}
			}
			boolean isSuccful = request != null && request.request() == requestCode && (request.result()[0] == -1 || (results.contains(resultCode)));
			if (isSuccful) {
				declaredMethod.setAccessible(true);
				try {
					if (data == null) {
						data = new Intent();
					}
					data.putExtra(EXERA_REQUEST, requestCode);
					data.putExtra(EXERA_RESULT, resultCode);
					declaredMethod.invoke(this, data);
				} catch (IllegalAccessException e) {
					LogUtils.d("error==>" + e.getMessage());
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					LogUtils.d("InvocationTargetException==>" + e.getTargetException().getMessage());
					e.printStackTrace();
				}
				return;
			}

		}
	}
```

> NetActivity 代码
>
> BaseToActivity  是demo中对BaseActivity适应项目的第二次封装  主要是为了自动设置标题 返回键的作用

```java
public class NetActivity extends BaseToActivity<NetContract.Presenter> implements NetContract.View {
    
   @Override
   public void onshowError(ExceptionHandle.ResponeThrowable baseThrowable) {
      ToastUtils.showShort(baseThrowable.msg);
   }

   @Override
   protected String getTooBarTitle() {
      return "网络请求";
   }

    //返回布局
   @Override
   protected int getLayout() {
      return R.layout.activity_net;
   }

    //使用presenter加载数据
   @Override
   protected void loadData() {
      findViewById(R.id.net_login).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            presenter.loginWanAndroid("tianxiaolong", "dsadsa");
         }
      });
      findViewById(R.id.net_clean).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            presenter.cleanLogin();
         }
      });
      findViewById(R.id.net_collect).setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            String username = "loginUserName="+SpUtils.getInstance().getString(KeyUtils.USERNAME);
            String passwrold = "loginUserPassword=" + SpUtils.getInstance().getString(KeyUtils.PASSWORLD);

            for(int i=0;i<5;i++){
               presenter.getCollectList("0",username,passwrold);

            }
         }
      });
   }

    //创建presenter
   @NonNull
   @Override
   protected NetContract.Presenter createPresenter() {
      return new NetPresenter();
   }


   @Override
   public void loginSuccful(LoginResponse response) {
      SpUtils instance = SpUtils.getInstance();
      instance.putValue(KeyUtils.USERNAME, response.getUsername());
      instance.putValue(KeyUtils.PASSWORLD, response.getPassword());
      ToastUtils.showShort("登录成功");
   }

   @Override
   public void showCollectList(CollectListBean response) {
      ToastUtils.showShort("获取收藏列表成功");
   }

   @Override
   public void cleanLoginSuccful() {
      ToastUtils.showShort("清除登录状态成功");
   }
}
```



#####  自动分发跳转回传的使用

> MvpRoute  使用注解 反射  简化了原生跳转回传的使用 
>
> 继承BaseActivity后  不需要再onActivityResult里面进行  if switch判断
>
> 注解

```java
	@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Request {
	String EXERA_RESULT = "result";

	String EXERA_REQUEST = "request";

	int request();
    //默认-1
	int[] result() default {-1};
}
```



```java

```

##### 点击外部隐藏软键盘

>  继承BaseActivity后重写
>
> hideSoftByEditViewIds方法  返回需要隐藏软键盘的id
>
> ```
> @Override
> public int[] hideSoftByEditViewIds() {
>    return super.hideSoftByEditViewIds();
> }
> ```