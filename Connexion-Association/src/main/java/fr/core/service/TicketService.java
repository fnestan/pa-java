package fr.core.service;

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
    public Ticket createTicket(String data) {
        var values = new HashMap<String, String>() {{
            put("label", data);
        }};
        System.out.println("hi there");
        Ticket ticket = null;
        try {
            ticket = iRestConnector.post("ticket/create", values, Ticket.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ticket;
    }

    @Override
    public Ticket createMessage(Integer idTicket, String message) {
        var values = new HashMap<String, String>() {{
            put("message", message);
        }};
        Ticket ticket = null;
        try {
            ticket = iRestConnector.post("ticket/" + idTicket + "/message", values, Ticket.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ticket;
    }

    @Override
    public String closeTicket(Integer IdTicket) {
        String ticket = null;
        try {
            ticket = iRestConnector.get("ticket/" + IdTicket + "/close", String.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ticket;
    }

    @Override
    public List<Ticket> getMyTicket() {
        Optional<List<Ticket>> optionalList = null;
        try {
            List<Ticket> tickets = Arrays.asList(iRestConnector.get("ticket/myTickets", Ticket[].class));
            optionalList = Optional.of(tickets);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return optionalList.orElse(null);
    }

    @Override
    public List<Ticket> getAllTickets() {
        Optional<List<Ticket>> optionalList = null;
        try {
            List<Ticket> tickets = Arrays.asList(iRestConnector.get("ticket/all", Ticket[].class));
            optionalList = Optional.of(tickets);
            System.out.println(tickets.size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return optionalList.orElse(null);
    }

    @Override
    public Ticket getTicketById(Integer idTicket) {
        Optional<Ticket> optionalTicket = null;
        try {
            Ticket tickets = iRestConnector.get("ticket/" + idTicket, Ticket.class);
            optionalTicket = Optional.of(tickets);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return optionalTicket.orElse(null);
    }
}
