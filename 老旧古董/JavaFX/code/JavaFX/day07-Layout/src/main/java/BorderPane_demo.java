import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BorderPane_demo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane a1 = new AnchorPane();
        a1.setStyle("-fx-background-color: #BBFFFF");
        a1.setPrefWidth(100);
        a1.setPrefHeight(100);

        AnchorPane a2 = new AnchorPane();
        a2.setStyle("-fx-background-color: #B000FF");
        a2.setPrefWidth(100);
        a2.setPrefHeight(100);

        AnchorPane a3 = new AnchorPane();
        a3.setStyle("-fx-background-color: #24ec95");
        a3.setPrefWidth(100);
        a3.setPrefHeight(100);

        AnchorPane a4 = new AnchorPane();
        a4.setStyle("-fx-background-color: #9e0808");
        a4.setPrefWidth(100);
        a4.setPrefHeight(100);

        AnchorPane a5 = new AnchorPane();
        a5.setStyle("-fx-background-color: #08eaf1");
        a5.setPrefWidth(100);
        a5.setPrefHeight(100);

        //方位布局(边界布局)
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(a1);
        borderPane.setRight(a2);
        borderPane.setTop(a3);
        borderPane.setBottom(a4);
        borderPane.setCenter(a5);

        //内边距
        borderPane.setPadding(new Insets(10));
        //外边距
        BorderPane.setMargin(a1,new Insets(10));
        //对齐方式
        BorderPane.setAlignment(a1, Pos.CENTER);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
