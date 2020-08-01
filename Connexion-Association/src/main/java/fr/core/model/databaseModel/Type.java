package fr.core.model.databaseModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.codehaus.jackson.annotate.JsonProperty;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Getter
@Setter
public class Type {
    private Integer id;
    @JsonProperty("token")
    private String name;
}
