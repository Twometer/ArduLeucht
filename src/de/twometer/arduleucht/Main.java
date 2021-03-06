package de.twometer.arduleucht;

import de.twometer.arduleucht.gui.util.Theme;
import de.twometer.arduleucht.io.ResourceLoader;
import de.twometer.arduleucht.util.BuildInfo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(ResourceLoader.getResource("layout/main.fxml"));
        loader.setResources(ResourceBundle.getBundle("bundles.gui"));
        Parent root = loader.load();
        Theme.apply(root);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(ResourceLoader.getResource("css/main.css").toExternalForm());

        primaryStage.getIcons().add(new Image(ResourceLoader.getResourceAsStream("icons/main.png")));
        primaryStage.setTitle(BuildInfo.buildTitle());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
