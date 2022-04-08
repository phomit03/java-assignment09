package javafx_lab01;

        import javafx.application.Application;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;

public class Main extends Application {
    //khởi tạo 1 mảng có đích chung là class Main
    public static ObservableList<PhoneNumber> phonesList = FXCollections.observableArrayList();

    public static Stage rootStage;  //khai báo 1 stage

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("listUserPhone.fxml"));
        primaryStage.setTitle("Quản Lý Danh Bạ Điện Thoại");
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        //add sẵn 2 người
        phonesList.add(new PhoneNumber("XTharo", "0365600678"));
        phonesList.add(new PhoneNumber("David", "0123456789"));
        launch(args);
    }
}
