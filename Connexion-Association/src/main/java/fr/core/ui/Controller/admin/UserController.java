package fr.core.ui.Controller.admin;

import fr.core.model.customModel.Session;
import fr.core.model.databaseModel.User;
import fr.core.model.customModel.ValidationResponse;
import fr.core.service.inter.IUserService;
import fr.core.ui.Controller.MenuBarLoader;
import fr.core.ui.ControllerRouter;
import fr.core.ui.Router;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserController {

    private Router router;
    @FXML
    MenuBar menuBar;
    @FXML
    VBox vboxuser;
    @FXML
    TextField searchTextField;
    Optional<List<User>> userList = Optional.of(new ArrayList<>());

    IUserService userService;


    @FXML
    public void initialize() throws Exception {
        menuBar.getMenus().clear();
        MenuBarLoader menuBarLoader = new MenuBarLoader();
        // menuBarLoader.LoadMenuBar(menuBar, router);

    }

    public void setUserService(IUserService userService) throws Exception {
        this.userService = userService;
        userList = this.getAllUser();
        this.loadVBox(userList);
    }

    private void loadVBox(Optional<List<User>> users) {
        if (users.isPresent()) {
            for (User user : users.get()) {
                if (user.getId() != Session.user.getId()) {
                    HBox hBox = new HBox();
                    hBox.setSpacing(10.0);
                    hBox.getChildren().add(new Label(user.getLastname().concat(" " + user.getFirstname())));
                    hBox.getChildren().add(new Label(user.getRole().getName()));
                    Button update = new Button("Modifier");
                    hBox.getChildren().add(update);
                    if (user.isActive()) {
                        Button ban = new Button("Bannir");
                        hBox.getChildren().add(ban);
                        ban.setOnAction(event -> {
                            try {
                                userService.banUser(user.getId());
                                ControllerRouter.geneRouter(router, UserController.class);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                vboxuser.getChildren().clear();
                                this.initialize();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                        if (user.getValidForUser().equals("ATTENTE")) {
                            Button accept = new Button("Valider Comme utilisateur");
                            Button refuse = new Button("Refuser Comme utilisateur");
                            hBox.getChildren().add(accept);
                            hBox.getChildren().add(refuse);
                            accept.setOnAction(event -> {
                                try {
                                    ValidationResponse validationResponse = new ValidationResponse();
                                    validationResponse.valide = "ACCEPTE";
                                    userService.validateUser(user.getId(), validationResponse);
                                    hBox.getChildren().remove(accept);
                                    hBox.getChildren().remove(refuse);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    vboxuser.getChildren().clear();
                                    this.initialize();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            });
                            refuse.setOnAction(event -> {
                                try {
                                    ValidationResponse validationResponse = new ValidationResponse();
                                    validationResponse.valide = "REFUSE";
                                    userService.validateUser(user.getId(), validationResponse);
                                    hBox.getChildren().remove(accept);
                                    hBox.getChildren().remove(refuse);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    vboxuser.getChildren().clear();
                                    this.initialize();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        } else if (user.getValidForUser().equals("ACCEPTE")) {
                            if (user.getValidForVolunteer().equals("ATTENTE")) {
                                Button accept = new Button("Valider Comme bénévole");
                                Button refuse = new Button("Refuser Comme bénévole");
                                hBox.getChildren().add(accept);
                                hBox.getChildren().add(refuse);
                                accept.setOnAction(event -> {
                                    try {
                                        ValidationResponse validationResponse = new ValidationResponse();
                                        validationResponse.valide = "ACCEPTE";
                                        userService.validateVolunteer(user.getId(), validationResponse);
                                        hBox.getChildren().remove(accept);
                                        hBox.getChildren().remove(refuse);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                                refuse.setOnAction(event -> {
                                    try {
                                        ValidationResponse validationResponse = new ValidationResponse();
                                        validationResponse.valide = "REFUSE";
                                        userService.validateVolunteer(user.getId(), validationResponse);
                                        hBox.getChildren().remove(accept);
                                        hBox.getChildren().remove(refuse);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                            }
                        }
                    }

                    vboxuser.setSpacing(20.0);
                    vboxuser.getChildren().add(hBox);
                }
            }
        }
        searchTextField.setOnKeyTyped(keyEvent -> {
            Optional<List<User>> userList1 = Optional.of(userList.get().stream().filter(user -> user.getLastname().startsWith(searchTextField.getText())).collect(Collectors.toList()));
            vboxuser.getChildren().clear();
            this.loadVBox(userList1);
        });
    }

    private Optional<List<User>> getAllUser() throws Exception {
        Optional<List<User>> users = userService.users();
        return users;
    }


    public void setRouter(Router router) {
        this.router = router;
    }
}
