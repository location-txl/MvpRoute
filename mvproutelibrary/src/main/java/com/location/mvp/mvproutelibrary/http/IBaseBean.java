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

/**
 * 基础实体类接口
 * @param <T>
 */

public interface IBaseBean<T> {

	/**
	 * 返回你想要的数据源
	 * @return
	 */
	T getData();

	/**
	 * 是否请求成功
	 * @return
	 */
	boolean isOk();

	/**
	 * 错误原因
	 * @return
	 */
	String getErrorMsg();

	/**
	 * 返回状态码
	 * @return
	 */
	int   getStatusCode();
}
