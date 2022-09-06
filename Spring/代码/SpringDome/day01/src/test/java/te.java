import com.zimo.HelloSpring;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class te {
    public static void main(String[] args) {
        //有参构造注入
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        HelloSpring zi = (HelloSpring)context.getBean("hello");  //bean name="hello"
        System.out.println(zi.toString());
    }
}
