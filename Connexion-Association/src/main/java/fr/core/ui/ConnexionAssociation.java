package fr.core.ui;

import fr.core.service.config.ConfigService;
import fr.core.ui.Controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ConnexionAssociation extends Application {
    private Router router;


    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.router = new Router(stage);
        ConfigService configService = new ConfigService();
        configService.Config();
        ControllerRouter.geneRouter(router, LoginController.class);
        stage.setTitle("CA");
        stage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
