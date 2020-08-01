package fr.core.service;

import fr.core.model.customModel.SearchProduct;
import fr.core.model.databaseModel.Product;
import fr.core.service.inter.IProductService;
import fr.core.service.inter.IRestConnector;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductService implements IProductService {
    IRestConnector restConnector;

    public void setRestConnector(IRestConnector restConnector) {
        this.restConnector = restConnector;
    }

    public Product createProduct(Product p) throws Exception {
        Optional<Product> product = null;
        try {
            Product types = restConnector.post("product/create", p, Product.class);
            product = Optional.ofNullable(types);
        } catch (Exception e) {

        }
        return product.orElse(null);
    }

    @Override
    public Optional<List<Product>> getProductByName(SearchProduct text) {
        Optional<List<Product>> products = Optional.empty();
        try {
            products =  Optional.ofNullable(Arrays.asList(restConnector.post("product/searchProduct", text, Product[].class)));
        } catch (Exception e) {

        }
        return products;
    }
}
