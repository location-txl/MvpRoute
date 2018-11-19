package com.location.mvp.mvproutelibrary.utils;

import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author location
 * @Date 2018/11/19
 */

public class JsonUtils {
	private static final String GSON_NAME = "com.google.gson.Gson";
	private static final String FAST_JSON_NAME = "com.alibaba.fastjson.JSON";
	private static final String GSON_PARSER = "com.google.gson.JsonParser";


	public static String obtJson(Object data) {
		if (checkClass(GSON_NAME)) {
			return gsonToJson(data);
		} else if (checkClass(FAST_JSON_NAME)) {
			return fastToJson(data);
		}
		return "";
	}

	@Nullable
	public static <T> T obtObject(String data, Class<? extends T> clazz) {
		if (checkClass(GSON_NAME)) {
			return gsonToObject(data, clazz);
		} else if (checkClass(FAST_JSON_NAME)) {
			return fastToObject(data, clazz);
		}
		return null;
	}


	public static <T> List<T> obtArray(String data, Class<? extends T> clazz) {
		if (checkClass(GSON_NAME)) {
			return gsonToArray(data, clazz);
		} else if (checkClass(FAST_JSON_NAME)) {
			return fastToArray(data, clazz);
		}
		return null;
	}

	private static <T> List<T> fastToArray(String data, Class<? extends T> clazz) {
		try {
			Class<?> aClass = Class.forName(FAST_JSON_NAME);
			Method method = aClass.getMethod("parseArray", String.class, Class.class);
			return (List<T>) method.invoke(null, data, clazz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static <T> List<T> gsonToArray(String data, Class<? extends T> clazz) {
		try {
			Class<?> aClass = Class.forName(GSON_PARSER);
			Object o = aClass.getConstructor().newInstance();
			Method method = aClass.getMethod("parse", String.class);
			Object invoke =  method.invoke(o, data);
			Method getAsJsonArray = invoke.getClass().getMethod("getAsJsonArray");
			//jsonArray
			Object jsonArray =  getAsJsonArray.invoke(invoke);
			Class<?> jsonArrayClass = jsonArray.getClass();
			List<T> list = new ArrayList<>();
			int size = (int) jsonArrayClass.getMethod("size").invoke(jsonArray);
			for(int i=0;i<size;i++){
				Object jsonElement = jsonArrayClass.getMethod("get", int.class).invoke(jsonArray, i);
				list.add(gsonJsonElement(jsonElement,clazz));
			}

			return list;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static <T> T gsonJsonElement(Object element, Class<? extends T> clazz) {
		try {
			Class<?> aClass = Class.forName(GSON_NAME);
			Object o = aClass.getConstructor().newInstance();
			Class<?> elementclass = Class.forName("com.google.gson.JsonElement");
			Method method = aClass.getMethod("fromJson", elementclass, Class.class);
			return (T) method.invoke(o, element, clazz);
		} catch (ClassNotFoundException  e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static <T> T gsonToObject(String data, Class<? extends T> clazz) {

		try {
			Class gsonClass = Class.forName(GSON_NAME);
			Object gsonObject = gsonClass.getConstructor().newInstance();
			Method fromJson = gsonClass.getMethod("fromJson", String.class, Class.class);
			return (T) fromJson.invoke(gsonObject, data, clazz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static <T> T fastToObject(String data, Class<? extends T> clazz) {
		try {
			Class<?> aClass = Class.forName(FAST_JSON_NAME);
			Method method = aClass.getMethod("parseObject", String.class, Class.class);
			return (T) method.invoke(null, data, clazz);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String fastToJson(Object data) {
		try {
			Class<?> aClass = Class.forName(FAST_JSON_NAME);
			Method method = aClass.getMethod("toJSONString", Object.class);
			return (String) method.invoke(null, data);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return "";
	}

	private static String gsonToJson(Object data) {
		try {
			Class gsonclass = Class.forName(GSON_NAME);
			Object o = gsonclass.getConstructor().newInstance();
			Method toJson = gsonclass.getMethod("toJson", Object.class);
			return (String) toJson.invoke(o, data);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return "";
	}

	private static boolean checkClass(String className) {
		try {
			Class<?> gsonClass = Class.forName(className);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	/**
	 * 判断json串是否是对象
	 *
	 * @param json
	 * @return
	 * @throws RuntimeException 当不是合法的json串是抛出此异常
	 */
	public static boolean isJsonObject(String json) {
		try {
			Object o = new JSONTokener(json).nextValue();
			if (o instanceof JSONObject) {
				return true;
			}
			if (o instanceof JSONArray) {
				return false;
			} else {
				throw new RuntimeException("Check the json string format");
			}
		} catch (JSONException e) {
			throw new RuntimeException("Check the json string format");
		}
	}
}
