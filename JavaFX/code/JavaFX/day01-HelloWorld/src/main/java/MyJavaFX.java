import javafx.application.Application;
import javafx.stage.Stage;

public class MyJavaFX extends Application {

    @Override
    public void init() throws Exception {
        System.out.println("Application实现类init()方法执行，执行线程为: "+Thread.currentThread().getName());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.show();//显示窗口
        primaryStage.setTitle("HelloWorld");//设置标题
        System.out.println("Application实现类start方法执行，执行线程为: "+Thread.currentThread().getName());
    }


    @Override
    public void stop() throws Exception {
        System.out.println("Application实现类 stop()方法执行，执行线程为: "+Thread.currentThread().getName());
    }
}
