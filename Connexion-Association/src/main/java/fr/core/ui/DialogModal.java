package fr.core.ui;

import javafx.scene.control.Alert;

public class DialogModal {
    public static void intercet(String title, String contain) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(contain);
        alert.showAndWait();
    }
}
