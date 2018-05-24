package com.location.mvp.mvp_route_demo.modle.bean;

/**
 * 项目名称: MvpRoute
 * 类描述:
 * 创建人: 田晓龙
 * 创建时间: 2018/5/19 0019 22:37
 * 修改人:
 * 修改内容:
 * 修改时间:
 */


public class UserBean {

    /**
     * userId : 123
     * userName : 田晓龙
     * sex : 男
     * isDuck : false
     */

    private int userId;
    private String userName;
    private String sex;
    private boolean isDuck;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isIsDuck() {
        return isDuck;
    }

    public void setIsDuck(boolean isDuck) {
        this.isDuck = isDuck;
    }
}
