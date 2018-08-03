package com.vdaoyun.systemapi.common.utils;

import java.util.regex.Pattern;

/**
 * 
 * @Package com.vdaoyun.systemapi.common.utils
 *  
 * @ClassName: ValidationUtils
 *  
 * @Description: 正则匹配
 *  
 * @author DaPeng (fengzq@vdaoyun.com)
 *  
 * @date 2018年8月3日 上午11:01:22
 *
 */
public class ValidationUtils {
	
    public static final String REGEX_USERACCOUNT = "";

    public static final String REGEX_PHONENUMBER = "";
    
    public static final String REGEX_MOBILE = "^1[\\\\d]{10}";

    public static final String REGEX_URL = "^[a-zA-z]+://(//w+(-//w+)*)(//.(//w+(-//w+)*))*(//?//S*)?$";

    public static final String REGEX_PASSWORD = "";

    public static final String REGEX_EMAIL = "/w+([-+.]/w+)*@/w+([-.]/w+)*/./w+([-.]/w+)*";

    /** 身份证 */
    public static final String REGEX_IDCARD = "^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$";
    
    public boolean isUrl(String str) {
        return match(REGEX_URL, str);
    }
    
    /**
     * 
     * @Title: isMobile
     *  
     * @Description: 	是否为手机号码
     *  
     * @param str		需要验证的字符串
     * @return boolean	true/false
     */
    public static boolean isMobile(String str) {
    	return match(REGEX_MOBILE, str);
    }
    
    public boolean isPhoneNumber(String str) {
        return match(REGEX_PHONENUMBER, str);
    }
    
    public boolean isEmail(String str) {
        return match(REGEX_EMAIL, str);
    }
    
    private static boolean match(String regex, String str) {
        return Pattern.compile(regex).matcher(str).matches();
    }

}
