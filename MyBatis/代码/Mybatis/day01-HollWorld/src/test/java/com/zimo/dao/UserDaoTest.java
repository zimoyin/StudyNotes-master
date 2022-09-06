package com.zimo.dao;

import com.zimo.pojo.User;
import com.zimo.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserDaoTest {

    @Test
    public void test(){
        //获取SqlSession 对象
        SqlSession sqlSession = null;

        try {
            //获取SqlSession 对象
            sqlSession = MybatisUtils.getSqlSession();
            //执行SQL

            //方式一:getMapper
            UserDao mapper = sqlSession.getMapper(UserDao.class);
            List<User> userList = mapper.getUserList();

            //方式二（不建议使用,方法佼老）：
//        List<User> userList = sqlSession.selectList("com.zimo.dao.UserDao.getUserList");

            //输出结果
            for (User u:userList) {
                System.out.println(u);
            }
        }catch (Exception e){

        }finally {
            //关闭sqlsession
            sqlSession.close();
        }






    }
}
