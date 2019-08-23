package de.twometer.arduleucht.gui;

import de.twometer.arduleucht.util.BuildInfo;
import de.twometer.arduleucht.util.ResourceLoader;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro8.JMetro;

import java.io.IOException;
import java.util.ResourceBundle;

public class UploadController {

    public ProgressBar progressBar;
    public Label uploadContentLabel;
    private Stage stage;

    static UploadController show() throws IOException {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(ResourceLoader.getResource("layout/upload.fxml"));
        loader.setResources(ResourceBundle.getBundle("bundles.gui"));

        Parent root = loader.load();
        new JMetro(JMetro.Style.LIGHT).applyTheme(root);

        UploadController controller = loader.getController();
        controller.stage = stage;

        Scene scene = new Scene(root);
        stage.setTitle(BuildInfo.buildTitle());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Uploading...");
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        return controller;
    }

    void setStatus(String status) {
        Platform.runLater(() -> uploadContentLabel.setText(status));
    }

    void close() {
        Platform.runLater(() -> stage.close());
    }

}
