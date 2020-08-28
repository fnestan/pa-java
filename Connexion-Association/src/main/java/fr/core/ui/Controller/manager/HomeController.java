package fr.core.ui.Controller.manager;

import fr.core.model.customModel.PluginData;
import fr.core.model.customModel.PluginModelData;
import fr.core.model.databaseModel.Annex;
import fr.core.service.inter.IAnnexService;
import fr.core.ui.Controller.MenuBarLoader;
import fr.core.ui.ControllerRouter;
import fr.core.ui.Router;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public class HomeController {
    public VBox vb;
    private Router router;

    @FXML
    MenuBar menuBar;


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
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(30);
        gridPane.setVgap(30);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().add(gridPane);
        Optional<List<Annex>> annexes = annexService.myAnnexes();
        PluginModelData pluginModelData = new PluginModelData();
        pluginModelData.setScreen("Acceuil liste des annexes");
        if (annexes.isPresent()) {
            if (annexes.get().size() == 0) {
                TextFlow text = new TextFlow(new Text("Vous n'avez aucune annexe accessible"));
                gridPane.add(text, 0, 0);
            } else {
                pluginModelData.setOutput(annexes.get());
                pluginModelData.setInput(null);
                PluginData.pluginModelData = pluginModelData;
                int raw = 1;
                gridPane.add(new Label("Nom"), 0, 0);
                gridPane.add(new Label("Email"), 1, 0);
                gridPane.add(new Label("Adresse"), 2, 0);
                gridPane.add(new Label("Téléphone"), 3, 0);
                gridPane.add(new Label("Actions"), 4, 0);
                for (Annex annex : annexes.get()) {
                    Label name = new Label(annex.getName());
                    Label email = new Label(annex.getEmail());
                    Label address = new Label(annex.getStreet() + " " + annex.getZipCode() + "   "
                            + annex.getCity());
                    Label phone = new Label(annex.getPhone());
                    Button edit = new Button("Consulter");
                    gridPane.add(name, 0, raw);
                    gridPane.add(email, 1, raw);
                    gridPane.add(address, 2, raw);
                    gridPane.add(phone, 3, raw);
                    gridPane.add(edit, 4, raw);
                    raw++;
                    edit.setOnAction(event -> {
                        AnnexDetailController.idAnnex = annex.getId();
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
                    });
                }
            }
        }
    }
}
