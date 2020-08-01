package fr.core.model.databaseModel;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
@Setter
public class Requerir {
    private int id;
    private int quantity;
    private Integer DonationId;
    private Integer ProductId;
    @JsonProperty("Product")
    private Product product;
}
