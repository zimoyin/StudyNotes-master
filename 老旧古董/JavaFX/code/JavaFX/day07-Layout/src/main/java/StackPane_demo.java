import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StackPane_demo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane stackPane = new StackPane();


        stackPane.setStyle("-fx-background-color: #BBFFFF");

        //创建控件
        Button button0 = new Button("button0");
        stackPane.getChildren().add(button0);
        for (int i = 1; i <10; i++) {
            Button button = new Button("button"+i);
            stackPane.getChildren().add(button);
        }


        //设置对齐方式:居中
        stackPane.setAlignment(Pos.CENTER);
        //设置内边距
        stackPane.setPadding(new Insets(10));
        //设置对齐
        stackPane.setAlignment(Pos.BOTTOM_LEFT);
        //设置控件外边距(这里是静态方法)
        stackPane.setMargin(button0, new Insets(100));


        //遍历节点
        stackPane.getChildren().forEach(item -> {
            System.out.println(((Button)item).getText());
        });

        Scene scene = new Scene(stackPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        primaryStage.show();
    }
}
