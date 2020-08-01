package fr.core.service.inter;

import fr.core.model.customModel.Login;
import fr.core.model.databaseModel.User;

import java.util.Optional;

public interface IAuthService {
    public Optional<User> login(Login data) throws Exception;
}
