package fr.core.ui.Controller.manager;

import fr.core.model.databaseModel.Donation;
import fr.core.model.databaseModel.User;
import fr.core.service.inter.IAnnexService;
import fr.core.ui.Controller.MenuBarLoader;
import fr.core.ui.ControllerRouter;
import fr.core.ui.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public class GetDonationController {
    public MenuBar menuBar;
    public Label labelDonationName;
    public Label labelDonationDescription;
    public Button back;
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
        labelDonationName.setText(donation.get().getNom());
        labelDonationDescription.setText(donation.get().getDescription());
        if (donation.isPresent()) {
            for (int i = 0; i < donation.get().getRequerirs().size(); i++) {
                ListViewRequerir.getItems().add(new Label(
                        " Nom du produit :  " + donation.get().getRequerirs().get(i).getProduct().getName() + " Quantité du produit demandée: " + donation.get().getRequerirs().get(i).getQuantity() + " " + donation.get().getRequerirs().get(i).getProduct().getType().getName()
                                + " Quantité du produit encore necessaire: " + donation.get().getRequerirs().get(i).getQuantityLeft() + " " + donation.get().getRequerirs().get(i).getProduct().getType().getName()+ "\n\n"));
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
            ControllerRouter.geneRouter(router, ParticipateUserController.class);
        }
    }
}
