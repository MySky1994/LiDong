package com.chestnut.lidong.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by AshZheng on 2016/9/9.
 */

// 专门用于存放项目中的Bean，首先新建一个UserInfo


  /*首先在UserInfo类上添加@DatabaseTable(tableName = "user_info")，标明这是数据库中的一张表，标明为user_info
    @DatabaseField(generatedId = true) ，generatedId 表示id为主键且自动生成
    然后分别在属性上添加@DatabaseField(columnName = "username") ，columnName的值为该字段在数据中的列名
  */

@DatabaseTable(tableName = "user_info")
public class UserInfo extends BaseEntity {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "username")
    private String username; //用户名
    @DatabaseField(columnName = "password")
    private String password;  //用户密码
    @DatabaseField(columnName = "niname")
    private String niname;   //昵称
    @DatabaseField(columnName = "user_id")
    private String userId;  //用户的id
    @DatabaseField(columnName = "date_of_birth")
    private String dateOfBirth;    //生日
    @DatabaseField(columnName = "sex")
    private String sex;   //性别
    @DatabaseField(columnName = "user_ico")
    private String userIco;  //用户的头像
    @DatabaseField(columnName = "user_sign")
    private String userSign;   //用户的个性签名
    @DatabaseField(columnName = "zan")
    private int zan;    //赞的次数
    @DatabaseField(columnName = "user_phone")
    private String userPhone;   //用户的手机号码


// 编写各个属性的set()和get()方法，实现数据的封装
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserIco() {
        return userIco;
    }

    public void setUserIco(String userIco) {
        this.userIco = userIco;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }


    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", niname='" + niname + '\'' +
                ", userId='" + userId + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", sex='" + sex + '\'' +
                ", userIco='" + userIco + '\'' +
                ", userSign='" + userSign + '\'' +
                ", zan=" + zan +
                ", userPhone='" + userPhone + '\'' +
                '}';
    }

    public String getNiname() {
        return niname;
    }

    public void setNiname(String niname) {
        this.niname = niname;
    }

    public UserInfo() {

    }

    public UserInfo(String username, String password, String niname, String userId, String dateOfBirth, String sex, String userIco, String userSign, int zan, String userPhone) {

        this.username = username;
        this.password = password;
        this.niname = niname;
        this.userId = userId;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.userIco = userIco;
        this.userSign = userSign;
        this.zan = zan;
        this.userPhone = userPhone;
    }
}
