package fr.core.service.inter;

import fr.core.model.customModel.Information;
import fr.core.model.databaseModel.Ticket;

import java.util.List;
import java.util.Optional;

public interface ITicketService {
    public Optional<Ticket> createTicket(String label);

    public Optional<Ticket> createMessage(Integer idTicket, String mesage);

    public Optional<Information> closeTicket(Integer IdTicket);

    public Optional<List<Ticket>> getMyTicket();

    public Optional<List<Ticket>> getAllTickets();

    public Optional<Ticket> getTicketById(Integer idTicket);
}
