package com.chestnut.lidong.constant;


/**
 * Created by AshZheng on 2016/9/19.
 */
public class AppConstant {

    public static final String SHARED_PREFERENCES_NAME = "LiDongData";

    //发生错误时的返回值
    public static final int ERROR = -1;

    //服务器端URL
//  public static final String SERVER_URL = "http://192.168.191.1:8080/LiDongAppServer";
//  public static final String SERVER_URL = "http://60.205.200.110:8080/LiDongAppServer";
//    public static final String SERVER_URL = "http://172.21.3.20:8080/LiDongAppServer";
      public static final String SERVER_URL = "http://192.168.191.1:8080/LiDongAppServer";
//    public static final String SERVER_URL = "http://121.42.142.215:8080/LiDongAppServer";

    public static final String SERVER_URL_LOGIN = SERVER_URL+"/user_login";
    public static final String SERVER_URL_REGISTER = SERVER_URL+"/user_register";
    public static final String SERVER_URL_GET_USER_RUNDATA = SERVER_URL+"/running_data_get";
    public static final String SERVER_URL_POST_USER_RUNDATA = SERVER_URL+"/running_data_post";


    //登录
    public static final int LOGIN = 1;
    public static final int LOGIN_SUCCESS = 11;
    public static final int LOGIN_WRONG_PASSWORD = 12;
    public static final int LOGIN_NO_USER = 13;


    //注册
    public static final int REGISTER = 2;
    public static final int REGISTER_SUCCESS = 21;
    public static final int REGISTER_DIFFERENT_PASSWORD = 22;
    public static final int REGISTER_SAME_USERNAME = 23;
    public static final int REGISTER_ILLEGAL_USERNAME = 24;
    public static final int REGISTER_ILLEGAL_PASSWORD = 25;
    public static final int REGISTER_UNKNOW_WRONG = 200;
}
