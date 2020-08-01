package fr.core.service;

import fr.core.model.databaseModel.Annex;
import fr.core.model.customModel.Manager;
import fr.core.model.databaseModel.Donation;
import fr.core.model.databaseModel.Service;
import fr.core.service.inter.IAnnexService;
import fr.core.service.inter.IRestConnector;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AnnexService implements IAnnexService {
    IRestConnector restConnector;

    public void setRestConnector(IRestConnector restConnector) {
        this.restConnector = restConnector;
    }

    // L' email ou le mot de passe est incorrect
    public Optional<List<Annex>> myAnnexes() throws Exception {
        Optional<List<Annex>> optionalList = Optional.empty();
        try {
            optionalList = Optional.ofNullable(Arrays.asList(restConnector.get("annex/myannexes", Annex[].class)));
        } catch (Exception e) {
            System.out.println(e);
        }
        return optionalList;
    }

    public Annex getAnnexById(Integer id) throws Exception {
        Annex optionalAnnex = null;
        try {
            Annex annex = restConnector.get("annex/getAnnex/" + id, Annex.class);
            optionalAnnex = annex;
        } catch (Exception e) {

        }
        return optionalAnnex;
    }

    public String addManager(Integer id, Manager data) throws Exception {
        String optionalResponse = null;
        System.out.println(data.email);
        try {
            String response = restConnector.post("annex/" + id + "/addmanager", data, String.class);
            optionalResponse = response;
        } catch (Exception e) {

        }
        return optionalResponse;
    }

    public String removeManager(Integer annexId, Integer userId) throws Exception {
        String optionalResponse = null;
        try {
            String response = restConnector.get("annex/" + annexId + "/removeManager/" + userId, String.class);
            optionalResponse = response;
        } catch (Exception e) {

        }
        return optionalResponse;
    }

    public Service createService(Service data) throws Exception {
        Optional<Service> optionalResponse = null;
        try {
            Service response = restConnector.post("annex/service/" + data.getAnnexId(), data, Service.class);
            optionalResponse = Optional.of(response);
        } catch (Exception e) {

        }
        return optionalResponse.orElse(null);
    }

    public Optional<List<Service>> listServices(Integer idAnnex) {
        Optional<List<Service>> optionalResponse = null;
        try {
            List<Service> response = Arrays.asList(restConnector.get("/annex/" + idAnnex + "/service/list", Service[].class));
            optionalResponse = Optional.of(response);
        } catch (Exception e) {

        }
        return optionalResponse;
    }

    public Integer[] removeService(Integer serviceId) throws Exception {
        Integer[] optionalResponse = null;
        try {
            optionalResponse = restConnector.put("/annex/service/delete/" + serviceId, null, Integer[].class);
        } catch (Exception e) {
            System.out.println(e);
        }
        return optionalResponse;
    }

    @Override
    public Donation createDonation(Donation donation) {
        Optional<Donation> optionalResponse = null;
        try {
            Donation response = restConnector.post("donation/" + donation.getAnnexId(), donation, Donation.class);
            optionalResponse = Optional.of(response);
        } catch (Exception e) {

        }
        return optionalResponse.orElse(null);
    }

    @Override
    public Optional<List<Donation>> listDonations(Integer idAnnex) {
        Optional<List<Donation>> optionalResponse = Optional.empty();
        try {
            optionalResponse = Optional.ofNullable(Arrays.asList(restConnector.get("/donation/" + idAnnex + "/list", Donation[].class)));
        } catch (Exception e) {
            System.out.println(e);
        }
        return optionalResponse;
    }

    public Integer[] removeDonation(Integer donationId) throws Exception {
        Integer[] optionalResponse = null;
        try {
            optionalResponse = restConnector.put("/annex/donation/delete/" + donationId, null, Integer[].class);
        } catch (Exception e) {
            System.out.println(e);
        }
        return optionalResponse;
    }

    public Donation getDonationById(Integer donationId) {
        Donation optionalResponse = null;
        try {
            optionalResponse = restConnector.get("/donation/get/" + donationId, Donation.class);
        } catch (Exception e) {
            System.out.println(e);
        }
        return optionalResponse;
    }

    @Override
    public Service getServiceById(Integer idService) {
        Service optionalResponse = null;
        try {
            optionalResponse = restConnector.get("/donation/get/" + idService, Service.class);
        } catch (Exception e) {
            System.out.println(e);
        }
        return optionalResponse;
    }
}
