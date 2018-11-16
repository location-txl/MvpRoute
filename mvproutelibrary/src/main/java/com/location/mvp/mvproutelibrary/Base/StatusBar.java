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
 * 此注解为设置沉浸式状态栏
 * {@link #tranStatus()}  是否为沉浸式状态栏  默认为true
 * 设置为true后  {@link #color()} 此属性将失效
 * {@link #color()}      这个是导航栏颜色  默认为-1 具体逻辑{@link BaseActivity#setStatusBarColorForAnnotation(int)}
 *
 * 将会获取app的主题颜色
 *
 * {@link #paddingTop()} 设置透明状态栏后 内容是否显示在状态栏之下 默认显示在状态栏之下
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface StatusBar {


	//状态栏颜色
	int color() default -1;

	// 设置透明状态栏后 内容是否显示在状态栏之下
	boolean paddingTop() default true;

	//是否沉浸式状态栏
	boolean tranStatus() default true;


}
