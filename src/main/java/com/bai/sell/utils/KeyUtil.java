package com.bai.sell.utils;


import java.util.Random;

public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式:时间+随机数
     * 添加synchronized关键词防止重复
     * @return
     */
    public static synchronized String genUniqueKey(){
        Random random = new Random();
        //生成6位随机数
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
