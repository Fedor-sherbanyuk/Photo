package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nu.pattern.OpenCV;
import org.opencv.core.Core;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    public static void main(String[] args) {
//        OpenCV.loadShared(); Если до 11 Java
        OpenCV.loadLocally();
        System.out.println("Verion: " + Core.VERSION);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, NullPointerException {
        URL url = new File("src/main/java/fxml/FXMLDocument.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}