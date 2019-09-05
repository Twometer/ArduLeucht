package de.twometer.arduleucht.gui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import jfxtras.styles.jmetro8.JMetro;

public class Theme {

    private static JMetro metro = new JMetro(JMetro.Style.LIGHT);

    static void apply(Dialog dialog) {
        metro.applyTheme(dialog.getDialogPane().getScene());
    }

    public static void apply(Scene scene) {
        metro.applyTheme(scene);
    }

    public static void apply(Parent parent) {
        metro.applyTheme(parent);
    }

    static void apply(Alert alert) {
        metro.applyTheme(alert.getDialogPane().getScene());
    }

}
