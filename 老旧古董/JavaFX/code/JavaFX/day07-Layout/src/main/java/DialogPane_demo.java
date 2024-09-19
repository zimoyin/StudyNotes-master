import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DialogPane_demo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {


        AnchorPane root = new AnchorPane();
        Button button = new Button("点我...");
        button.setStyle("-fx-background-color: #0eece8");

        root.getChildren().add(button);
        AnchorPane.setTopAnchor(button,300.0);
        AnchorPane.setLeftAnchor(button,300.0);


        button.setOnAction((bu)->{
            //对话框布局，需要一个窗口
            DialogPane dialogPane = new DialogPane();//有默认宽高

            dialogPane.setHeaderText("头部文字");
            dialogPane.setContentText("ContentText");

            //功能按钮
            dialogPane.getButtonTypes().add(ButtonType.APPLY);
            dialogPane.getButtonTypes().add(ButtonType.CLOSE);

            Button node = (Button) dialogPane.lookupButton(ButtonType.APPLY);

            node.setOnAction((b) -> {
                System.out.println("完成");
            });
            Scene scene = new Scene(dialogPane);
            //窗口
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("我是对话框");
            //设置主框框
            stage.initOwner(primaryStage);
            //窗口类型
            stage.initStyle(StageStyle.UNIFIED);
            //设置模态
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setWidth(600);
            stage.setHeight(450);
            stage.show();
        });


        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        primaryStage.show();
    }
}
