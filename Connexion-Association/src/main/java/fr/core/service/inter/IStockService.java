package fr.core.service.inter;

import fr.core.model.customModel.Information;
import fr.core.model.databaseModel.Stock;

import java.util.List;
import java.util.Optional;

public interface IStockService {

    public Optional<List<Stock>> getStock(Integer idAnnex);

    public Optional<Information> updateStock(Integer id, Integer quantity);
}
