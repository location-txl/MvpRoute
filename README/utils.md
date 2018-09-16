#### Utils类



##### Sputils的使用

>  Sputils是对于sharedPreferences类的一个封装
>
> 支持常规的存储操作  还支持sharedPreferences不支持的类型  如实体类

 使用方式

1. 在App中初始化

   > ```
   > SpUtils.init(this);
   > ```

2. 存储数据

   调用putValue（）方法来存储  

3. 获取数据

      调用  getString（）  getBoolean()等等方法来获取数据

4.  存储实体类

   >  使用Sputils类可以存储 实体类  采用的是反射  Sputils会通过反射寻找是否存在gson包
   >
   > 如果存在  就会调用gson生成json串来存储
   >
   > 获取  会通过Gson重新解析为实体类来回调给客户端

5. 其余方法

      Sputils 还支持

```java
/**
 * 判断是否包含某个key值
 *
 * @param key
 * @return
 */
public boolean contains(String key) {
   return sharedPreferences.contains(key);
}

/**
 * 清除掉数据
 */
public void clearAll() {
   sharedPreferences.edit().clear().apply();
}

/**
 * 删除掉某个key值
 *
 * @param key
 */
public void remove(String key) {
   sharedPreferences.edit().remove(key).apply();
}
```

##### SpanUtils

>  SpanUtils  是一个可以设置TextView多种样式

![](https://s1.ax1x.com/2018/09/16/iZSiw9.gif)



> 使用  更多样式请查看源码

```
CharSequence build = SpanUtils.Builder(message)
      .setBorder(3, 5)
      .setTextSize(textsize)
      .build();
```