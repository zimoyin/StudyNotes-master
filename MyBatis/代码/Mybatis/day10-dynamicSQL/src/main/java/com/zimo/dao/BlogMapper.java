package com.zimo.dao;

import com.zimo.pojo.Blog;

import java.util.List;
import java.util.Map;

public interface BlogMapper {
    //添加记录
    int addBlog(Blog blog);

    //根据传入的标题查询记录，如果没有标题就查询全部
    List<Blog> getBlogByTitle (Map map);

    List<Blog> getBlog2(Map map);


    List<Blog> getBlog(Map<String, Object> map);

    List<Blog>  getForEach(Map<String, Object> map);
}
