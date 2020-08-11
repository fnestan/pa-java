package fr.core.service;

import fr.core.model.customModel.CustomService;
import fr.core.model.customModel.Information;
import fr.core.model.customModel.Manager;
import fr.core.model.databaseModel.*;
import fr.core.service.inter.IAnnexService;
import fr.core.service.inter.IRestConnector;

import java.util.ArrayList;
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

    public Optional<List<Service>> removeService(Integer serviceId) throws Exception {
        Optional<List<Service>> optionalResponse = Optional.empty();
        try {
            optionalResponse = Optional.ofNullable(Arrays.asList(restConnector.put("/annex/service/delete/" + serviceId, null, Service.class)));
        } catch (Exception e) {
            System.out.println(e);
        }
        return optionalResponse;
    }

    @Override
    public Optional<Donation> createDonation(Donation donation) {
        Optional<Donation> optionalResponse = Optional.empty();
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

    public Optional<List<Donation>> removeDonation(Integer donationId) throws Exception {
        Optional<List<Donation>> optionalResponse = Optional.empty();
        try {
            optionalResponse = Optional.ofNullable(Arrays.asList(restConnector.put("/annex/donation/delete/" + donationId, null, Donation.class)));
        } catch (Exception e) {
            System.out.println(e);
        }
        return optionalResponse;
    }

    public Optional<Donation> getDonationById(Integer donationId) {
        Optional<Donation> optionalResponse = Optional.empty();
        try {
            optionalResponse = Optional.ofNullable(restConnector.get("donation/get/" + donationId, Donation.class));
        } catch (Exception e) {
            System.out.println(e);
        }
        return optionalResponse;
    }

    @Override
    public Optional<Service> getServiceById(Integer idService) {
        Optional<CustomService> optionalResponse = Optional.empty();
        try {
            optionalResponse = Optional.ofNullable(restConnector.get("service/get/" + idService, CustomService.class));
        } catch (Exception e) {
            System.out.println(e);
        }
        return Optional.ofNullable(optionalResponse.get().getService());
    }

    @Override
    public Optional<Information> updateAnnex(Annex annex) {
        Optional<Information> optionalResponse = Optional.empty();
        try {
            Information response = restConnector.put("annex/update/" + annex.getId(), annex, Information.class);
            optionalResponse = Optional.ofNullable(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return optionalResponse;
    }

    @Override
    public Optional<Annex> createAvailability(Integer idAnnex, AnnexAvailability annexAvailability) throws Exception {
        Optional<Annex> optionalAnnex = Optional.empty();
        try {
            Annex annex = restConnector.post("annex/availability/create/" + idAnnex, annexAvailability, Annex.class);
            optionalAnnex = Optional.ofNullable(annex);
        } catch (Exception e) {

        }
        return optionalAnnex;
    }

    @Override
    public Optional<Annex> deleteAvailability(Integer idAnnexAvailability) throws Exception {
        Optional<Annex> optionalAnnex = Optional.empty();
        try {
            Annex annex = restConnector.delete("annex/deleteAvailable/" + idAnnexAvailability, Annex.class);
            optionalAnnex = Optional.ofNullable(annex);
        } catch (Exception e) {

        }
        return optionalAnnex;
    }

    @Override
    public Optional<List<User>> getParticipants(Integer serviceId) throws Exception {
        Optional<List<User>> optionalResponse = Optional.empty();
        try {
            optionalResponse = Optional.ofNullable(Arrays.asList(restConnector.get("/service/get/users/list/" + serviceId, User[].class)));
        } catch (Exception e) {
            System.out.println(e);
        }
        return optionalResponse;
    }

    @Override
    public Optional<List<User>> getDonors(Integer donationId) {
        Optional<List<User>> optionalResponse = Optional.empty();
        try {
            optionalResponse = Optional.ofNullable(Arrays.asList(restConnector.get("/donation/get/users/list/" + donationId, User[].class)));
        } catch (Exception e) {
            System.out.println(e);
        }
        return optionalResponse;
    }
}
