package fr.core.ui.Controller.manager;

import fr.core.model.customModel.Email;
import fr.core.model.customModel.Information;
import fr.core.model.databaseModel.Product;
import fr.core.model.databaseModel.User;
import fr.core.service.inter.IUserService;
import fr.core.ui.ControllerRouter;
import fr.core.ui.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class ContactUserController {
    public TextField objet;
    public TextArea message;
    public Button send;
    public Button cancel;
    public Label contact;
    public static User user;
    public IUserService userService;
    private Router router;


    public void setUserService(IUserService userService) {
        this.userService = userService;
        message.setWrapText(true);
        contact.setText("Contacter" + user.getFirstname() + " " + user.getLastname());
    }

    public void cancel(ActionEvent event) throws NoSuchMethodException, IllegalAccessException, InstantiationException, FileNotFoundException, InvocationTargetException, ClassNotFoundException {
        ControllerRouter.geneRouter(router, ParticipateUserController.class);

    }

    public void send(ActionEvent event) throws NoSuchMethodException, IllegalAccessException, InstantiationException, FileNotFoundException, InvocationTargetException, ClassNotFoundException {
        if (objet.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("contacter un utilisateur");
            alert.setContentText("Il doit y avoir un objet");
            alert.showAndWait();
        }
        if (message.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("contacter un utilisateur");
            alert.setContentText("Il doit y avoir un message");
            alert.showAndWait();
        }
        if (!objet.getText().equals("") && !message.getText().equals("")) {
            Email email = new Email();
            email.setObject(objet.getText());
            email.setEmail(user.getEmail());
            email.setMessage(message.getText());
            Optional<Information> information = userService.sendMail(email);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("contacter un utilisateur");
            alert.setContentText(information.get().message);
            alert.showAndWait();
            ControllerRouter.geneRouter(router, ParticipateUserController.class);
        }
    }


    public void setRouter(Router router) {
        this.router = router;
    }
}
