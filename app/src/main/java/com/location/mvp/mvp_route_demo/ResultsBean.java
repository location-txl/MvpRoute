package com.location.mvp.mvp_route_demo;

import com.google.gson.annotations.SerializedName;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/13 0013 22:19
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class ResultsBean extends BaseIBean<ResultsBean>{


    /**
     * result : {"name":"txl","age":12}
     */

    @SerializedName("result")
    private ResultBean resultX;

    public ResultBean getResultX() {
        return resultX;
    }

    public void setResultX(ResultBean resultX) {
        this.resultX = resultX;
    }

    public static class ResultBean {
        /**
         * name : txl
         * age : 12
         */

        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
