package com.zimo.dao;

import com.zimo.pojo.Teacher;

import java.util.List;

public interface TeacherMapper {

    //获取指定ID的老师下的所以学生
    Teacher getTeacher(int id);

}
