package fr.core.ui.Controller.manager;

import fr.core.model.customModel.PluginData;
import fr.core.model.customModel.PluginModelData;
import fr.core.model.databaseModel.Service;
import fr.core.model.databaseModel.User;
import fr.core.service.inter.IAnnexService;
import fr.core.ui.Controller.MenuBarLoader;
import fr.core.ui.ControllerRouter;
import fr.core.ui.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public class GetServiceController {

    public MenuBar menuBar;
    public Label labelServiceName;
    public TextArea description;
    public Label nbPersons;
    public Label dateService;
    public Label nbRegistered;
    private Router router;
    public static Integer serviceId;
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
        this.getService();
    }

    private void getService() {
        Optional<Service> service = this.iAnnexService.getServiceById(serviceId);

        PluginModelData pluginModelData = new PluginModelData();
        pluginModelData.setScreen("Consultation du stock ");
        pluginModelData.setOutput(service.get());
        pluginModelData.setInput(null);
        PluginData.pluginModelData = pluginModelData;

        labelServiceName.setText("Nom du service : " + service.get().getNom());
        description.setWrapText(true);
        description.setText(service.get().getDescription());
        dateService.setText("Date du service : " + service.get().getDate_service().toString());
        nbPersons.setText("nombre de personnes requise : " + service.get().getQuantite());
        nbRegistered.setText("nombre de personnes inscrites : " + service.get().getRegistered());
    }

    public void back(ActionEvent event) throws NoSuchMethodException, IllegalAccessException, InstantiationException, FileNotFoundException, InvocationTargetException, ClassNotFoundException {
        ControllerRouter.geneRouter(router, AnnexDetailController.class);
    }

    public void participants(ActionEvent event) throws Exception {
        Optional<List<User>> users = iAnnexService.getParticipants(serviceId);
        if (users.isPresent()) {
            ParticipateUserController.typeAction = "Service";
            ParticipateUserController.title = "Liste des bénévoles";
            ParticipateUserController.userList = users.get();
            ControllerRouter.geneRouter(router, ParticipateUserController.class);
        }
    }
}
