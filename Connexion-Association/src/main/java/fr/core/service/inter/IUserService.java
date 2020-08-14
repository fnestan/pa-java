package fr.core.service.inter;

import fr.core.model.customModel.Email;
import fr.core.model.customModel.Information;
import fr.core.model.databaseModel.User;
import fr.core.model.customModel.ValidationResponse;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    public Optional<List<User>> users() throws Exception;

    public Information banUser(Integer idUser) throws Exception;

    public Information validateUser(Integer idUser, ValidationResponse answer) throws Exception;

    public Information validateVolunteer(int id, ValidationResponse validationResponse);

    Optional<Information> sendMail(Email email);

}
