package fr.core.ui.Controller.manager;

import fr.core.model.customModel.PluginData;
import fr.core.model.customModel.PluginModelData;
import fr.core.model.customModel.Session;
import fr.core.model.databaseModel.Message;
import fr.core.model.databaseModel.Ticket;
import fr.core.service.inter.ITicketService;
import fr.core.ui.Controller.MenuBarLoader;
import fr.core.ui.ControllerRouter;
import fr.core.ui.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class GetTicketController {
    public MenuBar menuBar;
    public Label labelTicket;
    public TextArea mesageToSend;
    public Button sendMessage;
    public static Integer ticketId;
    public VBox box;
    private Router router;
    private ITicketService iTicketService;

    @FXML
    public void initialize() throws Exception {
    }

    public void setRouter(Router router) throws Exception {
        this.router = router;
        MenuBarLoader m = new MenuBarLoader();
        m.LoadMenuBar(menuBar, router);
    }

    public void setiTicketService(ITicketService iTicketService) throws IOException {
        this.iTicketService = iTicketService;
        this.getTicket();
    }

    private void getTicket() throws IOException {
        ScrollPane sp = new ScrollPane();
        VBox vb = new VBox();
        Optional<Ticket> ticket = this.iTicketService.getTicketById(ticketId);
        labelTicket.setText(ticket.get().getNumber() + " - " + ticket.get().getLabel());
        box.getChildren().addAll(sp);
        VBox.setVgrow(sp, Priority.ALWAYS);
        vb.setSpacing(10);

        PluginModelData pluginModelData = new PluginModelData();
        pluginModelData.setScreen("Consultation du stock ");
        pluginModelData.setOutput(ticket.get().getMessages());
        pluginModelData.setInput(null);
        PluginData.pluginModelData = pluginModelData;

        for (Message me : ticket.get().getMessages()) {
            Pane splitPane = (Pane) FXMLLoader.load(this.getClass().getResource("/view/manager/panelofMessage.fxml"));
            splitPane.setPrefWidth(750);
            splitPane.setPrefHeight(120);
            if (me.getUserTrunqued().id == Session.user.getId()) {
                splitPane.setStyle("-fx-background-color: #33FFE2");
            }
            Label labelDate = (Label) splitPane.getChildren().stream().filter(ele -> ele.getId().equals("date")).findFirst().get();
            Label labelAuthor = (Label) splitPane.getChildren().stream().filter(ele -> ele.getId().equals("author")).findFirst().get();
            Label labelMessage = (Label) splitPane.getChildren().stream().filter(ele -> ele.getId().equals("message")).findFirst().get();
            labelDate.setText(me.getSendDate().toString());
            labelAuthor.setText(me.getUserTrunqued().firstname + " " + me.getUserTrunqued().lastname);
            labelMessage.setText(me.getContent());
            sp.setContent(vb);
            vb.getChildren().add(splitPane);
        }
    }

    public void send(ActionEvent event) throws NoSuchMethodException, IllegalAccessException, InstantiationException, FileNotFoundException, InvocationTargetException, ClassNotFoundException {
        if (!mesageToSend.getText().equals("")){
            iTicketService.createMessage(ticketId, mesageToSend.getText());
            ControllerRouter.geneRouter(router, GetTicketController.class);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le message doit contenir du texte");
            alert.showAndWait();
        }

    }

    public void back(ActionEvent event) throws NoSuchMethodException, IllegalAccessException, InstantiationException, FileNotFoundException, InvocationTargetException, ClassNotFoundException {
        ControllerRouter.geneRouter(router, TicketController.class);
    }
}
