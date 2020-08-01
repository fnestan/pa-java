package fr.core.service.inter;

import fr.core.model.databaseModel.Annex;
import fr.core.model.customModel.Manager;
import fr.core.model.databaseModel.Donation;
import fr.core.model.databaseModel.Service;

import java.util.List;
import java.util.Optional;

public interface IAnnexService {
    public Optional<List<Annex>> myAnnexes() throws Exception;

    public Annex getAnnexById(Integer id) throws Exception;

    public String addManager(Integer id, Manager data) throws Exception;

    public String removeManager(Integer annexId, Integer userId) throws Exception;

    public Service createService(Service data) throws Exception;

    Optional<List<Service>> listServices(Integer idAnnex);

    public Integer[] removeService(Integer serviceId) throws Exception;

    Donation createDonation(Donation donation);

    Optional<List<Donation>> listDonations(Integer idAnnex);

    public Integer[] removeDonation(Integer donationId) throws Exception;

    public Donation getDonationById(Integer donationId);

    Service getServiceById(Integer idService);
}
