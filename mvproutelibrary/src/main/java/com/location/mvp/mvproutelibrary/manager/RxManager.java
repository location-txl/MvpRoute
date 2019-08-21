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
package com.location.mvp.mvproutelibrary.manager;

import androidx.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 网络管理类  在activity关闭时 会结束网络线程
 */


public class RxManager {
    private final CompositeDisposable compositeDisposable;

    public RxManager() {
        compositeDisposable = new CompositeDisposable();
    }

    public void add(@NonNull Disposable disposable) {
        compositeDisposable.add(disposable);
    }
    public void clear(){
        compositeDisposable.clear();
    }
}
