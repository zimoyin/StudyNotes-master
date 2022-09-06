import com.zimo.mapper.UserMapper;
import com.zimo.mapper.UserMapperImpl;
import com.zimo.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class MyTest {

    @Test
    public void test(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dao.xml");
        UserMapper userMapper = applicationContext.getBean("userMapper", UserMapper.class);

        List<User> userList = userMapper.getUserList();
        for (User user : userList) {
            System.out.println(user);
        }
    }
    
    @Test
    public void test2(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-dao.xml");
        UserMapper userMapper = applicationContext.getBean("userMapper", UserMapper.class);

        User user = new User(9, "uur", "pwdafad7a5");
        int add = userMapper.add(user);
        System.out.println(add);

    }

}
