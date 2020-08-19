package fr.core.model.databaseModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
@Setter
@ToString
public class UserDonation {
    private int Id;
    private int UserId;
    private int quantity;
    private int ProductId;
    private int DonationId;
    private boolean give;
    private User user;
    @JsonProperty("Donation")
    private Donation donation;
    @JsonProperty("Product")
    private Product product;
}
