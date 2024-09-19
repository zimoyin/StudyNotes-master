package Text;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class 密码框 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        //密码框
        PasswordField psd = getPsd(); //创建密码框的方法
        psd = addPsdInputEvents(psd);//添加输入事件
        psd = addSelectedText(psd);//添加选择事件
        psd = addEventHandlers(psd);//添加单击事件
        root.getChildren().addAll(psd);




        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("密码框");
        primaryStage.setHeight(800);
        primaryStage.setWidth(800);
        primaryStage.show();
    }

    //创建一个密码框
    PasswordField getPsd(){
        PasswordField psd = new PasswordField();
        //给密码框添加文本
        psd.setText("");
        //设置位置
        psd.setLayoutX(100);
        psd.setLayoutY(100);
        //设置提示信息
        psd.setTooltip(getTip());
        //设置提示文本
        psd.setPromptText("请输入密码");
        //让输入框失去焦点
        psd.setFocusTraversable(false);

        return psd;
    }

    //创建一个提示信息
    Tooltip getTip(){
        Tooltip tip  = new Tooltip();
        //设置提示内容
        tip.setText("限制7个字符");
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




    //密码框输入监听
    //监听密码框的输入的内容
    PasswordField addPsdInputEvents(PasswordField psd){
        psd.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("密码框内容: "+newValue);
                //判断如果用户输入字符大于7个就让设置输入框内容为上一次的内容
                //禁止输入7个字符以上
                if (newValue.length()>7){
                    psd.setText(oldValue);
                }
            }
        });
        return  psd;
    }

    //密码框文本选择监听
    //监听输密码框中被选择的内容是什么
    PasswordField addSelectedText(PasswordField psd){
        psd.selectedTextProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("密码框选择了: "+newValue);
            }
        });
        return  psd;
    }


    //密码框事件处理
    //这里处理单击事件
    PasswordField addEventHandlers(PasswordField psd){
        psd.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("密码框被单击了");
            }
        });
        return  psd;
    }

}
