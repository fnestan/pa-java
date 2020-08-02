package fr.core.service;

import fr.core.model.customModel.CustomService;
import fr.core.model.customModel.Information;
import fr.core.model.customModel.Manager;
import fr.core.model.databaseModel.Annex;
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

    public Optional<Annex> getAnnexById(Integer id) throws Exception {
        Optional<Annex> optionalAnnex = Optional.empty();
        try {
            Annex annex = restConnector.get("annex/getAnnex/" + id, Annex.class);
            optionalAnnex = Optional.ofNullable(annex);
        } catch (Exception e) {

        }
        return optionalAnnex;
    }

    public Optional<Information> addManager(Integer id, Manager data) throws Exception {
        Optional<Information> optionalResponse = Optional.empty();
        System.out.println(data.email);
        try {
            Information response = restConnector.post("annex/" + id + "/addmanager", data, Information.class);
            optionalResponse = Optional.ofNullable(response);
        } catch (Exception e) {

        }
        return optionalResponse;
    }

    public Optional<Information> removeManager(Integer annexId, Integer userId) throws Exception {
        Optional<Information> optionalResponse = Optional.empty();
        try {
            Information response = restConnector.get("annex/" + annexId + "/removeManager/" + userId, Information.class);
            optionalResponse = Optional.ofNullable(response);
        } catch (Exception e) {

        }
        return optionalResponse;
    }

    public Optional<Service> createService(Service data) throws Exception {
        Optional<Service> optionalResponse = Optional.empty();
        try {
            Service response = restConnector.post("annex/service/" + data.getAnnexId(), data, Service.class);
            optionalResponse = Optional.ofNullable(response);
        } catch (Exception e) {

        }
        return optionalResponse;
    }

    public Optional<List<Service>> listServices(Integer idAnnex) {
        Optional<List<Service>> optionalResponse = Optional.empty();
        try {
            optionalResponse = Optional.ofNullable(Arrays.asList(restConnector.get("/annex/" + idAnnex + "/service/list", Service[].class)));
        } catch (Exception e) {

        }
        return optionalResponse;
    }

    public Optional<Information> removeService(Integer serviceId) throws Exception {
        Optional<Information> optionalResponse = Optional.empty();
        try {
            optionalResponse = Optional.ofNullable(restConnector.put("/annex/service/delete/" + serviceId, null, Information.class));
        } catch (Exception e) {
            System.out.println(e);
        }
        return optionalResponse;
    }

    @Override
    public Optional<Donation> createDonation(Donation donation) {
        Optional<Donation> optionalResponse = null;
        try {
            optionalResponse = Optional.ofNullable(restConnector.post("donation/" + donation.getAnnexId(), donation, Donation.class));
        } catch (Exception e) {

        }
        return optionalResponse;
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

    public Optional<Information> removeDonation(Integer donationId) throws Exception {
        Optional<Information> optionalResponse = null;
        try {
            optionalResponse = Optional.ofNullable(restConnector.put("/annex/donation/delete/" + donationId, null, Information.class));
        } catch (Exception e) {
            System.out.println(e);
        }
        return optionalResponse;
    }

    public Optional<Donation> getDonationById(Integer donationId) {
        Optional<Donation> optionalResponse = Optional.empty();
        try {
            optionalResponse = Optional.ofNullable(restConnector.get("/donation/get/" + donationId, Donation.class));
        } catch (Exception e) {
            System.out.println(e);
        }
        return optionalResponse;
    }

    @Override
    public Optional<Service> getServiceById(Integer idService) {
        Optional<CustomService> optionalResponse = Optional.empty();
        try {
            optionalResponse = Optional.ofNullable(restConnector.get("/service/get/" + idService, CustomService.class));
        } catch (Exception e) {
            System.out.println(e);
        }
        return Optional.ofNullable(optionalResponse.get().getService());
    }
}
