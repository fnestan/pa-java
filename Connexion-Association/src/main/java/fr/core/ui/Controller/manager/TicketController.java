package fr.core.ui.Controller.manager;

import com.sun.source.tree.IfTree;
import fr.core.model.customModel.PluginData;
import fr.core.model.customModel.PluginModelData;
import fr.core.model.customModel.Session;
import fr.core.model.databaseModel.Ticket;
import fr.core.service.inter.ITicketService;
import fr.core.ui.Controller.MenuBarLoader;
import fr.core.ui.ControllerRouter;
import fr.core.ui.Router;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketController {
    public MenuBar menuBar;
    public ScrollPane scrolTickets;
    public VBox vboxList;
    private Router router;
    ITicketService ticketService;

    @FXML
    public void initialize() throws Exception {
    }


    public void setTicketService(ITicketService ticketService) throws Exception {
        this.ticketService = ticketService;
        this.listTickets();
    }

    public void setRouter(Router router) throws Exception {
        this.router = router;
        MenuBarLoader m = new MenuBarLoader();
        m.LoadMenuBar(menuBar, router);
    }

    public void listTickets() throws Exception {
        Optional<List<Ticket>> tickets = Optional.of(new ArrayList<>());
        if (Session.user.getRoleId() == 3) {
            tickets = ticketService.getAllTickets();
        } else {
            tickets = ticketService.getMyTicket();
        }
        if (tickets.isPresent()) {
            if (tickets.get().size() == 0) {
                HBox hBox = new HBox();
                hBox.getChildren().add(new Label("Vous n'avez aucun tickets de disponible"));
                this.vboxList.getChildren().add(hBox);
            } else {
                int raw = 1;

                PluginModelData pluginModelData = new PluginModelData();
                pluginModelData.setScreen("Consultation du stock ");
                pluginModelData.setOutput(tickets.get());
                pluginModelData.setInput(null);
                PluginData.pluginModelData = pluginModelData;


                ScrollPane scrollPane = new ScrollPane();
                GridPane gridPane = new GridPane();
                gridPane.setPadding(new Insets(20));
                gridPane.setHgap(25);
                gridPane.setVgap(15);
                scrollPane.setContent(gridPane);
                gridPane.add(new Label("numéro - Libellé"), 0, 0);
                for (Ticket ticket : tickets.get()) {
                    TextFlow text = new TextFlow(new Text(ticket.getNumber() + " - " + ticket.getLabel()));
                    Button read = new Button("Voir");
                    Button close = new Button("Clore");
                    gridPane.add(text, 0, raw);
                    gridPane.add(read, 1, raw);
                    gridPane.add(close, 2, raw);
                    read.setOnAction(event -> {
                        GetTicketController.ticketId = ticket.getId();
                        try {
                            ControllerRouter.geneRouter(router, GetTicketController.class);
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
                    close.setOnAction(event -> {
                        var response = ticketService.closeTicket(ticket.getId());
                        try {
                            ControllerRouter.geneRouter(router, TicketController.class);
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
                    raw++;
                }
                vboxList.getChildren().add(scrollPane);
            }
        }
    }
}
