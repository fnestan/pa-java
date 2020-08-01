package fr.core.service;

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


    public List<User>  users() throws Exception {
        Optional<List<User>> optionalList = null;
        try {
            List<User> users = Arrays.asList(restConnector.get("user/get/all", User[].class));
            optionalList = Optional.of(users);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return optionalList.get();
    }

    public void banUser(Integer idUser) throws Exception {
        try {
            restConnector.get("user/ban/" + idUser, void.class);
        } catch (Exception e) {

        }
    }

    public User validateUser(Integer idUser, ValidationResponse answer) throws Exception {
        User user = null;
        try {
            user = restConnector.put("user/validateUser/" + idUser, answer, User.class);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return user;
    }

    public User validateVolunteer(int id, ValidationResponse validationResponse) {
        User user = null;
        try {
            user = restConnector.put("user/validateVolunter/" + id, validationResponse, User.class);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return user;
    }
}
