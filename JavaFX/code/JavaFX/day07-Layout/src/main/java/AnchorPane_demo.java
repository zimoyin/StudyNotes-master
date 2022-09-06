import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AnchorPane_demo extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        //创建锚点/绝对布局
        javafx.scene.layout.AnchorPane root = new javafx.scene.layout.AnchorPane();//根节点
        setBackground(root,"#ee00aa");//设置背景颜色
        javafx.scene.layout.AnchorPane an = new javafx.scene.layout.AnchorPane();//二级节点
        setBackground(an,"#00ee00");//设置背景颜色

        root.getChildren().add(an);//添加二级节点

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("标签");
        primaryStage.setHeight(800);
        primaryStage.setWidth(800);
        primaryStage.show();

        //放在这里是因为只有窗口被创建了才有宽和高
        //此时调整窗口大小an不会随着窗口调整而调整，所以我们需要一个监听来动态调整
        //这是一个静态方法，这里为了突出谁是调用者就这样写了
        root.setTopAnchor(an,0.0);//设置距离顶部的大小
        root.setBottomAnchor(an,root.getHeight()/2);//设置距离底部的大小
        root.setRightAnchor(an,root.getWidth()/2);//设置距离右部的大小
        root.setLeftAnchor(an,0.0);//设置距离左部的大小



        root.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                root.setRightAnchor(an,newValue.doubleValue()/2);//设置距离右部的大小
            }
        });
//        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                root.setRightAnchor(an,root.getWidth()/2);//设置距离右部的大小
//            }
//        });


        root.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                root.setRightAnchor(an,newValue.doubleValue()/2);//设置距离右部的大小
                System.out.println(newValue);
            }
        });
//        primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                //这里不用newValue是因为他是窗体的高，而我们要做的是要root的高,而且窗体是有边框的，所以窗体高不等于root的高。上面的一样
//                root.setRightAnchor(an,root.getHeight()/2);//设置距离右部的大小
//            }
//        });

    }

    //设置背景颜色
    javafx.scene.layout.AnchorPane setBackground(javafx.scene.layout.AnchorPane an, String c){
        an.setStyle("-fx-background-color: "+c);
        return an;
    }


}
