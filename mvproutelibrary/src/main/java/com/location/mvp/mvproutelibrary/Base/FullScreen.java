package com.location.mvp.mvproutelibrary.base;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author location
 * @Date 2018/11/15
 * 标识 此注解后 Activity 默认全屏
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FullScreen {

}
