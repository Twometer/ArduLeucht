package de.twometer.arduleucht.render;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;

class TextMetrics {

    private static TextMetrics instance;

    private Text text;

    private TextMetrics() {
        text = new Text();
        new Scene(new Group(text));
    }

    static TextMetrics getInstance() {
        if (instance == null) instance = new TextMetrics();
        return instance;
    }

    Bounds measure(String text) {
        this.text.setText(text);
        this.text.applyCss();
        return this.text.getLayoutBounds();
    }

}
