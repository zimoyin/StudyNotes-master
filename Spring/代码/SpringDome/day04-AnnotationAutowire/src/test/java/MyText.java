import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import zimo.User;

public class MyText {
    @Test
    public  void main() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        //getBean("user", com.hdu.autowire.User.class)这样写后代码就不用强转了
        User user = context.getBean("user", zimo.User.class);
        System.out.println(user);

    }
}
