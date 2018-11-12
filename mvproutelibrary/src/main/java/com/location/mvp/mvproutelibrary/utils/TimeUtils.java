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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * time时间工具类
 */
public class TimeUtils {

	public static final String FORMAT_TIME = "HH:mm:ss";

	public static final String FORMAT_DATE = "yyyy-MM-dd";

	public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

	public static String hxFormat(long misslas,String format) {
		return new SimpleDateFormat(format).format(new Date(misslas));
	}
	public static String hxSecondsFormat(long misslas,String format) {
		return new SimpleDateFormat(format).format(new Date(misslas*1000));
	}

	public static String hxDate(long misslas) {
		return new SimpleDateFormat(FORMAT_DATE).format(new Date(misslas));
	}
	public static String hxSecondsDate(long misslas) {
		return new SimpleDateFormat(FORMAT_DATE).format(new Date(misslas*1000));
	}
	public static String hxTime(long misslas) {
		return new SimpleDateFormat(FORMAT_TIME).format(new Date(misslas));
	}
	public static String hxSecondsTime(long misslas) {
		return new SimpleDateFormat(FORMAT_TIME).format(new Date(misslas*1000));
	}
	public static String hxMills(long misslas){
		return new SimpleDateFormat(FORMAT_DATE_TIME).format(new Date(misslas));
	}
	public static String hxSecondsMills(long misslas){
		return new SimpleDateFormat(FORMAT_DATE_TIME).format(new Date(misslas*1000));
	}


	public  static long hxStringDate(String time){
		try {
			return new SimpleDateFormat(FORMAT_DATE).parse(time).getTime();
		} catch (ParseException e) {
			return -1;
		}
	}
	public  static long hxStringTime(String time){
		try {
			return new SimpleDateFormat(FORMAT_TIME).parse(time).getTime();
		} catch (ParseException e) {
			return -1;
		}
	}

	public  static long hxStringMills(String time){
		try {
			return new SimpleDateFormat(FORMAT_DATE_TIME).parse(time).getTime();
		} catch (ParseException e) {
			return -1;
		}
	}
	public  static long hxString(String time,String format){
		try {
			return new SimpleDateFormat(format).parse(time).getTime();
		} catch (ParseException e) {
			return -1;
		}
	}

	/**
	 * 得到昨天的日期
	 *
	 * @return
	 */
	public static String getYesterdayDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		return new SimpleDateFormat(FORMAT_DATE).format(calendar.getTime());
	}


	/**
	 * 得到今天的日期
	 * @return
	 */
	public static String getTodayDate() {
		return new SimpleDateFormat(FORMAT_DATE).format(new Date());
	}


}
