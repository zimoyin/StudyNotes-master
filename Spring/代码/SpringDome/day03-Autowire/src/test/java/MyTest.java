import com.zimo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
自动装配
 */
public class MyTest {
    @Test
     public void testMethodAutowire() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
           	 //getBean("user", com.hdu.autowire.User.class)这样写后代码就不用强转了
                User user = context.getBean("user", com.zimo.User.class);
                user.getCat().shout();
                 user.getDog().shout();
    }
}
