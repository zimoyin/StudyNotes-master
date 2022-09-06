import com.zimo.dao.UserMapper;
import com.zimo.pojo.User;
import com.zimo.utils.MybatisUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;


public class MyTest {
    @Test
    public void test(){
        //获取sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        //创建map对象
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("startIndex",0);
        map.put("pageSize",2);

        //调用执行
        List<User> limit = mapper.getLimit(map);

        //打印结果
        for (User u:limit) {
            System.out.println(u);
        }

        sqlSession.close();
    }

    @Test
    public void test2(){
        //获取sqlSession对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        //RowBounds 实现
        RowBounds rowBounds = new RowBounds(1, 2);

        //通过java代码层面实现分页
        List<User> list = sqlSession.selectList("com.zimo.dao.UserMapper.getRowBounds",null,rowBounds);

        for (User user : list) {
            System.out.println(user);
        }

        sqlSession.close();
    }

    @Test
    public void logTest(){
        //注意导包不要导错
        //获取logger对象： 传入当前类的class，这样代表这个类输出的日志信息
        Logger logger = Logger.getLogger(MyTest.class);

        //logger有三个日志级别，info，debug，error。你可以根据你的情况来选择输出
        logger.info("info...");
        logger.debug("debug...");
        logger.error("error...");
    }
}
