package com.zimo.dao;
import com.zimo.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

//mapper
public interface UserMapper {

        @Select("select id,name,pwd from user where id = #{id}")
        User getUserByID(@Param("id") int id);


        //插入语句：这里使用了三个参数的方式，也可以用一个参数（对象）来实现，参考下面的addUser2
        //因为使用了@Param("password")，所以在SQL里要写 #{password})
        @Insert("insert into mybatis.user (id,name,pwd) value (#{id},#{name},#{password})")
        int addUser(@Param("id") int id,@Param("name") String name,@Param("password")String password);

        //这里的(sql)参数名称要与(User)类的属性名称一致
        @Insert("insert into mybatis.user (id,name,pwd) value (#{id},#{name},#{pwd})")
        int addUser(User user);

        @Delete("delete from user where id =#{id}")
        int delete(int id);

        @Update("update mybatis.user set  name =#{name },pwd=#{pwd}   where id=#{id}")
        int updateUser(User user);



}
