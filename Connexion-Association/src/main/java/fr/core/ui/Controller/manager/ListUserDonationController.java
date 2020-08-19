package fr.core.ui.Controller.manager;

import fr.core.model.customModel.Information;
import fr.core.model.databaseModel.Donation;
import fr.core.model.databaseModel.User;
import fr.core.model.databaseModel.UserDonation;
import fr.core.service.inter.IAnnexService;
import fr.core.ui.ControllerRouter;
import fr.core.ui.Router;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public class ListUserDonationController {
    public Label title;
    public ListView donationListView;
    public static User user;
    public static Donation donation;
    public IAnnexService iAnnexService;
    public Router router;


    public void setiAnnexService(IAnnexService iAnnexService) {
        this.iAnnexService = iAnnexService;
        this.giftList();
    }

    public void back(ActionEvent event) throws NoSuchMethodException, IllegalAccessException, InstantiationException, FileNotFoundException, InvocationTargetException, ClassNotFoundException {
        ControllerRouter.geneRouter(router, ParticipateUserController.class);
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public void giftList() {
        title.setText("Donation de " + user.getFirstname() + " " + user.getLastname());
        Optional<List<UserDonation>> userDonations = iAnnexService.getDonation(donation.getId(), user.getId());
        if (userDonations.isPresent()) {
            for (UserDonation userDonation : userDonations.get()) {
                HBox hBox = new HBox();
                hBox.setSpacing(20.0);
                String status = null;
                Label statusLabel = new Label();
                if (userDonation.isGive()) {
                    status = "Donnée";
                } else {
                    status = "En attente";
                }
                statusLabel.setText(status);
                hBox.getChildren().add(new Label("Nom du produit : " + userDonation.getProduct().getName()));
                String quantity = String.valueOf("Quantité : " + userDonation.getQuantity()) + " " + userDonation.getProduct().getType().getName();
                hBox.getChildren().add(new Label(quantity));
                hBox.getChildren().add(statusLabel);
                if (!userDonation.isGive()) {
                    Button button = new Button("Récuperer");
                    hBox.getChildren().add(button);
                    button.setOnAction(event -> {
                        Optional<Information> information = iAnnexService.setGive(userDonation.getId());
                        Alert alert = new Alert(null);
                        alert.setTitle("Réception d'une donation");
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setContentText(information.get().message);
                        alert.showAndWait();
                        statusLabel.setText("Donnée");
                        button.setVisible(false);
                    });
                }
                donationListView.getItems().add(hBox);
            }
        }
    }
}
