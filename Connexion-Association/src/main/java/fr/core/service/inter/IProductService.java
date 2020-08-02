package fr.core.service.inter;

import fr.core.model.customModel.SearchProduct;
import fr.core.model.databaseModel.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    public Optional<Product> createProduct(Product p) throws Exception;

    Optional<List<Product>> getProductByName(SearchProduct text);
}
