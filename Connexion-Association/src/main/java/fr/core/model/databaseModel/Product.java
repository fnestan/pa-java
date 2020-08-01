package fr.core.model.databaseModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.codehaus.jackson.annotate.JsonProperty;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Product {
    private int id;
    private String name;
    @JsonProperty("TypeId")
    private Integer typeId;
    private boolean active;
    @JsonProperty("Type")
    private Type type;
}
