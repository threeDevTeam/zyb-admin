package com.hthyaq.zybadmin;

import org.apache.commons.lang.WordUtils;

public class T3 {
    public static void main(String[] args) throws ClassNotFoundException {
        String classNameStr="supervise";
        //首字母大写
        classNameStr= WordUtils.capitalize(classNameStr);
        //加上model
        classNameStr="com.hthyaq.zybadmin.model.excelModel."+classNameStr+"Model";
        System.out.println(classNameStr);
        //根据字符串获取Class类型
        Class clazz=Class.forName(classNameStr);
        System.out.println(clazz);
    }
}
