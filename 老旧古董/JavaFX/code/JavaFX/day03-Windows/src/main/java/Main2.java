import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 模式
 */

public class Main2 extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("风格和模态");
        //风格
//        primaryStage.initStyle(StageStyle.DECORATED);//具有纯白色背景和平台装饰的舞台。 默认
//        primaryStage.initStyle(StageStyle.UNDECORATED);//具有纯白色背景且没有装饰的舞台。
//        primaryStage.initStyle(StageStyle.TRANSPARENT);//具有透明背景且没有装饰的舞台。
//        primaryStage.initStyle(StageStyle.UTILITY);//具有纯白色背景和最少平台装饰的舞台。
        primaryStage.show();


        //模态
        Stage s = new Stage();
//        s.initModality(Modality.APPLICATION_MODAL);
//        s.initModality(Modality.NONE);

        s.initModality(Modality.WINDOW_MODAL);
        s.initOwner(primaryStage);//为这个窗口设置主窗口

        s.show();

        Stage s2 = new Stage();
        s2.setTitle("s2");
        s2.show();



    }
}
