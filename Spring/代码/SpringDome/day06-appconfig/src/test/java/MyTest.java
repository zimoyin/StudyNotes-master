import com.zimo.config.ZimoConfig;
import com.zimo.pojo.User;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyTest {
    @Test
    public void test(){
        //加载配置类
        AnnotationConfigApplicationContext config = new AnnotationConfigApplicationContext(ZimoConfig.class);
        User user = (User) config.getBean("user");
        User getUser = (User) config.getBean("getUser");
        System.out.println(getUser);
        System.out.println(user);


    }
}
