package fr.core.ui;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class Router {

    private final Stage stage;

    Router(final Stage stage) {
        this.stage = stage;
    }

    public void goTo(final String viewName) {
        goTo(viewName, __ -> {
        });
    }

    public <T> void goTo(final String viewName, final Consumer<T> controllerConsumer) {
        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
        final var view = loadView(viewName, controllerConsumer);
        Scene scene = new Scene(view,screenSize.getWidth(), screenSize.getHeight());
        stage.setScene(scene);
        if(!stage.isMaximized()){
            stage.setMaximized(true);

        }
    }

    private <T> Parent loadView(final String viewName, final Consumer<T> controllerConsumer) {
        final var viewPath = String.format("/%sView.fxml", viewName);
        try {
            final var fxmlLoader = new FXMLLoader(this.getClass().getResource(viewPath));
            final Parent view = fxmlLoader.load();
            controllerConsumer.accept(fxmlLoader.getController());
            return view;
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Unable to load view: %s", viewPath), e);
        }
    }
}
