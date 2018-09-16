### BaseAdapter

#### 说明

> BaseAdapter是对原生的RecyclerView.Adapter的二次封装  
>
> 功能
>
> - 头布局 尾部局  一键添加头尾布局  设置数据在adapter内  和Activity分离  layout一样的布局会被复用 减小内存消耗
> - 空布局  当adapter没有数据的时候展示  多种展示模式  详见后面的方法注释
> - 多类型  使用接口回调 类型转到实体类决定  
> - group分组  用于部分app我的  个人中心类的模式  多样式分割线
> - 点击事件  处理了  头布局  尾部局  item  item内的view的点击事件
>
> 相对于原生的adapter  最少减少了50%的代码量



>  原生的adapter 

```java
public class MessAAdapter  extends RecyclerView.Adapter<MessAAdapter.ViewHolder>{
private List<NoMessageBean> data;
private @LayoutRes int layout;

   public MessAAdapter(List<NoMessageBean> data, int layout) {
      this.data = data;
      this.layout = layout;
   }

   @Override
   public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view  = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
      return new ViewHolder(view);
   }

   @Override
   public void onBindViewHolder(ViewHolder holder, int position) {
         holder.textView.setText(data.get(position).getName());
   }

   @Override
   public int getItemCount() {
      return data.size();
   }

   class  ViewHolder extends RecyclerView.ViewHolder{
TextView textView;
      public ViewHolder(View itemView) {
         super(itemView);
         textView  = itemView.findViewById(R.id.item_content);
      }
   }
}
```

> 使用Baseadapter

```java
public class MessageAdapter extends BaseAdapter<NoMessageBean> {
   public MessageAdapter(Collection<NoMessageBean> data, int layout) {
      super(data, layout);
   }

   @Override
   public void conver(ViewHolder holder, @Nullable NoMessageBean data, int viewType) {
      holder.setText(R.id.item_content, data.getName());

   }
}
```

>  可以看到  使用BaseAdapter  后  只需要重写一个conver方法  如果参数更多  多类型  差距会更大

#### 使用方式

> 继承BaseAdapter  类
>
> 重写构造方法
>
> 有三种构造方法可以选择

| public BaseAdapter(@LayoutRes int layout) { }                |
| ------------------------------------------------------------ |
| public BaseAdapter(Collection<T> data, @LayoutRes int layout) {    } |
| public BaseAdapter(Collection<T> data, @LayoutRes int layout, OnItemClickListener listener) {     } |

>  在conver 方法里面设置数据
>
> viewholder方法
>
> 开发者可以根据项目需求自行扩展

|                           方法                            | 解释                |
| :-------------------------------------------------------: | ------------------- |
|               findViewById(@IdRes int ids)                | 寻找view            |
|    setText(@IdRes int ids, CharSequence charSequence)     | 给TextView设置数据  |
|          setText(@IdRes int ids, String message)          | 给textview设置数据  |
|     setText(@IdRes int ids, @StringRes int stringres)     | 给TextView设置数据  |
|                       getItemView()                       | 得到itemview        |
| setImageResouce(@IdRes int ids, @DrawableRes int resouce) | 给ImageView设置数据 |
|               setVisibility(@IdRes int ids)               | 显示某个view        |
|                  setGone(@IdRes int ids)                  | 隐藏某个view        |
|                       getContext()                        | 得到上下文对象      |

> activity代码

```java
recyclerView = findViewById(R.id.home_RecyclerView);
recyclerView.setLayoutManager(new LinearLayoutManager(this));
recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration
      .VERTICAL_LIST, 5, Color.parseColor("#999999")));

homeAdapter = new HomeAdapter(R.layout.item_home);
recyclerView.setAdapter(homeAdapter);
//设置item点击事件
homeAdapter.setOnItemClickListener(this);
//设置itemview里面id_1的点击事件
homeAdapter.setOnChildClickListener(R.id.id_1, new OnChildClickListener() {
   @Override
   public void onChildClcik(ViewHolder viewHolder, View view, int position) {
      
   }
});
//设置头尾布局的点击事件
homeAdapter.setOnHeaderClickListener(new OnHeaderClickListener() {
   @Override
   public void onHeaderClick(int layout, View view, @Nullable Object data, int position, boolean isHeader) {
      
   }
});
```

#### 添加头布局

>  Baseadapter不同于其他的适配器的封装
>
> 对于头尾布局的数据设置移到了Adapter内部  更易于管理
>
> 添加头布局

| addHeaderView(Object data, @LayoutRes int layout)            | data  头布局数据 layout  头布局id |
| ------------------------------------------------------------ | --------------------------------- |
| addHeaderView(int index, Object data, @LayoutRes int layout) | index  头布局的位置               |
| addHeaderView(@LayoutRes int layout)                         | 只添加布局  没有数据              |

```java
adapterHome = new AdapterHome(list, R.layout.item_home);
//    adapterHome.addHeaderView("20", R.layout.header_view);
      adapterHome.addHeaderView("123",R.layout.header_test_view);
      adapterHome.addHeaderView("123",R.layout.header_test_view);
//    adapterHome.addHeaderView("20", R.layout.header_view);
//    adapterHome.addHeaderView("20", R.layout.header_view);
      recyclerView.setAdapter(adapterHome);
```

