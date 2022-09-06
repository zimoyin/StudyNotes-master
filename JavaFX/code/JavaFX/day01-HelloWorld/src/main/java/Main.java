import javafx.application.Application;
import javafx.stage.Stage;

/**
 * 这是我的第一个程序
 */

public class Main  {

    public static void main(String[] args) {
        System.out.println("Main方法执行，执行线程为: "+Thread.currentThread().getName());
        //运行
        MyJavaFX.launch(MyJavaFX.class,args);
    }
}
