package fr.core.service.inter;

import fr.core.model.databaseModel.Type;

import java.util.List;
import java.util.Optional;

public interface ITypeService {
    public Optional<List<Type>> getAllTypes() throws Exception;
}
