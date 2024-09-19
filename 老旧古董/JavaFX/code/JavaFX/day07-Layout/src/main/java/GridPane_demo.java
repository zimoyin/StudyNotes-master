import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class GridPane_demo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #BBFFFF");

        //创建控件
        Button button0 = new Button("button0");
        gridPane.getChildren().add(button0);
        for (int i = 1; i <10; i++) {
            Button button = new Button("button"+i);
            //添加控件(有多种这里只演示一种) 第i列第0行
            gridPane.add(button,i,0);
        }


//        //设置对齐方式:居中
//        gridPane.setAlignment(Pos.CENTER);
//        //设置内边距
//        gridPane.setPadding(new Insets(10));
//        //设置对齐
//        gridPane.setAlignment(Pos.CENTER);
//        //水平间距
//        gridPane.setHgap(10);
//        //垂直间距
//        gridPane.setVgap(10);
//        //设置控件外边距(这里是静态方法)
//        GridPane.setMargin(button0,new Insets(100));

        //单独设置行列间距，会覆盖前面的间距
        //设置第一列间距
        gridPane.getColumnConstraints().add(new ColumnConstraints(100));
        //设置第一行间距
        gridPane.getRowConstraints().add(new RowConstraints(100));

        //设置第二列间距
        gridPane.getColumnConstraints().add(new ColumnConstraints(100));
        //设置第二行间距
        gridPane.getRowConstraints().add(new RowConstraints(100));
        //...............

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        primaryStage.show();
    }
}
