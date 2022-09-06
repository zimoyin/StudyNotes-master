package Text;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class 输入框 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        //输入框
        TextField text = addSelectedText(addTestInputEvents(getText()));
        root.getChildren().addAll(addEventHandlers(text));




        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("输入框");
        primaryStage.setHeight(800);
        primaryStage.setWidth(800);
        primaryStage.show();
    }


    //创建一个输入框
    TextField getText(){
        TextField text = new TextField();
        //给输入框添加文本
        text.setText("");
        //设置位置
        text.setLayoutX(100);
        text.setLayoutY(100);
        //设置提示信息
        text.setTooltip(getTip());
        //设置提示文本
        text.setPromptText("请输入7个字文本");
        //让输入框失去焦点
        text.setFocusTraversable(false);

        return text;
    }

    //创建一个提示信息
    Tooltip getTip(){
        Tooltip tip  = new Tooltip();
        //设置提示内容
        tip.setText("这是一个输入框");
        //设置字体
        tip.setFont(Font.font("楷体",12));
        //设置提示框的颜色
        tip.setStyle("-fx-background-color: #aaee0088;" +  /* 设置提示信息颜色*/
                "-fx-background-radius: 20;" +              /* 设置提示信息颜色*/
                "-fx-text-fill: #272727;"+                   /* 设置字体颜色*/
                "-fx-border-style: dashed ;" +              /*设置边框*/
                "-fx-border-color: #aa00EE;" +
                "-fx-border-radius: 25;" +
                "-fx-border-width: 2");
        return tip;
    }




    //输入框输入监听
    //监听输入框的输入的内容
    TextField addTestInputEvents(TextField text){
        text.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("文本框内容: "+newValue);
                //判断如果用户输入字符大于7个就让设置输入框内容为上一次的内容
                //禁止输入7个字符以上
                if (newValue.length()>7){
                    text.setText(oldValue);
                }
            }
        });
        return  text;
    }

    //输入框文本选择监听
    //监听输入框中被选择的内容是什么
    TextField addSelectedText(TextField text){
        text.selectedTextProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("文本框选择了: "+newValue);
            }
        });
        return  text;
    }


    //文本框事件处理
    //这里处理单击事件
    TextField addEventHandlers(TextField text){
        text.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("输入框被单击了");
            }
        });
        return  text;
    }

}
