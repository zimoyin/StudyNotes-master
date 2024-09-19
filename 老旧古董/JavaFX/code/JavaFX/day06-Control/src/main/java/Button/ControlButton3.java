package Button;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * 设置快捷键
 */
public class ControlButton3 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        //设置scene
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        Button b1 =addClickEvents(button1());//初始化一个带有事件的按钮



        //添加控件
        root.getChildren().addAll(b1);

        KeyCombination kc1 = new KeyCodeCombination(KeyCode.C,KeyCombination.CONTROL_DOWN);
        Mnemonic mnemonic1 = new Mnemonic(b1,kc1);
        scene.addMnemonic(mnemonic1);

        KeyCodeCombination kc2 = new KeyCodeCombination(KeyCode.W, KeyCodeCombination.CONTROL_DOWN);
        scene.getAccelerators().put(kc2, new Runnable() {

            @Override
            public void run() {
                System.out.println("事件");
                b1.fire();
            }
        });



        //设置窗体
        primaryStage.setTitle("按钮控件");
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.show();
    }

    //定义一个按钮
    Button button1 (){
        Button button = new Button("按钮1");
        //设置位置
        button.setLayoutY(100);
        button.setLayoutX(100);
        //设置大小
        button.setPrefHeight(200);
        button.setPrefWidth(200);
        //设置字体
        button.setFont(Font.font("楷体",20));
        //设置背景，可以查看类API进一步定制
        //红、绿、蓝、透明度  #00 00 00 00
        BackgroundFill fill = new BackgroundFill(Paint.valueOf("#ef5b9c"),new CornerRadii(25),new Insets(5));//颜色，圆角，偏移量(按钮离边框的距离 上下左右同一指定10)
        Background background = new Background(fill);
        button.setBackground(background);
        //设置边框
        BorderStroke borderStroke = new BorderStroke(Paint.valueOf("#009ad6"),BorderStrokeStyle.DASHED, new CornerRadii(30),
                new BorderWidths(2),new Insets(0));//颜色，类型，圆角，宽度，[偏移量: 边框和按钮的距离]
        Border border = new Border(borderStroke);
        button.setBorder(border);
        return button;
    }

    //为按钮添加单击事件
    Button addClickEvents(Button b){
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("执行");
            }
        });
        return b;
    }




}
