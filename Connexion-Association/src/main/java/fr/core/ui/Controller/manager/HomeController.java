package fr.core.ui.Controller.manager;

import fr.core.model.databaseModel.Annex;
import fr.core.service.inter.IAnnexService;
import fr.core.ui.Controller.MenuBarLoader;
import fr.core.ui.ControllerRouter;
import fr.core.ui.Router;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
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
                    Pane p = new Pane();
                    TextFlow text = new TextFlow(new Text(annex.getName() + "    " + annex.getEmail() +
                            "   " + annex.getStreet() + "   " + annex.getZipCode() + "   "
                            + annex.getStreet() + "   " + annex.getPhone()));
                    p.getChildren().add(text);
                    text.setId(String.valueOf(annex.getId()));
                    this.list.getItems().add(text);
                }
            }
        }
        list.setOnMouseClicked(Event -> {
            TextFlow t = (TextFlow) list.getSelectionModel().getSelectedItem();
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
