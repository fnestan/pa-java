package fr.core.ui.Controller.manager;

import com.sun.source.tree.IfTree;
import fr.core.model.customModel.Session;
import fr.core.model.databaseModel.Ticket;
import fr.core.service.inter.ITicketService;
import fr.core.ui.Controller.MenuBarLoader;
import fr.core.ui.ControllerRouter;
import fr.core.ui.Router;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
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
    public ListView list;
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
                this.list.getItems().add(hBox);
            } else {
                for (Ticket ticket : tickets.get()) {
                    HBox hBox = new HBox();
                    hBox.setSpacing(20.0);
                    TextFlow text = new TextFlow(new Text(ticket.getNumber() + " - " + ticket.getLabel()));
                    hBox.getChildren().add(text);
                    Button close = new Button("Clore");
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
                    hBox.getChildren().add(close);
                    hBox.setId(String.valueOf(ticket.getId()));
                    this.list.getItems().add(hBox);
                }
            }
        }
        list.setOnMouseClicked(Event -> {
            HBox hBox = (HBox) list.getSelectionModel().getSelectedItem();

            if (hBox != null) {
                GetTicketController.ticketId = Integer.parseInt(hBox.getId());
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
            }
        });
    }
}
