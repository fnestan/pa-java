package fr.core.ui.Controller.manager;

import fr.core.model.customModel.Information;
import fr.core.model.databaseModel.Stock;
import fr.core.service.inter.IStockService;
import fr.core.ui.ControllerRouter;
import fr.core.ui.Router;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public class AnnexStockController {
    public Label title;
    public IStockService iStockService;
    public Router router;
    public static int AnnexId;
    public VBox vbox;

    public void setRouter(Router router) throws Exception {
        this.router = router;
    }

    public void setiStockService(IStockService iStockService) {
        this.iStockService = iStockService;
        this.getStock();
    }

    public void getStock() {
        title.setText("Consultation du stock de l'annexe");
        Optional<List<Stock>> stocks = iStockService.getStock(AnnexId);
        if (stocks.isPresent()) {
            if (stocks.get().size() == 0) {
                vbox.getChildren().add(new Label("Vous n'avez aucun produit en stock"));
            } else {
                GridPane gridPane = new GridPane();
                gridPane.setPadding(new Insets(20));
                gridPane.setHgap(25);
                gridPane.setVgap(15);
                int raw = 1;
                gridPane.add(new Label("Nom du produit"), 0, 0);
                gridPane.add(new Label("Quantite du produit"), 1, 0);
                for (Stock stock : stocks.get()) {
                    String product = stock.getProduct().getName();
                    Label labelProduct = new Label(product);
                    String quantity = stock.getQuantity()
                            + " " + stock.getProduct().getType().getName();
                    Label labelQuantity = new Label(quantity);
                    Button updateStockButton = new Button("Modifier");
                    gridPane.add(labelProduct, 0, raw);
                    gridPane.add(labelQuantity, 1, raw);
                    gridPane.add(updateStockButton, 2, raw);
                    updateStockButton.setOnAction(event -> {
                        updateStock(stock.getId());
                    });
                    raw++;
                }
                vbox.getChildren().add(gridPane);
            }
        }
    }

    private void updateStock(int id) {
        Label label = new Label("Modifier la quantite du stock : ");
        TextField quantity = new TextField();
        quantity.setPromptText("quantité de produit");
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        Button update = new Button("Modifier");
        Button cancel = new Button("Annuler");
        hBox.setSpacing(20);
        hBox.getChildren().add(update);
        hBox.getChildren().add(cancel);
        vBox.setSpacing(20);
        vBox.getChildren().add(label);
        vBox.getChildren().add(quantity);
        vBox.getChildren().add(hBox);
        Pane pane = new Pane();
        pane.getChildren().add(vBox);
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        cancel.setOnAction(event -> {
            stage.close();
        });
        update.setOnAction(event -> {
            if (quantity.getText().matches("\\d{1,5}")) {
                Optional<Information> information = iStockService.updateStock(id, Integer.parseInt(quantity.getText()));
                if (information.isPresent()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Modifier le stock");
                    alert.setContentText(information.get().message);
                    alert.showAndWait();
                    stage.close();
                    try {
                        AnnexStockController.AnnexId = AnnexId;
                        ControllerRouter.geneRouter(router, AnnexStockController.class);
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

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Modifier le stock");
                alert.setContentText("Veuillez saisir un nombre");
                alert.showAndWait();
            }
        });
    }

    public void back(ActionEvent event) throws NoSuchMethodException, IllegalAccessException, InstantiationException, FileNotFoundException, InvocationTargetException, ClassNotFoundException {
        ControllerRouter.geneRouter(router, AnnexDetailController.class);
    }
}
