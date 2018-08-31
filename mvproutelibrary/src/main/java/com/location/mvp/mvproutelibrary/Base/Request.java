package com.location.mvp.mvproutelibrary.Base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 项目:MvpRoute
 *
 * @author：location time：2018/8/18 0018 12:59
 * description：
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Request {
	  String EXERA_RESULT = "result";

	 String EXERA_REQUEST = "request";
	int request();

	int[] result() default {-1};
}
