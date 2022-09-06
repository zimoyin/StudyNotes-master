package com.zimo.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

//SqlSessionFactory ==> SqlSession
public class MybatisUtils {
    private static String resource;
    private static InputStream inputStream;
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            //获取 SqlSessionFactory 对象
            resource ="mybatis-config.xml";
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //获取 SqlSession 对象
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }

}
