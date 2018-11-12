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
package com.location.mvp.mvproutelibrary.http;

import io.reactivex.Observable;


/**
 *   刷新token接口
 *   如果需要刷新token 开发者实现此接口
 */

public interface IRefreshToken {

	/**
	 * 执行刷新token
	 * 如果需要在刷新完成后  做一些操作
	 * 可以使用doOnNext操作符
	 * 在里面做保存操作
	 * @return
	 */
	Observable refreshToken();

	/**
	 * token是否过期
	 * @param code 服务器返回的code值
	 * @param errorMsg 服务器返回的msg值
	 * @return
	 */
	boolean isTokenException(int code,String errorMsg);
}
