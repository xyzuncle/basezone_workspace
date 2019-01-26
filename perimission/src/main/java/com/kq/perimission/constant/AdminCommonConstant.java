package com.kq.perimission.constant;


/**
 * @author yerui uncle
 * @create 2018年3月27日15:28:59
 * @deprecated 用于描述权限的常亮
  */
public class AdminCommonConstant {
    public final static int ROOT = -1;
    public final static int DEFAULT_GROUP_TYPE = 0;
    /**
     * 权限关联类型
     */
    public final static String AUTHORITY_TYPE_GROUP = "group";

    /**
     * 游客获取的前端权限
     */
    public final static String GUEST_GROUP_TYPE = "outside";

    /**
     * 游客获取的前端权限的ID
     */
    public final static String GUEST_GROUP_ID = "guest";

    /**
     * 权限资源类型
     */
    public final static String RESOURCE_TYPE_MENU = "MENU";
    public final static String RESOURCE_TYPE_BTN = "button";
    public final static String RESOURCE_TYPE_SERVICE = "USER_SERVICE";
    public final static String RESOURCE_TYPE_ELEMENT= "ELEMENT";

    public final static String RESOURCE_REQUEST_METHOD_GET = "GET";
    public final static String RESOURCE_REQUEST_METHOD_PUT = "PUT";
    public final static String RESOURCE_REQUEST_METHOD_DELETE = "DELETE";
    public final static String RESOURCE_REQUEST_METHOD_POST = "POST";


    public final static String RESOURCE_ACTION_VISIT = "访问";

    public final static String BOOLEAN_NUMBER_FALSE = "0";

    public final static String BOOLEAN_NUMBER_TRUE = "1";

}
