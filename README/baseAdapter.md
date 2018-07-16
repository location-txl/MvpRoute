
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




