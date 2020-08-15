package fr.core.model.databaseModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class User {

    private int id;
    private String lastname;
    private String firstname;
    private String login;
    private String password;
    private String email;
    private String zipCode;
    private String street;
    private String city;
    private int phone;
    private Date birthdate;
    private boolean active;
    private String token;
    private String validForVolunteer;
    private String validForUser;
    @JsonProperty("ImageId")
    private int ImageId;
    @JsonProperty("RoleId")
    private Integer roleId;
    private Date createdAt;
    private Date updatedAt;
    private List<Annex> annexes;
    @JsonProperty("Role")
    private fr.core.model.databaseModel.Role Role;
    @JsonProperty("isReported")
    private boolean isReported;
}
