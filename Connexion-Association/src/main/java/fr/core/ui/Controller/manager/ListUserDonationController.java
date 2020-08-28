package fr.core.ui.Controller.manager;

import fr.core.model.customModel.Information;
import fr.core.model.customModel.PluginData;
import fr.core.model.customModel.PluginModelData;
import fr.core.model.databaseModel.Donation;
import fr.core.model.databaseModel.User;
import fr.core.model.databaseModel.UserDonation;
import fr.core.service.inter.IAnnexService;
import fr.core.ui.ControllerRouter;
import fr.core.ui.Router;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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
    public Button back;
    public ScrollPane scroll;


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
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(25);
        gridPane.setVgap(15);
        scroll.setContent(gridPane);
        title.setText("Donation de " + user.getFirstname() + " " + user.getLastname());
        Optional<List<UserDonation>> userDonations = iAnnexService.getDonation(donation.getId(), user.getId());


        if (userDonations.isPresent()) {

            PluginModelData pluginModelData = new PluginModelData();
            pluginModelData.setScreen("Consultation du stock ");
            pluginModelData.setOutput(userDonations.get());
            pluginModelData.setInput(null);
            PluginData.pluginModelData = pluginModelData;


            gridPane.add(new Label("Nom du produit"), 0, 0);
            gridPane.add(new Label("Qunatité"), 1, 0);
            gridPane.add(new Label("Statut"), 2, 0);
            int raw = 1;
            for (UserDonation userDonation : userDonations.get()) {
                String status = null;
                Label statusLabel = new Label();
                if (userDonation.isGive()) {
                    status = "Donnée";
                } else {
                    status = "En attente";
                }
                statusLabel.setText(status);
                gridPane.add(new Label(userDonation.getProduct().getName()), 0, raw);
                String quantity = String.valueOf(userDonation.getQuantity()) + " " + userDonation.getProduct().getType().getName();
                gridPane.add(new Label(quantity), 1, raw);
                gridPane.add(statusLabel, 2, raw);
                if (!userDonation.isGive()) {
                    Button button = new Button("Récuperer");
                    gridPane.add(button, 3, raw);
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
                raw++;
            }
        }
    }
}
