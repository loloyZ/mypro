package com.wei.cn.util;

public class ValidateUtil {
	/**
	 * 验证输入的数据是否为空
	 * @param data 输入数据
	 * @return 如果不是null返回true，否则返回false
	 */
    public static boolean validateEmpty(String data) {
    	if (data == null || "".equals(data)) {
    		return false ;
    	}
    	return true ;
    }
    /**
     * 进行数据的正则操作验证
     * @param data 要验证的数据
     * @param regex 要执行验证的正则表达式
     * @return 如果验证通过返回true，否则返回false
     */
    public static boolean validateRegex(String data,String regex) {
    	if (validateEmpty(data)) {    //判断数据是否为空
    		return data.matches(regex) ;
    	}
    	return false ;
    }

	/**
	 * 进行数据重复判断
	 * @param datea 数据A
	 * @param dateb 数据B
	 * @return 数据相同返回ture,否则返回false
	 */
    public static boolean validateSame(String datea,String dateb) {
    	if (validateEmpty(datea) && validateEmpty(dateb)) {
    		return datea.equalsIgnoreCase(dateb) ;
		}
		return false ;
	}

}
