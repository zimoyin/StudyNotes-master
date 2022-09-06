import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class FlowPane_demo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FlowPane flowPane = new FlowPane();
        flowPane.setStyle("-fx-background-color: #BBFFFF");

        //添加控件
        Button button0 = new Button("button0");
        flowPane.getChildren().add(button0);

        for (int i = 1; i < 10; i++) {
            flowPane.getChildren().add(new Button("button"+i));
        }

        //设置内边距
        flowPane.setPadding(new Insets(10));
        //设置外边距
        FlowPane.setMargin(button0,new Insets(10));
        //设置对齐
        flowPane.setAlignment(Pos.CENTER);
        //水平间距
        flowPane.setHgap(10);
        //垂直间距
        flowPane.setVgap(10);
        //设置布局方向: 水平
        flowPane.setOrientation(Orientation.VERTICAL);


        Scene scene = new Scene(flowPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        primaryStage.show();

    }
}
