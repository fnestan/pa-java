package fr.core.model.customModel;

import fr.core.model.databaseModel.Product;

public class ProductRequest extends Product {
    public Integer idProduct;
    public Integer quantity;

    public Integer getProductId() {
        return idProduct;
    }

    public void setProductId(Integer productId) {
        this.idProduct = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
