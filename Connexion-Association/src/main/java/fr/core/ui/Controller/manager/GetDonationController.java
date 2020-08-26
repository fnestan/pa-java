package fr.core.ui.Controller.manager;

import fr.core.model.databaseModel.Donation;
import fr.core.model.databaseModel.User;
import fr.core.service.inter.IAnnexService;
import fr.core.ui.Controller.MenuBarLoader;
import fr.core.ui.ControllerRouter;
import fr.core.ui.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public class GetDonationController {
    public MenuBar menuBar;
    public Label labelDonationName;
    public Button back;
    public ScrollPane scroll;
    public TextArea description;
    private Router router;
    public static Integer donationId;
    private IAnnexService iAnnexService;
    private Donation donation;

    @FXML
    public void initialize() throws Exception {
    }

    public void setRouter(Router router) throws Exception {
        this.router = router;
        MenuBarLoader m = new MenuBarLoader();
        m.LoadMenuBar(menuBar, router);
    }

    public void setiAnnexService(IAnnexService iAnnexService) throws IOException {
        this.iAnnexService = iAnnexService;
        this.getDonation();
    }

    private void getDonation() throws IOException {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(25);
        gridPane.setVgap(15);
        scroll.setContent(gridPane);
        Optional<Donation> donation = this.iAnnexService.getDonationById(donationId);
        labelDonationName.setText(donation.get().getNom());
        description.setWrapText(true);
        description.setText(donation.get().getDescription());
        if (donation.isPresent()) {
            this.donation = donation.get();
            int raw = 1;
            gridPane.add(new Label("Nom du produit"), 0, 0);
            gridPane.add(new Label("Quantite du produit"), 1, 0);
            gridPane.add(new Label("Quantite restante"), 2, 0);
            for (int i = 0; i < donation.get().getRequerirs().size(); i++) {
                gridPane.add(new Label(donation.get().getRequerirs().get(i).getProduct().getName()), 0, raw);
                gridPane.add(new Label(String.valueOf(donation.get().getRequerirs().get(i).getQuantity() + " " + donation.get().getRequerirs().get(i).getProduct().getType().getName())), 1, raw);
                gridPane.add(new Label(donation.get().getRequerirs().get(i).getQuantityLeft() + " " + donation.get().getRequerirs().get(i).getProduct().getType().getName()), 2, raw);
                raw++;
            }

        }
    }

    public void back(ActionEvent event) throws NoSuchMethodException, IllegalAccessException, InstantiationException, FileNotFoundException, InvocationTargetException, ClassNotFoundException {
        ControllerRouter.geneRouter(router, AnnexDetailController.class);
    }

    public void donors(ActionEvent event) throws NoSuchMethodException, IllegalAccessException, InstantiationException, FileNotFoundException, InvocationTargetException, ClassNotFoundException {
        Optional<List<User>> users = iAnnexService.getDonors(donationId);
        if (users.isPresent()) {
            ParticipateUserController.typeAction = "Donation";
            ParticipateUserController.title = "Liste des Donateurs";
            ParticipateUserController.userList = users.get();
            ParticipateUserController.donation = this.donation;
            ControllerRouter.geneRouter(router, ParticipateUserController.class);
        }
    }
}
