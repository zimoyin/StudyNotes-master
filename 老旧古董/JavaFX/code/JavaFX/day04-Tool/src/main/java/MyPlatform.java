import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.net.URL;

public class MyPlatform extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        //和start是一个线程 JavaFX Application Thread
        //将来在某些未指定的时间在JavaFX Application Thread上运行指定的Runnable。
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });
        System.out.println(Thread.currentThread().getName());

        //查询平台是否支持特定的条件功能。
        boolean supported = Platform.isSupported(ConditionalFeature.SCENE3D);
        System.out.println("当前电脑是否支持3d: "+supported);

        Platform.exit();//退出

    }
}
