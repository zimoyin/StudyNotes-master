import com.zimo.service.UserService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    
    @Test
    public void test(){
        ClassPathXmlApplicationContext c = new ClassPathXmlApplicationContext("beans.xml");
        //注意要用接口接受
        UserService userService = c.getBean("userService", UserService.class);
        userService.delete();
    }
}
