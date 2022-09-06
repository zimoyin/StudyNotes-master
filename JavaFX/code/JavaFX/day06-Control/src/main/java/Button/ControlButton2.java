package Button;

import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * 鼠标多击和监听键盘按键
 */
public class ControlButton2 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        //设置scene
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);


        //添加控件
//        root.getChildren().addAll(addClickEvents(button1()));//多击按钮
        root.getChildren().addAll(addKeyEvents(button1()));//添加按键监听事件按钮

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


    //为按钮添加多击事件
    Button addClickEvents(Button b){
//       b.addEventFilter();//父向子类传值
//       b.addEventHandler(); //子向父类传值
        /*
        MouseEvent 鼠标事件
        MOUSE_CLICKED 点击事件
        注意导包
         */
        b.addEventHandler(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                System.out.println(((Button)event.getSource()).getText()+"被单击了");
                if (event.getClickCount()==2) System.out.println(((Button)event.getSource()).getText()+"被双击了");
                if (event.getClickCount()==3) System.out.println(((Button)event.getSource()).getText()+"被三击了");
            }
        });
        return b;
    }

    //为按钮添加多击事件,只能左键，右键无效
    Button addLeftClickEvents(Button b){
        b.addEventHandler(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if ((event.getClickCount()==2) && (event.getButton().name()).equals(MouseButton.PRIMARY.name())){
                    System.out.println("按钮被左键双击");
                }
            }
        });
        return b;
    }

    //添加按键监听
    Button addKeyEvents(Button b){

        //按键按下
        b.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println(event.getCode().getName());
                if (event.getCode().getName().equals(KeyCode.S.getName())){
                    System.out.println("S 被按下");
                }
            }
        });


        //按键释放
        b.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().getName().equals(KeyCode.S.getName())){
                    System.out.println("S 被释放");
                }
            }
        });
        return b;
    }


}
