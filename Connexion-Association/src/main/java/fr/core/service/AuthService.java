package fr.core.service;

import fr.core.model.customModel.Information;
import fr.core.model.customModel.Login;
import fr.core.model.databaseModel.User;
import fr.core.service.inter.IAuthService;
import fr.core.service.inter.IRestConnector;

import java.util.Optional;

public class AuthService implements IAuthService {
    IRestConnector restConnector;

    public void setRestConnector(IRestConnector restConnector) {
        this.restConnector = restConnector;
    }

    // L' email ou le mot de passe est incorrect
    public Optional<User> login(Login data) throws Exception {
        Optional<User> optionalUser = Optional.empty();
        try {
            optionalUser = Optional.ofNullable(restConnector.post("auth/login", data, User.class));
        } catch (Exception e) {

        }
        return optionalUser;
    }
}
