package fr.core.model.databaseModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
@Setter
@ToString
public class Stock {

    private int id;
    private int quantity;
    @JsonProperty("Annex")
    private Annex annex ;
    @JsonProperty("Product")
    private Product product;
}
