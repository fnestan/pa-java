package fr.core.model.databaseModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Getter
@Setter
public class Role {
    private Integer id;
    private String name;
    private Date createdAt;
    private Date updatedAt;
}
