package fr.core.service.inter;

import fr.core.model.customModel.Information;
import fr.core.model.databaseModel.Annex;
import fr.core.model.customModel.Manager;
import fr.core.model.databaseModel.Donation;
import fr.core.model.databaseModel.Service;

import java.util.List;
import java.util.Optional;

public interface IAnnexService {
    public Optional<List<Annex>> myAnnexes() throws Exception;

    public Optional<Annex> getAnnexById(Integer id) throws Exception;

    public Optional<Information> addManager(Integer id, Manager data) throws Exception;

    public Optional<Information> removeManager(Integer annexId, Integer userId) throws Exception;

    public Optional<Service> createService(Service data) throws Exception;

    Optional<List<Service>> listServices(Integer idAnnex);

    public Optional<Information> removeService(Integer serviceId) throws Exception;

    Optional<Donation> createDonation(Donation donation);

    Optional<List<Donation>> listDonations(Integer idAnnex);

    public Optional<Information> removeDonation(Integer donationId) throws Exception;

    public Optional<Donation> getDonationById(Integer donationId);

    Optional<Service> getServiceById(Integer idService);
}
