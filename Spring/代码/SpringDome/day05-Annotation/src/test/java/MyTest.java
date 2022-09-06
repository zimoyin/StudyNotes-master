import com.zimo.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {

    @Test
    public  void main() {
       ApplicationContext c = new ClassPathXmlApplicationContext("beans.xml");
        User user = (User) c.getBean("user");
        System.out.println(user.name);
    }
}
