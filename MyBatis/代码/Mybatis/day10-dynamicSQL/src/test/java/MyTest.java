import com.zimo.dao.BlogMapper;
import com.zimo.pojo.Blog;
import com.zimo.utils.IDUtils;
import com.zimo.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.*;


public class MyTest {
    @Test
    public void addBlogTest(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
            Blog blog = new Blog();
            blog.setId(IDUtils.getID());
            blog.setTitle("Mybatis");
            blog.setAuthor("狂神说");
            blog.setCreateTime(new Date());
            blog.setViews(9999);

            mapper.addBlog(blog);

            blog.setId(IDUtils.getID());
            blog.setTitle("Java");
            mapper.addBlog(blog);

            blog.setId(IDUtils.getID());
            blog.setTitle("Spring");
            mapper.addBlog(blog);

            blog.setId(IDUtils.getID());
            blog.setTitle("微服务");
            mapper.addBlog(blog);

            sqlSession.commit();
            sqlSession.close();
    }

    @Test
    public void getBlogByTitle(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

            Map<String,Object> map  = new HashMap<String, Object>();

            //传入标题
            map.put("title","Spring");
            List<Blog> spring = mapper.getBlogByTitle(map);

            //不传入标题
//            List<Blog> spring = mapper.getBlogByTitle(map);

            for (Blog blog : spring) {
                    System.out.println(blog);
            }


            sqlSession.commit();
            sqlSession.close();
    }


        @Test
        public void getBlog(){
                SqlSession sqlSession = MybatisUtils.getSqlSession();
                BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

                Map<String,Object> map  = new HashMap<String, Object>();


//                map.put("title","Spring");
//                map.put("author","狂神说");
                List<Blog> spring = mapper.getBlog(map);


                for (Blog blog : spring) {
                        System.out.println(blog);
                }


                sqlSession.commit();
                sqlSession.close();
        }


        @Test
        public void getBlog2(){
                SqlSession sqlSession = MybatisUtils.getSqlSession();
                BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

                Map<String,Object> map  = new HashMap<String, Object>();


                map.put("title","Spring");
                map.put("author","狂神说");
                List<Blog> spring = mapper.getBlog2(map);


                for (Blog blog : spring) {
                        System.out.println(blog);
                }


                sqlSession.commit();
                sqlSession.close();
        }

        @Test
        public void getForEach(){
                SqlSession sqlSession = MybatisUtils.getSqlSession();
                BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

                Map<String,Object> map  = new HashMap<String, Object>();

                List<String > list = new ArrayList<String>();
                list.add("Spring");
                list.add("微服务");

                map.put("list",list);

                List<Blog> spring = mapper.getForEach(map);


                for (Blog blog : spring) {
                        System.out.println(blog);
                }


                sqlSession.commit();
                sqlSession.close();
        }
}
