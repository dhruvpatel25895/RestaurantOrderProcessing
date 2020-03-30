package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// This is class from where application starts
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Views/ClientCustomer.fxml"));
        primaryStage.setTitle("SwinRestaurant");
        primaryStage.setScene(new Scene(root, 1310, 950));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
