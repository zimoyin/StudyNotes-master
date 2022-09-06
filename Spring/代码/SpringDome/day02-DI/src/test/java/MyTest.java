import com.zimo.Student;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

//SET注入
public class MyTest {
    public static void main(String[] args) {
        ApplicationContext app = new ClassPathXmlApplicationContext("beans.xml");
        Student student = (Student)app.getBean("student");
        System.out.println(student.toString());
    }

    /**
     * C/P命名注入
     */
    @Test
    public void test1(){
        ApplicationContext app = new ClassPathXmlApplicationContext("beans2.xml");
        Student student = (Student)app.getBean("student");
        System.out.println(student.getName());
//        System.out.println(student.toString());
    }
}
