package zimo.qq.login;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

public class LoginFX extends Application {

    private String style1 = "-fx-background-color: #aaee0088;" +  /* 设置提示信息颜色*/
            "-fx-background-radius: 20;" +              /* 设置提示信息颜色*/
            "-fx-text-fill: #272727;" +                   /* 设置字体颜色*/
            "-fx-border-style: dashed ;" +              /*设置边框*/
            "-fx-border-color: #aa00EE;" +
            "-fx-border-radius: 25;" +
            "-fx-border-width: 0";

    private String style2 = "-fx-background-color: #3fd2f3;" +  /* 设置提示信息颜色*/
            "-fx-background-radius: 20;" +              /* 设置提示信息颜色*/
            "-fx-text-fill: #272727;" +                   /* 设置字体颜色*/
            "-fx-border-style: dashed ;" +              /*设置边框*/
            "-fx-border-color: #aa00EE;" +
            "-fx-border-radius: 25;" +
            "-fx-border-width: 2";

    private String style3 = "-fx-background-size:16px 16px;" +
            "-fx-background-repeat: no-repeat;" +
            "-fx-background-color:transparent;" +
            "-fx-background-position:center;";
    private long id = 0;
    private String password = "";
    boolean isSave = false;
    boolean isAuto = false;

    @Override
    public void start(Stage stage) throws Exception {


        GridPane root = new GridPane();

        //标签
        Label name = new Label("账号: ");
        Label psd = new Label("密码: ");
        //输入框
        TextField text_name = new TextField();
        PasswordField text_psd = new PasswordField();
        //头像
        ImageView imageView = new ImageView("http://bpic.588ku.com/element_origin_min_pic/16/10/29/2ac8e99273bc079e40a8dc079ca11b1f.jpg");
        //记住密码
        CheckBox save = new CheckBox("记住密码");
        //自动登录
        CheckBox autoLogin = new CheckBox("自动登录");
        //登录
        Button button = new Button("登录");


        //初始化值
        getSave();
        save.setSelected(isSave);
        autoLogin.setSelected(isAuto);
        text_name.setText(String.valueOf(id));
        text_psd.setText(password);
        if (id != 0) {
            imageView.setImage(new Image("http://q1.qlogo.cn/g?b=qq&nk=" + id + "&s=640"));
        }


        //样式设置
        name.setTextFill(Paint.valueOf("#31B5D0"));
        psd.setTextFill(Paint.valueOf("#31B5D0"));

        text_name.setFont(Font.font("楷体", 11));
        text_psd.setFont(Font.font("楷体", 11));
        text_name.setStyle(style1);
        text_psd.setStyle(style1);

        button.setPrefWidth(198);
        button.setPrefHeight(198 / 8);
        button.setStyle(style2);


        //添加控件
        root.add(imageView, 1, 0);
        root.add(name, 0, 1);
        root.add(psd, 0, 2);
        root.add(text_name, 1, 1);
        root.add(text_psd, 1, 2);
        root.add(button, 0, 5, 3, 5);
        root.add(save, 0, 3, 2, 3);
        root.add(autoLogin, 1, 3, 2, 3);

        //进行布局
        GridPane.setMargin(button, new Insets(0, 0, 0, 2));
        GridPane.setMargin(imageView, new Insets(0, 0, 0, 25));
        GridPane.setMargin(autoLogin, new Insets(0, 0, 0, 80));

        imageView.setFitHeight(50);
        imageView.setFitWidth(50);

        root.setHgap(10);
        root.setVgap(12);
        root.setAlignment(Pos.CENTER);

//        GridPane.setFillWidth(button,true);
        //调试网格
//        BooleanProperty booleanProperty = root.gridLinesVisibleProperty();
//        booleanProperty.set(true);

        //窗口
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("登录-QQ");
        stage.setWidth(446);
        stage.setHeight(337);
        stage.resizableProperty().set(false);
        stage.setOpacity(0.91);
        stage.setAlwaysOnTop(true);
        stage.getIcons().add(new Image("http://q1.qlogo.cn/g?b=qq&nk=2556608754&s=640"));
        stage.show();

        //获取账号动态更新头像
        text_name.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //从八位开始获取头像
                if (newValue.length() >= 8) {
                    imageView.setImage(new Image("http://q1.qlogo.cn/g?b=qq&nk=" + newValue + "&s=640"));
                }
            }
        });

        //登录事件
        button.setOnAction((bu) -> {
            try {
                id = Long.parseLong(text_name.getText());
                setSave();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            password = text_psd.getText();

            //呼吸灯动画
            FadeTransition fadeTransition = new FadeTransition();
            fadeTransition.setDuration(Duration.seconds(0.1));
            fadeTransition.setNode(button);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.play();
        });

        //记住密码
        save.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                isSave = newValue;
                try {
                    setSave();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //自动登录
        autoLogin.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                save.setSelected(true);
                isAuto = newValue;
                try {
                    setSave();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void setSave() throws IOException {
        //配置文件模板输入流
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("saveIDAndPassword.properties");
        Properties prop = new Properties();
        prop.load(new InputStreamReader(resourceAsStream)); //加载格式化后的流

        prop.setProperty("save", String.valueOf(isSave));
        prop.setProperty("save", String.valueOf(isAuto));
        prop.setProperty("save", String.valueOf(id));
        if (isSave) {
            prop.setProperty("psd", password);
        } else {
            prop.setProperty("psd", "");
        }
    }

    private void getSave() throws IOException {
        //配置文件模板输入流
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("saveIDAndPassword.properties");
        Properties prop = new Properties();
        prop.load(new InputStreamReader(resourceAsStream)); //加载格式化后的流

        this.isSave = Boolean.parseBoolean(prop.getProperty("save"));
        this.isAuto = Boolean.parseBoolean(prop.getProperty("auto"));
        this.id = Long.parseLong(prop.getProperty("id"));
        this.password = prop.getProperty("psd");
    }

    private void login(){

    }
}
