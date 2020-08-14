package fr.core.ui.Controller.manager;

import fr.core.model.databaseModel.User;
import fr.core.service.config.ConfigService;
import fr.core.service.inter.IUserService;
import fr.core.ui.Controller.MenuBarLoader;
import fr.core.ui.ControllerRouter;
import fr.core.ui.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParticipateUserController {
    public MenuBar menuBar;
    public Label labelTitle;
    public ListView userListView;
    static String title;
    static String typeAction;
    static List<User> userList = new ArrayList<>();
    private Router router;


    public void setRouter(Router router) throws Exception {
        this.router = router;
        MenuBarLoader m = new MenuBarLoader();
        m.LoadMenuBar(menuBar, router);
        setUserListView();
    }

    public void setUserListView() {
        labelTitle.setText(title);
        if (userList.size() > 0) {
            for (User user : userList) {
                HBox hBox = new HBox();
                hBox.setSpacing(20);
                Label labelName = new Label();
                labelName.setText(user.getFirstname() + " " + user.getLastname());
                Label labelEmail = new Label();
                labelEmail.setText(user.getEmail());
                Button report = new Button("Reporter");
                Button contact = new Button("Contacter");
                hBox.getChildren().add(labelName);
                hBox.getChildren().add(labelEmail);
                hBox.getChildren().add(contact);
                hBox.getChildren().add(report);
                if (typeAction.equals("Donation")) {
                    Button gift = new Button("Don");
                    hBox.getChildren().add(gift);
                }
                userListView.getItems().add(hBox);
                contact.setOnAction(event -> {
                    try {
                        ContactUserController.user =  user;
                        ControllerRouter.geneRouter(router,ContactUserController.class);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                });
            }
        } else {
            HBox hBox = new HBox();
            hBox.setSpacing(20);
            Label labelEmpty = new Label();
            labelEmpty.setText("Il n'y a aucun utilisateur qui participe a cette action");
            hBox.getChildren().add(labelEmpty);
            userListView.getItems().add(hBox);
        }

    }


    public void back(ActionEvent event) {
    }
}
