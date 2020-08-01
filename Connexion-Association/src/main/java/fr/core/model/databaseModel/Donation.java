package fr.core.model.databaseModel;

import fr.core.model.customModel.ProductRequest;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Donation extends Need {
    List<ProductRequest>productRequests = new ArrayList<>();
    @JsonProperty("Requerirs")
    List<Requerir>requerirs = new ArrayList<>();

    public List<ProductRequest> getProductRequests() {
        return productRequests;
    }

    public List<Requerir> getRequerirs() {
        return requerirs;
    }

    public void getRequerir(List<Requerir> requerirs) {
        this.requerirs = requerirs;
    }

    public void setProductRequests(List<ProductRequest> productRequests) {
        this.productRequests = productRequests;
    }
}
