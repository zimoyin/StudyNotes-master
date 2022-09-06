import com.zimo.dao.UserMapper;
import com.zimo.pojo.User;
import com.zimo.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class MyTest {

    @Test
    public void select(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        int i = mapper.addUser(7, "马月月", "azga45aa4541815gtfh");


        User userByID = mapper.getUserByID(7);
        System.out.println(userByID);



        sqlSession.commit();
        sqlSession.close();

    }
}