> 刷新头布局方法

| updateHeader(Object data, @LayoutRes int layouts)            | 刷新头布局  data数据  layouts  刷新的头布局id                |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| updateHeader(List datas, @LayoutRes int layouts, @IntRange(to = 0) Integer... index) | 有多个相同布局id的头布局刷新方法 index  头布局相对于这个布局的索引 |

> 绑定数据
>
> 重写adapter里面的onBindHeaderViewHolder方法

```java
@Override
public void onBindHeaderViewHolder(ViewHolder viewHolder, @Nullable Object response, int layout) {
    //判断layout布局
   switch (layout) {
      case R.layout.header_test_view:
           //response重写为你添加头布局时的类型
         ImageView imageView = viewHolder.findViewById(R.id.header_imageview);
         Glide.with(viewHolder.getContext()).load((String)response).into(imageView);
         break;
      default:
   }
}
```

#### 多item类型

>  实体类 实现MulitTypeListener类
>
> 重写 getItemType 方法
>
> adapter调用addType绑定布局
>
> 适配器根据布局设置数据

1. 实体类

   ```java
   public class ItemTypeResponse implements MulitTypeListener {
      private int type;
      private String content;
   
      public ItemTypeResponse(int type, String content) {
         this.type = type;
         this.content = content;
      }
   
      public int getType() {
         return type;
      }
   
      public void setType(int type) {
         this.type = type;
      }
   
      public String getContent() {
         return content;
      }
   
      public void setContent(String content) {
         this.content = content;
      }
   
       //重写 getItemType方法  设置type
      @Override
      public int getItemType() {
         return type;
      }
   }
   ```

2. adapter绑定布局

```java
for (int i = 0; i < 20; i++) {
   ItemTypeResponse response = new ItemTypeResponse(i%2,"测试"+i);
   list.add(response);
}
adapterHome = new AdapterHome(list, R.layout.item_home);
//绑定多布局  第一个参数  type类型  第二个参数  当前类型所对的布局
adapterHome.addType(0,R.layout.item_home);
adapterHome.addType(1,R.layout.item_button);
```

3. adaptrer设置数据

```java
@Override
public void conver(ViewHolder holder, @Nullable ItemTypeResponse data, int viewType) {
    //根据type 设置数据
   switch (viewType){
      case 0:
         holder.setText(R.id.home_text, data.getContent());
         break;
      case 1:
         holder.setText(R.id.home_button, data.getContent());
         break;
      default:
   }
}
```

#### 设置空布局

> BaseAdapter还可以设置空布局展示  
>
> 调用  setEmptyView(View view); 方法
>
> 会在adapter没有数据的时候展示
>
> 分别有两种模式

| setEmptyModle（@EmptyMode int mode) | EMPTY_MODLE_NOMAL当没有数据 并且头尾布局都没有的情况下显示   |
| ----------------------------------- | ------------------------------------------------------------ |
|                                     | EMPTY_MODLE_HEADERS  当数据没有时显示即使已经添加了头布局尾布局 |



### baseadpater方法注释

方法名 | 解释
---|---
BaseAdapter（int layout）| 构造方法  需要传入item布局
BaseAdapter(Collection<T> data, @LayoutRes  int layout) | data  数据源 layout item布局
BaseAdapter(Collection<T> data, @LayoutRes int layout, AbsListView.OnItemClickListener listener) | data 数据源 layout  item布局 listener item点击事件
初始化------------- | 
setData(Collection<T> data)  |设置数据源
addHeaderView(Object t, @LayoutRes int layout)|添加头布局  data 数据源  layout 头布局的id
addHeaderView(@LayoutRes int layout) | 添加头布局 
addFooterView(Object data, @LayoutRes int layout)| 添加尾部局  data 数据源  layout 尾部局id
addFooterView(@LayoutRes int layout) | 添加尾部局
setDrawHeaderFooterLine(boolean) | 是否绘制头尾布局分割线  默认绘制
setEmptyView(@NonNull View view)|  设置空布局 直接传View对象
setEmptyModle(@EmptyModle int modle) |设置空布局的显示模式 需要传固定的值 见下方
|| EMPTY_MODLE_HEADERS  （默认值） 当数据没有时显示 即使已经添加了头布局尾部局
|| EMPTY_MODLE_NOMAL    （可选值） 当没有数据 并且头尾布局都没有的情况下显示
addType(int type,int layouts) |多item布局时使用  这里 数据类要实现MulitTypeListener 接口 进行布局绑定type 布局类型  layouts 布局
setOnItemClickListener（）|设置item的点击事件
setOnChildClickListener（id ids,OnChildClickListener listener)|设置子view的点击事件 ids 子view的id listener 点击回调
setOnHeaderClickListener（OnHeaderClickListener onHeaderClickListener）|设置头尾布局的点击事件
刷新加载数据----------|
refresh（）|刷新数据  注意这里 adapter用的集合和activity使用的不是一个集合
refresh（Collection<T> response） |重新设置数据 
remove(int position)  |删除掉某条数据 索引从0开始
clear（） |清除数据
loadItem(Collection<T> items)|加载更多数据
loadItem(T t) |增加一条数据




