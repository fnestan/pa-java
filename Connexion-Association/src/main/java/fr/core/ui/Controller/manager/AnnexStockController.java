package fr.core.ui.Controller.manager;

import fr.core.model.customModel.Information;
import fr.core.model.databaseModel.Stock;
import fr.core.service.inter.IStockService;
import fr.core.ui.ControllerRouter;
import fr.core.ui.Router;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    public ListView stockListView;
    public IStockService iStockService;
    public Router router;
    public static int AnnexId;

    public void setRouter(Router router) throws Exception {
        this.router = router;
    }

    public void setiStockService(IStockService iStockService) {
        this.iStockService = iStockService;
        this.getStock();
    }

    public void getStock() {
        Optional<List<Stock>> stocks = iStockService.getStock(AnnexId);
        if (stocks.isPresent()) {
            if (stocks.get().size() == 0) {
                stockListView.getItems().add(new Label("Vous n'avez aucun produit en stock"));
            } else {
                for (Stock stock : stocks.get()) {
                    HBox hbox = new HBox();
                    hbox.setSpacing(20);
                    String product = "Nom du produit: " + stock.getProduct().getName();
                    Label labelProduct = new Label(product);
                    hbox.getChildren().add(labelProduct);
                    String quantity = "Quantité en stock du produit: "
                            + stock.getQuantity()
                            + " " + stock.getProduct().getType().getName();
                    Label labelQuantity = new Label(quantity);
                    hbox.getChildren().add(labelQuantity);
                    Button updateStockButton = new Button("Modifier");
                    hbox.getChildren().add(updateStockButton);
                    stockListView.getItems().add(hbox);
                    updateStockButton.setOnAction(event -> {
                        updateStock(stock.getId());
                    });
                }
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
