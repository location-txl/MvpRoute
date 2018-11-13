package com.location.mvp.mvproutelibrary.http;

import com.location.mvp.mvproutelibrary.Base.BaseProgressObserver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *time：2018/11/13 13:32
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Progress {
}
