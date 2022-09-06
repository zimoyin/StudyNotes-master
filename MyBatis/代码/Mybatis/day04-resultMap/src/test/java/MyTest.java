import com.zimo.dao.UserMapper;
import com.zimo.pojo.User;
import com.zimo.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class MyTest {

    @Test
    public void test(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
//        User user = mapper.getUserByID(4);

        User user = mapper.getUserMapByID(4);
        System.out.println(user.toString());


        sqlSession.close();
    }

}
