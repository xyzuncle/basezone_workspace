package com.kq.common.constants;

/**
 * Created by ace on 2017/8/29.
 */
public class CommonConstants {
    public final static String RESOURCE_TYPE_MENU = "menu";
    public final static String RESOURCE_TYPE_BTN = "button";
    public static final Integer EX_TOKEN_ERROR_CODE = 40101;
    //token过期
    public static final Integer TOKEN_EXPIRED = 40102;
    //没有查询权限
    public static final Integer QUERY_PERIMISSION = 40103;
    //内外网状态
    public static final Integer NETWORK_PERIMISSION = 40105;
    //session失效，必须让前端重新登录
    public static final Integer INVALID_SESSION = 40106;

    // 用户token异常
    public static final Integer EX_USER_INVALID_CODE = 40104;
    // 客户端token异常
    public static final Integer EX_CLIENT_INVALID_CODE = 40131;
    public static final Integer EX_CLIENT_FORBIDDEN_CODE = 40331;
    public static final Integer EX_OTHER_CODE = 500;
    //网关错误统一名称
    public static final Integer ZUUL_ERROR = 500;


    public static final String CONTEXT_KEY_USER_ID = "currentUserId";
    public static final String CONTEXT_KEY_USERNAME = "currentUserName";
    public static final String CONTEXT_KEY_USER_NAME = "currentUser";
    public static final String CONTEXT_KEY_USER_TOKEN = "currentUserToken";
    public static final String JWT_KEY_USER_ID = "userId";
    public static final String JWT_KEY_NAME = "name";
    public static final String JWT_KEY_USERTYPE = "userType";
    public static final String JWT_KEY_PERIMISSION = "currentPerimission";
    public static final String JWT_KEY_SYSTEM_TYPE = "sType";
    public static final String REDIS_PERIMISSION_PRE = "zero_perm:";

    public static final String AREA_OTHER = "未知";

    public static final Integer EXPIRES_TIME = 60;
}
