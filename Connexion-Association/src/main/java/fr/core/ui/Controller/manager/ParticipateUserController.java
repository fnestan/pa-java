package fr.core.ui.Controller.manager;

import fr.core.model.customModel.Information;
import fr.core.model.databaseModel.Donation;
import fr.core.model.databaseModel.User;
import fr.core.service.inter.IUserService;
import fr.core.ui.Controller.MenuBarLoader;
import fr.core.ui.ControllerRouter;
import fr.core.ui.Router;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParticipateUserController {
    public MenuBar menuBar;
    public Label labelTitle;
    static String title;
    static Donation donation;
    static String typeAction;
    static List<User> userList = new ArrayList<>();
    public ScrollPane scroll;
    private Router router;
    private IUserService iUserService;


    public void setRouter(Router router) throws Exception {
        this.router = router;
        MenuBarLoader m = new MenuBarLoader();
        m.LoadMenuBar(menuBar, router);
    }

    public void setiUserService(IUserService iUserService) {
        this.iUserService = iUserService;
        this.setUserListView();
    }

    public void setUserListView() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(25);
        gridPane.setVgap(15);
        scroll.setContent(gridPane);
        labelTitle.setText(title);
        if (userList.size() > 0) {
            int raw = 1;
            gridPane.add(new Label("Nom Prénom"), 0, 0);
            gridPane.add(new Label("Email"), 1, 0);
            for (User user : userList) {
                Label labelName = new Label();
                labelName.setText(user.getFirstname() + " " + user.getLastname());
                Label labelEmail = new Label();
                labelEmail.setText(user.getEmail());
                Button report = new Button("Reporter");
                Button contact = new Button("Contacter");
                gridPane.add(labelName, 0, raw);
                gridPane.add(labelEmail, 1, raw);
                gridPane.add(contact, 2, raw);
                if (!user.isReported()) {
                    gridPane.add(report, 3, raw);
                }
                if (typeAction.equals("Donation")) {
                    Button gift = new Button("Don");
                    gridPane.add(gift, 4, raw);
                    gift.setOnAction(event -> {
                        ListUserDonationController.user = user;
                        ListUserDonationController.donation = donation;
                        try {
                            ControllerRouter.geneRouter(router, ListUserDonationController.class);
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
                contact.setOnAction(event -> {
                    try {
                        ContactUserController.user = user;
                        ControllerRouter.geneRouter(router, ContactUserController.class);
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
                report.setOnAction(event -> {
                    Optional<Information> information = iUserService.reportUser(user.getId(), AnnexDetailController.idAnnex);
                    if (information.isPresent()) {
                        report.setVisible(false);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Bannir un utilisateur");
                        alert.setContentText(information.get().message);
                        alert.showAndWait();
                    }
                });
            }
        } else {
            HBox hBox = new HBox();
            hBox.setSpacing(20);
            Label labelEmpty = new Label();
            labelEmpty.setText("Il n'y a aucun utilisateur qui participe à cette action");
            hBox.getChildren().add(labelEmpty);
            gridPane.getChildren().add(hBox);
        }

    }


    public void back(ActionEvent event) throws NoSuchMethodException, IllegalAccessException, InstantiationException, FileNotFoundException, InvocationTargetException, ClassNotFoundException {
        if (typeAction.equals("Donation")) {
            ControllerRouter.geneRouter(router, GetDonationController.class);
        } else {
            ControllerRouter.geneRouter(router, GetServiceController.class);
        }
    }
}
