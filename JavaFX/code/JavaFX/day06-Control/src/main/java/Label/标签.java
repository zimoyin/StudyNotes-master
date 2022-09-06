package Label;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class 标签 extends Application {
    int i=0;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        //标签
        Label l = new Label();
        //设置位置
        l.setLayoutX(100);
        l.setLayoutY(100);
        //设置文本也可以在构造器设置
        l.setText("我是标签");
        //设置字体
        l.setFont(Font.font("楷体",20));
        //设置单击事件
        l.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                l.setText("老子被单击了");
                if (i>=1)  l.setText("老子被单击了 x"+i);
                i++;
            }
        });

        root.getChildren().add(l);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("标签");
        primaryStage.setHeight(800);
        primaryStage.setWidth(800);
        primaryStage.show();
    }





}
