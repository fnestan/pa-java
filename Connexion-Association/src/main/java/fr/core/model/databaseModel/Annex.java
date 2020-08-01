package fr.core.model.databaseModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
public class Annex {
    int id;
    String name;
    String email;
    String zipCode;
    String description;
    String street;
    int phone;
    String city;
    boolean active;
    boolean valid;
    Association association;
    @JsonProperty("Users")
    List<User> users =  new ArrayList<>();
    @JsonProperty("AnnexAvailabilities")
    List<AnnexAvailability> AnnexAvailabilities =  new ArrayList<>();
}
