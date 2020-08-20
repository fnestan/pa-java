package fr.core.service;

import com.google.gson.JsonObject;
import fr.core.model.customModel.Information;
import fr.core.model.databaseModel.Stock;
import fr.core.service.inter.IRestConnector;
import fr.core.service.inter.IStockService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StockService implements IStockService {

    IRestConnector restConnector;

    public void setRestConnector(IRestConnector restConnector) {
        this.restConnector = restConnector;
    }


    @Override
    public Optional<List<Stock>> getStock(Integer idAnnex) {
        Optional<List<Stock>> optionalResponse = Optional.empty();
        try {
            optionalResponse = Optional.ofNullable(Arrays.asList(restConnector.get("stock/get/list/" + idAnnex, Stock[].class)));
        } catch (Exception e) {
            System.out.println(e);
        }
        return optionalResponse;
    }

    @Override
    public Optional<Information> updateStock(Integer id, Integer quantity) {
        Optional<Information> optionalResponse = Optional.empty();
        Stock stock = new Stock();
        stock.setQuantity(quantity);
        try {
            optionalResponse = Optional.ofNullable(restConnector.put("stock/update/" + id, stock, Information.class));
        } catch (Exception e) {
            System.out.println(e);
        }
        return optionalResponse;
    }
}
