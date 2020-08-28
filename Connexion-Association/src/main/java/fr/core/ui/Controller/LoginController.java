package fr.core.ui.Controller;

import com.sun.source.util.Plugin;
import fr.core.model.customModel.Login;
import fr.core.model.customModel.Information;
import fr.core.model.customModel.PluginModelData;
import fr.core.model.customModel.Session;
import fr.core.model.databaseModel.User;
import fr.core.service.inter.IAuthService;
import fr.core.ui.Controller.admin.UserController;
import fr.core.ui.Controller.manager.HomeController;
import fr.core.ui.ControllerRouter;
import fr.core.ui.Router;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Optional;

public class LoginController {
    private Router router;
    IAuthService authService;

    @FXML
    private Button login;

    @FXML
    PasswordField password;

    @FXML
    TextField LoginTextField;

    @FXML
    public void initialize() {
    }

    public void setRouter(Router router) {
        this.router = router;

    }

    public void setAuthService(IAuthService authService) {
        this.authService = authService;
    }

    public void login(MouseEvent mouseEvent) {

        Login login = new Login();
//        login.login = this.LoginTextField.getText();
//        login.password = this.password.getText();
        login.login = "fnestan";
        login.password = "password";
        try {
            Optional<User> user = authService.login(login);
            if (user.isPresent()) {
                Session.user = user.get();
                if (Session.user.getRoleId() == 4) {
                    ControllerRouter.geneRouter(router, HomeController.class);
                    setAuthService(null);
                } else {
                    ControllerRouter.geneRouter(router, UserController.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
