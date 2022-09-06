import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MyScreen extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Screen primary = Screen.getPrimary(); //获取主屏幕
        Rectangle2D bounds = primary.getBounds();//获取全部的范围屏幕。
        System.out.println("屏幕最大XY  :"+bounds.getMaxX()+" × "+ bounds.getMaxY());
        System.out.println("屏幕最大宽高 :"+bounds.getWidth()+" × "+ bounds.getHeight());

        Rectangle2D visualBounds = primary.getVisualBounds();//获取可视化范围的屏幕。
        System.out.println("可视屏幕的最大XY  :"+visualBounds.getMaxX()+" × "+ visualBounds.getMaxY());
        System.out.println("可视屏幕的最大宽高 :"+visualBounds.getWidth()+" × "+ visualBounds.getHeight());

        double dpi = primary.getDpi();//获取此的分辨率（每英寸点数)
        System.out.println("DPI ：" + dpi);

        Platform.exit();//退出程序
    }
}
