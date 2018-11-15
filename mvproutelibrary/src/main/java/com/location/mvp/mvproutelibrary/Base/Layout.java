package com.location.mvp.mvproutelibrary.base;

import android.support.annotation.LayoutRes;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create：2018/11/15 17:50
 * {@link #value()}  layout布局id
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Layout {
	@LayoutRes int value();
}
