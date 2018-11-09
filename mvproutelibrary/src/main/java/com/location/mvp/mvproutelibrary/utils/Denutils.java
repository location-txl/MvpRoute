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
package com.location.mvp.mvproutelibrary.utils;


import android.content.Context;



public class Denutils {

	private Denutils() {
		throw new UnsupportedOperationException("cannot be instantiated");
	}


	public static int dptopx(Context context, float dp) {
		float density = context.getResources().getDisplayMetrics().density;
		return (int) (dp * density + 0.5f);
	}
	public static int pxtodp(Context context, float dp) {
		float density = context.getResources().getDisplayMetrics().density;
		return (int) (dp/density+0.5f);
	}
	public static int sptopx(Context context, float sp) {
		float density = context.getResources().getDisplayMetrics().scaledDensity;

		return (int) (sp*density+0.5f);
	}

	public static  int pxtosp(Context context,float px){
		float density = context.getResources().getDisplayMetrics().scaledDensity;

		return (int) (px/density+0.5f);
	}






}
