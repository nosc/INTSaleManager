package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.xml.bind.JAXBException;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        String a = System.getProperty("os.name");
        System.out.println(a);

        Parent root = FXMLLoader.load(getClass().getResource("forms/start.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) throws JAXBException {
        launch(args);
    }


}
