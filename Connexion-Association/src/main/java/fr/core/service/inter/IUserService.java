package fr.core.service.inter;

import fr.core.model.databaseModel.User;
import fr.core.model.customModel.ValidationResponse;

import java.util.List;

public interface IUserService {
    public List<User> users() throws Exception;

    public void banUser(Integer idUser) throws Exception;

    public User validateUser(Integer idUser, ValidationResponse answer) throws Exception;

    public User validateVolunteer(int id, ValidationResponse validationResponse);
    }
