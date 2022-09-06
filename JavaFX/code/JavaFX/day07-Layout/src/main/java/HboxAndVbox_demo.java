import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class HboxAndVbox_demo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Vbot与之相同
        HBox hBox = new HBox();
        hBox.setStyle("-fx-background-color: #BBFFFF");



        Button button0 = new Button("button0");
        hBox.getChildren().add(button0);
        //创建控件
        for (int i = 1; i <10; i++) {
            Button button = new Button("button"+i);
            hBox.getChildren().add(button);
        }
        //设置对齐方式:居中
        hBox.setAlignment(Pos.CENTER);
        //设置内边距
        hBox.setPadding(new Insets(10));
        //设置间距
        hBox.setSpacing(10);
        //设置控件外边距(这里是静态方法)
        HBox.setMargin(button0,new Insets(100));
//        hBox.setMargin(button0,new Insets(100));


        Scene scene = new Scene(hBox);
        primaryStage.setScene(scene);
        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        primaryStage.show();
    }
}
