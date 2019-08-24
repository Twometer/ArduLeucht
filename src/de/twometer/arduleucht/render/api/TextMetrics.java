package de.twometer.arduleucht.render.api;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;

public class TextMetrics {

    private static TextMetrics instance;

    private Text text;

    private TextMetrics() {
        text = new Text();
        new Scene(new Group(text));
    }

    public static TextMetrics getInstance() {
        if (instance == null) instance = new TextMetrics();
        return instance;
    }

    public Bounds measure(String text) {
        this.text.setText(text);
        this.text.applyCss();
        return this.text.getLayoutBounds();
    }

}
