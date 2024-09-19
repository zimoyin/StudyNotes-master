package Button;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * 创建按键
 * 设置样式
 * 设置点击事件
 */
public class ControlButton extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        //设置scene
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);


        //添加控件
//        root.getChildren().addAll(button1(),css());
        root.getChildren().addAll(addClickEvents(button1()),addClickEvents(css()));//添加有单击事件的按钮

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

    //css 编写按钮样式 简单应用
    Button css(){
        Button button = new Button("css");
        //设置位置
        button.setLayoutY(400);
        button.setLayoutX(400);
        //设置大小
        button.setPrefHeight(200);
        button.setPrefWidth(200);
        //设置字体
        button.setFont(Font.font("楷体",20));
        //css设置样式
        button.setStyle("-fx-background-color: #aaee00;" +  /* 设置按键背景颜色*/
                "-fx-background-radius: 20;" +              /* 设置按键圆角颜色*/
                "-fx-text-fill: #00eea0;"+                   /* 设置字体颜色*/
                "-fx-border-style: dashed ;" +              /*设置边框*/
                "-fx-border-color: #aa00EE;" +
                "-fx-border-radius: 25;" +
                "-fx-border-width: 2"

        );

        return button;
    }

    //为按钮添加单击事件
    Button addClickEvents(Button b){
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //获取被单击的按钮对象
                Button b = (Button) event.getSource();
                //获取这个按键的文本
                String text = b.getText();
                System.out.println(text+"被单击了");
            }
        });
        return b;
    }

}
