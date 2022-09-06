package com.zimo.mapper;

import com.zimo.pojo.User;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

//我们多了一个实现类来实现UserMapper里的方法，这样更符合面向对象。
// 这个实现类去操作数据库，去做以前mybatis做的事情
public class UserMapperImpl  implements  UserMapper{
    //在原来我们所以的操作，都使用sqlSession来执行，现在动用SqlSessionTemplate
    private SqlSessionTemplate sqlSession;

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }


    @Override
    public List<User> getUserList() {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.getUserList();
    }

    @Override
    public int add(User user) {
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.add(user);
        int a=1/0;
        return 0;
    }
}
