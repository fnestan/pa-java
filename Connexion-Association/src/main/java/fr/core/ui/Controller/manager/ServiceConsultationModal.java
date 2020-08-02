package fr.core.ui.Controller.manager;

import fr.core.model.databaseModel.Service;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;


public class ServiceConsultationModal {
    public static Optional<Service> service = Optional.empty();

    public static void createModal() {
        if (service.isPresent()) {
            Label labelAnnexName = new Label(service.get().getAnnex().getName());
            Label labelServiceName = new Label(service.get().getNom());
            Label labelServiceDesc = new Label(service.get().getDescription());
            Label labelServiceQty = new Label(Integer.toString(service.get().getQuantite()));
            Pane pane = new Pane();
            pane.setPrefSize(200, 200);
            VBox vBox = new VBox();
            vBox.setSpacing(10);
            vBox.setAlignment(Pos.BASELINE_CENTER);
            vBox.getChildren().add(labelAnnexName);
            vBox.getChildren().add(labelServiceName);
            vBox.getChildren().add(labelServiceDesc);
            vBox.getChildren().add(labelServiceQty);
            pane.getChildren().add(vBox);
            Stage stage = new Stage();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();
        }

    }
}
