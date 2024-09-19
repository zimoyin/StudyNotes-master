import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.text.Font;
import javafx.stage.*;


import java.awt.*;
import java.awt.event.KeyEvent;


public class R extends Application {

    volatile boolean tag = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Button b = new Button("开始");
        b.setLayoutX(100);
        b.setLayoutY(80);
        Button b2 = new Button("结束");
        b2.setLayoutX(300);
        b2.setLayoutY(80);

        root.getChildren().addAll(css(b),css(b2));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setWidth(500);
        primaryStage.setHeight(250);
        primaryStage.setTitle("连点器 - 关闭 : 作者:子墨");
        primaryStage.show();




        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setTitle("连点器 - 开启: 作者:子墨");
                System.out.println("开始了");
                tag=true;
                start2();
            }
        });

        b2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setTitle("连点器 - 关闭 : 作者:子墨");
                tag=false;
            }
        });

        //开始键
        KeyCodeCombination kc2 = new KeyCodeCombination(KeyCode.S,KeyCodeCombination.CONTROL_DOWN);
        scene.getAccelerators().put(kc2, new Runnable() {
            @Override
            public void run() {
                b.fire();
            }
        });
        //结束键
        KeyCodeCombination kc = new KeyCodeCombination(KeyCode.E,KeyCodeCombination.CONTROL_DOWN);
        scene.getAccelerators().put(kc, new Runnable() {
            @Override
            public void run() {
                b2.fire();
            }
        });
    }

    //css 编写按钮样式 简单应用
    Button css(Button button){
        //设置字体
        button.setFont(Font.font("楷体",20));
        //css设置样式
        button.setStyle("-fx-background-color: #aaee00;" +  /* 设置按键背景颜色*/
                "-fx-background-radius: 20;" +              /* 设置按键圆角颜色*/
                "-fx-text-fill: #2a5caa;"+                   /* 设置字体颜色*/
                "-fx-border-style: dashed ;" +              /*设置边框*/
                "-fx-border-color: #aa00EE;" +
                "-fx-border-radius: 25;" +
                "-fx-border-width: 2"

        );

        return button;
    }

    void start2(){
        //新建一个线程防止主界面休眠
        new Thread(new Runnable() {
            @Override
            public void run() {
                Robot robot = null;
                try {
                    robot = new Robot();
                } catch (AWTException e) {
                    e.printStackTrace();
                }

                while (tag){
                    try {
                        //妹300ms点击一次
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 按下和释放鼠标左键，选定工程
                    robot.mousePress(KeyEvent.BUTTON1_MASK);
                    robot.mouseRelease(KeyEvent.BUTTON1_MASK);
                }
                System.out.println("结束了");
            }
        }).start();
    }

    @Override
    public void stop() throws Exception {
        tag=false;
    }


}
