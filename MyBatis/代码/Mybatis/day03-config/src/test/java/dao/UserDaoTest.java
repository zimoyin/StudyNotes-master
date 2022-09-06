package dao;


import com.zimo.dao.UserMapper;
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

            //获取SqlSession 对象
            sqlSession = MybatisUtils.getSqlSession();
            System.out.println(sqlSession);
            //执行SQL

            //方式一:getMapper
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> userList = mapper.getUserList();

            //方式二（不建议使用,方法佼老）：
//        List<User> userList = sqlSession.selectList("com.zimo.dao.UserDao.getUserList");

            //输出结果
            for (User u:userList) {
                System.out.println(u);
            }
            //关闭sqlsession
            sqlSession.close();

    }


    @Test
    public void getUserID(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        //方式一:getMapper
        //获得接口
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //调用接口方法获取ID为1的记录
        User user = mapper.getUserByID(1);

        System.out.println(user);

        sqlSession.close();
    }

    @Test
    public void addUser(){
        //获取sqlSession 对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        //获得接口
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //new 一个用户
        User user =new User(7,"风铃","233");
        //调用接口方法，插入数据
        int i = mapper.addUser(user);
        if(i>0) System.out.println("插入成功");

        //提交事务
        //如果不提交的话，数据插入不进去
//        sqlSession.commit();
        //关闭流
        sqlSession.close();

        //打印表中所以的记录
        test();
    }


    @Test
    public  void update(){
        //获取sqlSession 对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        //获得接口
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //new 一个用户
        User user =new User();
        user.setId(1);
        user.setPwd("9554545");
        //调用接口方法，修改数据
        int i = mapper.updateUser(user);
        if(i>0) System.out.println("修改成功");

        //提交事务
        //如果不提交的话，数据插入不进去
        sqlSession.commit();
        //关闭流
        sqlSession.close();

        test();
    }

    @Test
    public  void delete(){
        //获取sqlSession 对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        //获得接口
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        //调用接口方法，删除数据
        int i = mapper.deleteUser(5);
        if(i>0) System.out.println("删除成功");

        //提交事务
        //如果不提交的话，数据插入不进去
        sqlSession.commit();
        //关闭流
        sqlSession.close();

        test();
    }


}
