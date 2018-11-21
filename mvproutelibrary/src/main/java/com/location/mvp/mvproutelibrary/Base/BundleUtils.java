package com.location.mvp.mvproutelibrary.base;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

/**
 * @author location
 * @Date 2018/11/21
 */

public class BundleUtils {
	void setBundleField(Object parasitifer, Bundle bundle) {
		Field[] declaredFields = parasitifer.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			if (field.isAnnotationPresent(RouteField.class)) {
				//  过滤静态成员属性和常量成员属性
				if (Modifier.isFinal(field.getModifiers())
						|| Modifier.isStatic(field.getModifiers())
						) {
					continue;
				}
				Type genericType = field.getGenericType();
				if (genericType instanceof Class<?>) {
					Class<?> clazz = (Class<?>) genericType;
					String name = clazz.getCanonicalName();
					//过滤系统类
					if (name.startsWith("android")
							|| name.startsWith("java")
							|| name.startsWith("javax")
							|| name.startsWith("kotlin")) {
						continue;
					}
				}
				field.setAccessible(true);
				RouteField routeField = field.getAnnotation(RouteField.class);
				/*
				  获取key值  如果注解value值为null则获取成员属性标识符
				 */
				String key = TextUtils.isEmpty(routeField.value()) ? field.getName() : routeField.value();
				Object o = bundle.get(key);
				//如果value值为null 过滤
				if (o == null) {
					continue;
				}
				//如果 成员属性类型和bundle获取到的类型一样  直接返回过去
				if (field.getType().isAssignableFrom(o.getClass())) {
					try {
						field.set(parasitifer, o);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					continue;
				}
				//如果是String 使用json转成Object
				if (o instanceof String) {
					Object ove = toObject((String) o, genericType);
					try {
						field.set(parasitifer, ove);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private static boolean checkedClass(String pageName) {
		try {
			Class.forName(pageName);
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	private <T> T toObject(String data, Type type) {
		if (checkedClass(RouteManager.GSON_NAME)) {
			return new Gson().fromJson(data, type);
		} else if (checkedClass(RouteManager.FAST_JSON_NAME)) {
			return JSON.parseObject(data, type);
		} else {
			throw new NullPointerException("you must import gson or FastJson");
		}
	}

	public   static void putObject(Bundle bundle,String key,Object data){
		String content;
		if(checkedClass(RouteManager.GSON_NAME)){
			content = new Gson().toJson(data);
		}else if(checkedClass(RouteManager.FAST_JSON_NAME)){
			content = JSON.toJSONString(data);
		}else{
			throw new NullPointerException("you must import gson or FastJson");
		}
		bundle.putString(key, content);
	}
}
