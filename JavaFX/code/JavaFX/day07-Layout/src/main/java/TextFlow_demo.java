import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class TextFlow_demo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        TextFlow textFlow = new TextFlow();


        Text text = new Text("你好呀");
        Text text2 = new Text("这里是");
        Text text3 = new Text("textField");

        //设置颜色
        text.setFill(Paint.valueOf("#AAAAAA"));
        //设置字体并加粗 字体，宽度，字号
        text.setFont(Font.font("楷体", FontWeight.BOLD,32));


        textFlow.getChildren().addAll(text,text2,text3,new Text("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));


        //文本对齐方式
        textFlow.setTextAlignment(TextAlignment.CENTER);
        //行间距
        textFlow.setLineSpacing(20);

        AnchorPane root = new AnchorPane();
        root.getChildren().add(textFlow);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        primaryStage.show();


        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                textFlow.setPrefWidth(root.getWidth());
            }
        });
    }
}