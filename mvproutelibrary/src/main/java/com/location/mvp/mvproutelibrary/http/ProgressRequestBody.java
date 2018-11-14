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

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;

import com.location.mvp.mvproutelibrary.Base.BaseProgressObserver;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 *
 *  带进度的RequestBody
 *  用于上传文件
 * 构造方法里面需要  {@link RequestBody,BaseProgressObserver}
 *
 */

public class ProgressRequestBody extends RequestBody {
	private final RequestBody mDelegate;
	private BufferedSink mBufferedSink;
	private BaseProgressObserver listener;

	public ProgressRequestBody(RequestBody delegate, BaseProgressObserver uploadResponse) {
		this.mDelegate = delegate;
		this.listener = uploadResponse;
	}

	@Override
	public MediaType contentType() {
		return mDelegate.contentType();
	}

	@Override
	public long contentLength() {
		try {
			return mDelegate.contentLength();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public void writeTo(@NonNull BufferedSink sink) throws IOException {
		if (sink instanceof Buffer) {
			// Log Interceptor
			mDelegate.writeTo(sink);
			return;
		}
		if (mBufferedSink == null) {
			mBufferedSink = Okio.buffer(new CountingSink(sink));
		}
		mDelegate.writeTo(mBufferedSink);
		mBufferedSink.flush();
	}

	protected final class CountingSink extends ForwardingSink {
		private long bytesWritten = 0;
		private long contentLength = 0L;
		private long lastTime = 0L;

		public CountingSink(Sink delegate) {
			super(delegate);
		}

		@Override
		public void write(Buffer source, final long byteCount) throws IOException {
			super.write(source, byteCount);
			if (contentLength == 0) {
				//获得contentLength的值，后续不再调用
				contentLength = contentLength();
				if(listener!=null){
					listener.setContentLength(contentLength);
				}
			}
			bytesWritten += byteCount;
			long cuTime = SystemClock.elapsedRealtime();
			if ((listener != null && cuTime - lastTime >= 10) || bytesWritten == contentLength) {
				final long current = bytesWritten;
                                if(current<contentLength){
									listener.onProgress(bytesWritten, contentLength);
								}
			}
			lastTime = cuTime;
		}
	}

}
