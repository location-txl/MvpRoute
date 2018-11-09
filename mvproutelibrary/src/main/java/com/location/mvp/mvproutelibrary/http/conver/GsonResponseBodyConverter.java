/*
 * Copyright (C) 2015 Square, Inc.
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

package com.location.mvp.mvproutelibrary.http.conver;

import android.text.TextUtils;

import com.google.gson.TypeAdapter;
import com.location.mvp.mvproutelibrary.http.IBaseBean;
import com.location.mvp.mvproutelibrary.error.ExceptionHandle;
import com.location.mvp.mvproutelibrary.http.RetrofitClient;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, Object> {

    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public Object convert(ResponseBody value) throws IOException {
        try {
            IBaseBean response = (IBaseBean) adapter.fromJson(value.charStream());
            if (!response.isOk()) {
                String msg = (TextUtils.isEmpty(response.getErrorMsg()) ? RetrofitClient.getInstance().getErrorResponse() == null ? "not errorMsg" : RetrofitClient.getInstance().getErrorResponse().getErrorMsg(response.getStatusCode()) : response.getErrorMsg());
                throw new ExceptionHandle.ServerException(response.getStatusCode(), msg);
            } else if (response.isOk()) {
                if(response.getData()!=null){
                    return response.getData();
                }else{
                    throw new NullPointerException("data is null");
                }
            }
        } finally {
            value.close();
        }
        return null;
    }
}
