package com.location.mvp.mvproutelibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 项目:趣租部落
 * author：田晓龙
 * time：2018/5/24 20:30
 * description：
 */

public class SpUtils {

    private static SharedPreferences sharedPreferences;
    private static Context context;
    private static Map<String, SharedPreferences> sps = new HashMap<>();
    private static SpUtils spUtils;

    private static final String DEFAULT_STRING = "";
    private static final boolean DEFAULT_BOOLEAN = false;
    private static final int DEFAULT_INT = -1;
    private static final Set<String> DEFAULT_SET = new HashSet<>();
    private static final long DEFAULT_LONG = -1;
    private static final float DEFAULT_FLOAT = -1f;

    private SpUtils(Context context) {
        SpUtils.context = context;
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        sps.put(context.getPackageName(), sharedPreferences);
    }

    public static void init(Context context) {
        spUtils = new SpUtils(context);
    }

    public static SpUtils getInstance(String keys) {
        if (context == null) throw new RuntimeException("you may Application init");
        if (sps.get(keys) == null) {
            sharedPreferences = context.getSharedPreferences(keys, Context.MODE_PRIVATE);
            sps.put(keys, sharedPreferences);
        } else {
            sharedPreferences = sps.get(keys);
        }
        return spUtils;
    }

    public void putValue(String key, String value) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public void putValue(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public void putValue(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public void putValue(String key, long value) {
        sharedPreferences.edit().putLong(key, value).apply();
    }

    public void putValue(String key, float value) {
        sharedPreferences.edit().putFloat(key, value).apply();
    }

    public void putValue(String key, Set<String> value) {
        sharedPreferences.edit().putStringSet(key, value).apply();
    }


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

    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    public void clearAll() {
        sharedPreferences.edit().clear();
    }

    public void remove(String key) {
        sharedPreferences.edit().remove(key);
    }

    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

}
