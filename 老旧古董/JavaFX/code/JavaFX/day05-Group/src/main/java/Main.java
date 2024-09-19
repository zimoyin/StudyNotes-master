import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application {
    int i = 0;
    @Override
    public void start(Stage primaryStage) throws Exception {
        //按钮
        Button button = new Button();
        button.setText("点我");//添加文本
        //确定位置，如果不知道默认在左上角
        button.setLayoutX(170);
        button.setLayoutY(170);


        //组件节点
        Group root = new Group();

        root.getChildren().add(button);//添加控件
//        root.getChildren().addAll(button);//批量添加控件

        root.setOpacity(1);//设置透明度

        root.setAutoSizeChildren(true);//设置是否给组件设置默认值，默认为true

//        root.getChildren().remove(0);//清理控件
//        root.getChildren().clear();//清理所有控件

        Object[] objects = root.getChildren().toArray(); //返回控件数组

        //设置监听，监听控件添加
        root.getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> c) {
                System.out.println("当前控件数量： "+c.getList().size());
            }
        });

        //场景
        Scene scene = new Scene(root);
        //窗体
        primaryStage.setScene(scene);


        //设置单击事件
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button button1 = new Button("按钮" + i);
                button1.setLayoutY(i+50);
                root.getChildren().add(button1);
                i++;
            }
        });
        //设置窗体属性
        primaryStage.setTitle("嘿嘿");
        primaryStage.setWidth(400);
        primaryStage.setHeight(400);
        primaryStage.show();
    }
}
