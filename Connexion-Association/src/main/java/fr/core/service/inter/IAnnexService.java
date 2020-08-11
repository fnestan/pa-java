package fr.core.service.inter;

import fr.core.model.customModel.Information;
import fr.core.model.databaseModel.*;
import fr.core.model.customModel.Manager;

import java.util.List;
import java.util.Optional;

public interface IAnnexService {
    public Optional<List<Annex>> myAnnexes() throws Exception;

    public Optional<Annex> getAnnexById(Integer id) throws Exception;

    public Optional<Information> addManager(Integer id, Manager data) throws Exception;

    public Optional<Information> removeManager(Integer annexId, Integer userId) throws Exception;

    public Optional<Service> createService(Service data) throws Exception;

    Optional<List<Service>> listServices(Integer idAnnex);

    public Optional<List<Service>> removeService(Integer serviceId) throws Exception;

    Optional<Donation> createDonation(Donation donation);

    Optional<List<Donation>> listDonations(Integer idAnnex);

    public Optional<List<Donation>> removeDonation(Integer donationId) throws Exception;

    public Optional<Donation> getDonationById(Integer donationId);

    Optional<Service> getServiceById(Integer idService);

    Optional<Information> updateAnnex(Annex annex);

    Optional<Annex> createAvailability(Integer idAnnex, AnnexAvailability annexAvailability) throws Exception;

    Optional<Annex> deleteAvailability(Integer idAnnexAvailability) throws Exception;

    Optional<List<User>> getParticipants(Integer serviceId) throws Exception;

    Optional<List<User>> getDonors(Integer donationId);
}
