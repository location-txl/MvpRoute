/*
 * Copyright 2018 location
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.location.mvp.mvproutelibrary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.TextureView;

import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * SharedPreferences存储工具类  简化了sp存储方式
 * 并且支持实体类的保存
 */
public final  class SpUtils {

	/**
	 * SharedPreferences 对象
	 */
	private static SharedPreferences sharedPreferences;
	/**
	 * 上下文
	 */
	protected   Context context;
	/**
	 * 存储SharedPreferences对象  每个key值对应一个
	 */
	private static ArrayMap<String, SharedPreferences> sps = new ArrayMap<>();
	/**
	 * 单例模式
	 */
	private static volatile SpUtils spUtils;

	/**
	 * 查找String的默认字符串
	 */
	private static final String DEFAULT_STRING = "";
	/**
	 * 查找Boolean的默认值
	 */
	private static final boolean DEFAULT_BOOLEAN = false;
	/**
	 * 查找Int类型的默认值
	 */
	private static final int DEFAULT_INT = -1;
	/**
	 * 查找Set集合的默认值
	 */
	private static final Set<String> DEFAULT_SET = new HashSet<>();
	/**
	 * 查找long类型的默认值
	 */
	private static final long DEFAULT_LONG = -1;
	/**
	 * 查找float的默认值
	 */
	private static final float DEFAULT_FLOAT = -1f;


	private SharedPreferences.Editor editor;

	/**
	 * 构造私有化
	 *
	 * @param context
	 */
	private SpUtils(Context context) {
		this.context = context.getApplicationContext();
		/**
		 * 默认的key值为app名字
		 * 没有获取到名字 获取包名
		 */
		sharedPreferences =this.context.getSharedPreferences(getAppName(context), Context.MODE_PRIVATE);
		/**
		 * 存储到集合
		 */
		sps.put(getAppName(this.context), sharedPreferences);
	}

	/**
	 * 在多进程中  Application 会初始化两次
	 * 初始化方法  在Application中初始化 防止内存泄露
	 *
	 * @param context
	 */
	public static void init(Context context) {
		if(spUtils==null){
			synchronized (SpUtils.class){
				if(spUtils==null){
					spUtils = new SpUtils(context);
				}
			}
		}
	}

	public static SpUtils getInstance(String keys) {
		/**
		 * 如果上下文为空 没有初始化
		 * 抛出异常
		 * throw {@link NullPointerException}
		 */
		if(spUtils==null){
			synchronized (SpanUtils.class){
				if (spUtils == null) throw new NullPointerException("you may Application init");
			}
		}


		/**
		 * 判断当前key是否为空 为空则第一次加载  实例化
		 */
		if (sps.get(keys) == null) {
			sharedPreferences = spUtils.context.getSharedPreferences(keys, Context.MODE_PRIVATE);
			sps.put(keys, sharedPreferences);
		} else {
			//反之获取sharedPreferences对象
			sharedPreferences = sps.get(keys);
		}
		return spUtils;
	}

	public static SpUtils getInstance() {
		if (spUtils == null) throw new NullPointerException("you may Application init");
		sharedPreferences = sps.get(getAppName(spUtils.context));
		return spUtils;
	}



	@SuppressLint("CommitPrefEdits")
	private void checkEditor() {
		if (editor == null) {
			editor = sharedPreferences.edit();
		}
	}




	public void putArray(String key, Object object) {
		String tojson = JsonUtils.obtJson(object);
		if (TextUtils.isEmpty(tojson)) {
			return;
		}
		sharedPreferences.edit().putString(key, tojson).apply();
	}

	/**
	 *
	 * 存储数据
	 * 存储Object类型
	 * 方法内会检查项目是否存在Gson包
	 * 如果存在就会使用Gson toJson方法转化成字符串存储
	 *
	 * @param key
	 * @param value
	 */
	public SpUtils putValue(String key, Object value) {
		checkEditor();
		if (value instanceof String) {
			editor.putBoolean(key, (Boolean) value);
		} else if (value instanceof Boolean) {
			editor.putBoolean(key, (Boolean) value);
		} else if (value instanceof Integer) {
			editor.putInt(key, (Integer) value);
		} else if (value instanceof Long) {
			editor.putLong(key, (Long) value);
		} else if (value instanceof Float) {
			editor.putFloat(key, (Float) value);
		} else {
			if (filterSystem(value.getClass())) {
				String tojson = JsonUtils.obtJson(value);
				if (TextUtils.isEmpty(tojson)) {
					return this;
				}
				editor.putString(key, tojson);
			}
		}
		return this;
	}




	public void putValue(Object object) {
		putValue(object.getClass().getSimpleName(), object);
	}

	private boolean filterSystem(Class<?> clazz) {
		String name = clazz.getCanonicalName();
		if (name.startsWith("android")
				|| name.startsWith("java")
				|| name.startsWith("javax")
				|| name.startsWith("kotlin")) {
			return false;
		}
		return true;
	}



	/**
	 * 下方都是获取值
	 *
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return sharedPreferences.getString(key, DEFAULT_STRING);
	}

	public boolean getBoolean(String key) {
		return sharedPreferences.getBoolean(key, DEFAULT_BOOLEAN);
	}

	public int getInt(String key) {
		return sharedPreferences.getInt(key, DEFAULT_INT);
	}

	public Set<String> getStringSet(String key) {
		return sharedPreferences.getStringSet(key, DEFAULT_SET);
	}

	public long getLong(String key) {
		return sharedPreferences.getLong(key, DEFAULT_LONG);
	}

	public float getFloat(String key) {
		return sharedPreferences.getFloat(key, DEFAULT_FLOAT);
	}


	public <T> T getObject(String key, Class<? extends T> clazz) {
		String str = sharedPreferences.getString(key, DEFAULT_STRING);
		if (TextUtils.isEmpty(str)) {
			return null;
		}
		return JsonUtils.obtObject(key, clazz);
	}


	public <T> T getObject(Class<? extends T> clazz) {
		return getObject(clazz.getSimpleName(), clazz);
	}


	public <T> List<T> getArray(String key, Class<? extends T> clazz) {
		String str = sharedPreferences.getString(key, DEFAULT_STRING);
		if (TextUtils.isEmpty(str)) {
			return null;
		}
		return JsonUtils.obtArray(str, clazz);
	}

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
	public SpUtils clearAll() {
		checkEditor();
		editor.clear();
		return this;
	}

	/**
	 * 删除掉某个key值
	 *
	 * @param key
	 */
	public SpUtils remove(String key) {
		checkEditor();
		editor.remove(key);
		return this;
	}


	/**
	 * 获取所有存储的键值对
	 *
	 * @return
	 */
	public Map<String, ?> getAll() {
		return sharedPreferences.getAll();
	}


	private static String getAppName(Context context) {
		PackageManager packageManager = context.getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (PackageManager.NameNotFoundException e) {
			return context.getPackageName();
		}
	}


	public void commit() {
         if(editor!=null){
         	editor.apply();
         	editor = null;
		}
	}

}
