package fr.core.service;

import fr.core.model.customModel.Information;
import fr.core.model.databaseModel.Ticket;
import fr.core.service.inter.IRestConnector;
import fr.core.service.inter.ITicketService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class TicketService implements ITicketService {
    IRestConnector iRestConnector;

    public void setRestConnector(IRestConnector iRestConnector) {
        this.iRestConnector = iRestConnector;
    }

    @Override
    public Optional<Ticket> createTicket(String data) {

        var values = new HashMap<String, String>() {{
            put("label", data);
        }};
        Optional<Ticket> ticket = Optional.empty();
        try {
            ticket = Optional.ofNullable(iRestConnector.post("ticket/create", values, Ticket.class));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ticket;
    }

    @Override
    public Optional<Ticket> createMessage(Integer idTicket, String message) {
        var values = new HashMap<String, String>() {{
            put("message", message);
        }};
        Optional<Ticket> ticket = Optional.empty();
        try {
            ticket = Optional.ofNullable(iRestConnector.post("ticket/" + idTicket + "/message", values, Ticket.class));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ticket;
    }

    @Override
    public Optional<Information> closeTicket(Integer IdTicket) {
        Optional<Information> ticket = Optional.empty();
        try {
            ticket = Optional.ofNullable(iRestConnector.get("ticket/" + IdTicket + "/close", Information.class));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ticket;
    }

    @Override
    public Optional<List<Ticket>> getMyTicket() {
        Optional<List<Ticket>> optionalList = Optional.empty();
        try {
            List<Ticket> tickets = Arrays.asList(iRestConnector.get("ticket/myTickets", Ticket[].class));
            optionalList = Optional.ofNullable(tickets);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return optionalList;
    }

    @Override
    public Optional<List<Ticket>> getAllTickets() {
        Optional<List<Ticket>> optionalList = Optional.empty();
        try {
            List<Ticket> tickets = Arrays.asList(iRestConnector.get("ticket/all", Ticket[].class));
            optionalList = Optional.ofNullable(tickets);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return optionalList;
    }

    @Override
    public Optional<Ticket> getTicketById(Integer idTicket) {
        Optional<Ticket> optionalTicket = Optional.empty();
        try {
            Ticket tickets = iRestConnector.get("ticket/" + idTicket, Ticket.class);
            optionalTicket = Optional.ofNullable(tickets);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return optionalTicket;
    }
}
