package fr.core.model.databaseModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Getter
@Setter
public class Association {
    private int id;
    private String name;
    private String description;
    private String city;
    private boolean active;
}
