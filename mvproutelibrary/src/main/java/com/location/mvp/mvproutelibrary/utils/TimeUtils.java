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

import android.provider.Telephony;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;




public class TimeUtils {
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String TIME_FORMAT = "HH:mm:ss";

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static String hxDate(long misslas){
        return new SimpleDateFormat(DEFAULT_FORMAT).format(new Date(misslas));
    }

    public static String hxTime(long misslas){
        return new SimpleDateFormat(TIME_FORMAT).format(new Date(misslas));
    }

    /**
     * 得到昨天的时间
     * @return
     */
    public static String getYesterdayDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);
        return new SimpleDateFormat(DATE_FORMAT).format(calendar.getTime());
    }


    public static String getTodayDate(){
        return new SimpleDateFormat(DATE_FORMAT).format(new Date());
    }


}
