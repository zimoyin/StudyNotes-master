import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * 窗体
 */

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //设置图标
//        String URL = "http://demo.sc.chinaz.com/Files/pic/icons/8015/w1.png";
        String URL = "/w1.png";
        primaryStage.getIcons().add(new Image(URL));

        primaryStage.setWidth(800);//设置窗口宽度
        primaryStage.setHeight(800);//设置窗口高度

//        primaryStage.setMaxWidth(900);//设置窗口最大宽度
//        primaryStage.setMaxHeight(900);//设置窗口最大宽度

        primaryStage.setResizable(true);//用户可以调整窗口大小

        //设置全屏
        primaryStage.setFullScreen(true);
        primaryStage.setScene(new Scene(new Group()));

        primaryStage.show();




        //监听窗口x位置
        primaryStage.xProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.print("窗体X轴发生变化   : ");
                System.out.println(oldValue+" ==变为==>  "+newValue);
            }
        });

        //监听窗口y位置
        primaryStage.yProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.print("窗体y轴发生变化   : ");
                System.out.println(oldValue+" ==变为==>  "+newValue);
            }
        });




        //监听窗体高度
        primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.print("窗体高度发生变化   : ");
                System.out.println(oldValue+" ==变为==>  "+newValue);
            }
        });

        //监听窗体宽度
        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.print("窗体宽度发生变化   : ");
                System.out.println(oldValue+" ==变为==>  "+newValue);
            }
        });
    }
}
