package com.location.mvp.mvproutelibrary.base;

import android.graphics.Color;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create：2018/11/15 22:00
 * <p>
 * 此注解默认为透明导航栏
 * {@link #isTranslucent()} 这个可以设置是否是半透明  默认不是半透明
 * {@link #color()}      这个是导航栏颜色  默认为透明颜色
 * {@link #paddingTop()} 设置透明状态栏后 内容是否显示在状态栏之下 默认显示在状态栏之下
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface StatusBar {
	//是否半透明
	boolean isTranslucent() default false;

	//状态栏颜色
	int color() default Color.TRANSPARENT;

	// 设置透明状态栏后 内容是否显示在状态栏之下
	boolean paddingTop() default true;

}
