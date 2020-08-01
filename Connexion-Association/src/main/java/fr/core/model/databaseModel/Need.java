package fr.core.model.databaseModel;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.codehaus.jackson.annotate.JsonProperty;

@FieldDefaults(level= AccessLevel.PRIVATE)
@Getter
@Setter
public class Need {
    private int id;
    private String nom;
    private String description;
    private int quantite;
    private boolean status;
    private boolean actif;
    private int AnnexId;
    @JsonProperty("Annex")
    private Annex annex;
}
