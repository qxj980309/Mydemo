package com.example.demo.mock.common.Constants;

public class Constants {
    private Constants(){

    }

    public static final String INTERFACE_CACHE_DATA = "cache-data";

    public static final String INTERFACE_IMPORT_DATA = "import-data";

    public static final Integer INTERFACE_SUCCESS_STATUS = 1;

    public static final Integer INTERFACE_WARN_STATUS = 2;

    public static final Integer INTERFACE_FAIL_STATUS = 3;

    public static final String SOCKET_SERVER_OPEN = "1";

    public static final String SOCKET_SERVER_CLOSE = "0";

    /*
    * 从请求中取值
    * */
    public static final String DYNAMIC_RULE_01 = "01";

    /*
    *自增
    * */
    public static final String DYNAMIC_RULE_02 = "02";

    /*
    * UUID
    * */
    public static final String DYNAMIC_RULE_03 = "03";

    /*
     * 指定位数自增
     * */
    public static final String DYNAMIC_RULE_04 = "04";

    /*
    * 拼接-自增
    * */
    public static final String DYNAMIC_RULE_05 = "05";

    /*
    * 报文格式
    * */
    public static final String DATA_TYPE_XML = "01";

    public static final String DATA_TYPE_JSON = "02";

    /*
    * 请求报文预处理
    * */
    public static final String PRETREATMENT_CUT = "01";

    public static final String STRUCTURE_JSONOBJECT ="01";

    public static final String STRUCTURE_JSONARRAY ="02";

    /*
    * 报文单位 :1M
    * */
    public static final int ONE_M = 1024 * 1024;

    /*
    * 数据格式类型
    * */
    public static final String A = "A";

    public static final String N = "n";

    public static final String S = "s";

    public static final String AN = "AN";

    public static final String AS = "AS";

    public static final String ANS = "ANS";

    /*
    * 必输
    * */
    public static final String Y = "Y";

    /*
    * 变长
    * */
    public static final String UNCERTAINLENGTH = "..";

    /*
    * 特殊字符开头
    * */
    public static final String DATABASE_PREFIX = "#";
    /*
     * MAC规范适用范围
     * */
    public static final String MAC_APPLICATION_SCOPE_REQUEST = "01";
    public static final String MAC_APPLICATION_SCOPE_RESPONSE = "02";

}
