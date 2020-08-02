package fr.core.ui.Controller.manager;

import fr.core.model.databaseModel.Donation;
import fr.core.service.inter.IAnnexService;
import fr.core.ui.Controller.MenuBarLoader;
import fr.core.ui.Router;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Optional;

public class GetDonationController {
    public MenuBar menuBar;
    public Label labelDonationName;
    public Label labelDonationDescription;
    public ListView ListViewRequerir;
    public ScrollPane scroll;
    private Router router;
    public static Integer donationId;
    private IAnnexService iAnnexService;

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
        Optional<Donation> donation = this.iAnnexService.getDonationById(donationId);
        if (donation.isPresent()) {
            for (int i = 0; i < donation.get().getRequerirs().size(); i++) {
                ListViewRequerir.getItems().add(new Label("Nom de la donation : " + donation.get().getNom() + "  Description : "
                        + donation.get().getDescription() +
                        " Nom du produit :  " + donation.get().getRequerirs().get(i).getProduct().getName() + " Quantité du produit : " + donation.get().getRequerirs().get(i).getQuantity() + " " + donation.get().getRequerirs().get(i).getProduct().getType().getName() + "\n\n"));
            }
        }
    }
}
