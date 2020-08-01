package fr.core.service.inter;

import fr.core.model.databaseModel.Ticket;

import java.util.List;

public interface ITicketService {
    public Ticket createTicket(String label);

    public Ticket createMessage(Integer idTicket, String mesage);

    public String closeTicket(Integer IdTicket);

    public List<Ticket> getMyTicket();

    public List<Ticket> getAllTickets();

    public Ticket getTicketById(Integer idTicket);
}
