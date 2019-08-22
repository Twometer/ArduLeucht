package de.twometer.arduleucht;

import de.twometer.arduleucht.util.BuildInfo;
import de.twometer.arduleucht.util.ResourceLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro8.JMetro;

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
        new JMetro(JMetro.Style.DARK).applyTheme(root);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(ResourceLoader.getResource("css/main.css").toExternalForm());

        primaryStage.setTitle(BuildInfo.buildTitle());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /*
    Project project = new Project(new File("D:\\test_project"));
        ProjectBuilder builder = new ProjectBuilder(project);

        builder.setBuildListener(new BuildListener() {
            @Override
            public void onBuildStateChanged(BuildState buildState) {
                System.out.println("Build state: " + buildState);
            }

            @Override
            public void onBuildFailed(String message) {
                System.out.println("Build failed: " + message);
            }

            @Override
            public void onBuildSucceeded() {
                System.out.println("Build successful");
            }
        });

        builder.build();
     */

}
