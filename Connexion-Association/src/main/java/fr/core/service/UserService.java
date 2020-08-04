package fr.core.service;

import fr.core.model.customModel.Information;
import fr.core.model.databaseModel.User;
import fr.core.model.customModel.ValidationResponse;
import fr.core.service.inter.IRestConnector;
import fr.core.service.inter.IUserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserService implements IUserService {
    IRestConnector restConnector;

    public void setRestConnector(IRestConnector restConnector) {
        this.restConnector = restConnector;
    }


    public Optional<List<User>> users() throws Exception {
        Optional<List<User>> optionalList = Optional.empty();
        try {
            List<User> users = Arrays.asList(restConnector.get("user/get/all", User[].class));
            optionalList = Optional.of(users);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return optionalList;
    }

    public Information banUser(Integer idUser) throws Exception {
        Information information = new Information();
        try {
            information = restConnector.get("user/ban/" + idUser, Information.class);
        } catch (Exception e) {

        }
        return information;
    }

    public Information validateUser(Integer idUser, ValidationResponse answer) throws Exception {
        Information information = new Information();
        try {
            information = restConnector.put("user/validateUser/" + idUser, answer, Information.class);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return information;
    }

    public Information validateVolunteer(int id, ValidationResponse validationResponse) {
        Information information = new Information();
        try {
            information = restConnector.put("user/validateVolunter/" + id, validationResponse, Information.class);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return information;
    }
}
