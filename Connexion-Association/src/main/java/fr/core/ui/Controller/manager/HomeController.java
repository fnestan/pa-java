package fr.core.ui.Controller.manager;

import fr.core.model.databaseModel.Annex;
import fr.core.service.inter.IAnnexService;
import fr.core.ui.Controller.MenuBarLoader;
import fr.core.ui.ControllerRouter;
import fr.core.ui.Router;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public class HomeController {
    private Router router;

    @FXML
    MenuBar menuBar;

    @FXML
    ScrollPane scrolAnnexes;

    @FXML
    ListView list;

    IAnnexService annexService;


    @FXML
    public void initialize() throws Exception {
    }


    public void setRouter(Router router) throws Exception {
        this.router = router;
        MenuBarLoader menuBarLoader = new MenuBarLoader();
        menuBarLoader.LoadMenuBar(menuBar, router);
    }

    public void setAnnexService(IAnnexService annexService) throws Exception {
        this.annexService = annexService;
        this.listAnnex();
    }

    public void listAnnex() throws Exception {
        Optional<List<Annex>> annexes = annexService.myAnnexes();
        if (annexes.isPresent()) {
            if (annexes.get().size() == 0) {
                Pane p = new Pane();
                TextFlow text = new TextFlow(new Text("Vous n'avez aucune annexe accessible"));
                p.getChildren().add(text);
                this.list.getItems().add(text);
            } else {
                for (Annex annex : annexes.get()) {
                    HBox hBox = new HBox();
                    hBox.setSpacing(30);
                    hBox.getChildren().add(new Label("Nom de l'annexe : " +annex.getName()));
                    hBox.getChildren().add(new Label("Couriel de l'annexe : " +annex.getEmail()));
                    hBox.getChildren().add(new Label("Adresse de l'annexe : " +annex.getStreet() + " " + annex.getZipCode() + "   "
                            + annex.getCity()));
                    hBox.getChildren().add(new Label("Téléphone de l'annexe : " +annex.getPhone()));
                    hBox.setId(String.valueOf(annex.getId()));
                    this.list.getItems().add(hBox);

                }
            }
        }
        list.setOnMouseClicked(Event -> {
            HBox t = (HBox) list.getSelectionModel().getSelectedItem();
            if (t != null) {
                AnnexDetailController.idAnnex = Integer.parseInt(t.getId());
                try {
                    ControllerRouter.geneRouter(router, AnnexDetailController.class);
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
            }
        });
    }
}
