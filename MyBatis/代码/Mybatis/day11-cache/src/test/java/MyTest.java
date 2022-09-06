import com.zimo.dao.BlogMapper;
import com.zimo.pojo.Blog;
import com.zimo.utils.IDUtils;
import com.zimo.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTest {

    //一级缓存
    @Test
    public void one(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        Map<String,Object> map  = new HashMap<String, Object>();
        map.put("title","Spring");


        List<Blog> spring = mapper.getBlogByTitle(map);

        //清理缓存
//        sqlSession.clearCache();

//        开启以下注释会添加一条记录，会更新缓存，所以spring==spring2 就会为false
//        Blog blog = new Blog();
//        blog.setId(IDUtils.getID());
//        blog.setTitle("Mybatis");
//        blog.setAuthor("狂神说2");
//        blog.setCreateTime(new Date());
//        blog.setViews(9999);
//
//        mapper.addBlog(blog);


        List<Blog> spring2 = mapper.getBlogByTitle(map);

        System.out.println(spring==spring2);



        sqlSession.commit();
        sqlSession.close();

    }

    //二级缓存
    @Test
    public void two(){
        Map<String,Object> map  = new HashMap<String, Object>();
        map.put("title","Spring");

        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        List<Blog> spring = mapper.getBlogByTitle(map);
        sqlSession.commit();
        sqlSession.close();

        SqlSession sqlSession2 = MybatisUtils.getSqlSession();
        BlogMapper mapper2 = sqlSession2.getMapper(BlogMapper.class);
        List<Blog> spring2 = mapper2.getBlogByTitle(map);
        System.out.println(spring2);
        sqlSession2.commit();
        sqlSession2.close();


        System.out.println(spring==spring2);
    }



}
